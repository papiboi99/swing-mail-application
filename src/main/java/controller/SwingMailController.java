package controller;

import api.SwingMailApi;
import model.Mail;
import swingpanels.*;
import swingpanels.mailbox.MailListPanel;
import swingpanels.mailbox.MailPanel;
import swingpanels.mailbox.MailSenderPanel;
import utilities.SwingMailReceiver;
import utilities.SwingMailSender;
import utilities.impl.SwingMailReceiverImpl;
import utilities.impl.SwingMailSenderImpl;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.SearchException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SwingMailController implements SwingMailApi {

    // Define the window
    private JFrame frame;

    // Define the "container" panel of the "pages"
    private JPanel appPanel;
    // Define the different "pages"
    private LoginPanel loginPanel;
    private MailboxPanel mailboxPanel;
    private List<MailSenderPanel> mailSenderPanelList;

    // Define the top-level pages manager
    private CardLayout appCL;

    // Define the properties
    private Properties prop;

    // Define the receiver and the sender
    private SwingMailReceiver mailReceiver;
    private SwingMailSender mailSender;

    // Define progress monitor
    private ProgressMonitor progressMonitor;
    private Download download;

    // Define folders
    public static final String DEFAULT_FOLDER = "Inbox";
    public static final String INBOX = "Inbox";
    public static final String SENT = "Sent";
    public static final String SPAM = "Spam";
    public static final String TRASH = "Trash";
    public static final int DEFAULT_FOLDER_NUM = 0;
    public static final int INBOX_NUM = 0;
    public static final int SENT_NUM = 1;
    public static final int SPAM_NUM = 2;
    public static final int TRASH_NUM = 3;

    // Define send modes
    public static final int SEND = 0;
    public static final int REPLY = 1;


    public SwingMailController() {

        // Create the CardLayout
        appCL = new CardLayout();

        // Create the first panel
        frame = new JFrame("MailBox by papiboi99");
        appPanel = new JPanel(appCL);

        // Create the properties
        prop = new Properties();

        // Create the mail receiver and sender
        mailReceiver = new SwingMailReceiverImpl();
        mailSender = new SwingMailSenderImpl();

        setupGUI();

        frame.pack();
        frame.setVisible(true);
    }

    public void setupGUI() {
        // Set up the the login menu
        setupLogin();

        // Set up the window.
        frame.add(appPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/media/" +
                "papiboi99_icon.png"));
        frame.setIconImage(image);
    }

    public void setupLogin(){
        // Create login menu and configure it
        loginPanel = new LoginPanel();
        appPanel.add(loginPanel, "1");
        appCL.show(appPanel, "1");
        appPanel.validate();
        appPanel.repaint();
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim = new Dimension(dim.width/5,dim.height/2);
        frame.setSize(dim);
        frame.setLocationRelativeTo(null);
        appPanel.setPreferredSize(dim);
        loginPanel.setPreferredSize(dim);


        JTextField usernameField = loginPanel.getUsernameField();
        JPasswordField passwordField = loginPanel.getPasswordField();
        JButton loginButton = loginPanel.getLoginButton();

        // When Login button pressed check the credentials to go next
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (OutputStream output = new FileOutputStream("src/main/resources/mail.properties", true)){

                    prop.setProperty("mail.user", usernameField.getText());
                    // The getPassword from JPasswordField returns a char array for security purpose
                    // but we are not worried about security because the one who will do that work
                    // is the java mail with imap connection
                    prop.setProperty("mail.password", new String(passwordField.getPassword()));

                    InputStream input =
                            SwingMailController.class.getClassLoader().getResourceAsStream("mail.properties");

                    if (input == null) {
                        JOptionPane.showMessageDialog(null,
                                "Sorry, unable to find config.properties",
                                "Login error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //load a properties file from class path
                    prop.load(input);

                    // Try to connect
                    mailReceiver.connect(prop);

                    setupMailbox();

                } catch (AuthenticationFailedException ex) {
                    // When wrong username or password
                    loginPanel.authFailed();
                    loginPanel.revalidate();
                    frame.pack();
                } catch (MessagingException ex) {
                    // When no email address is written
                    loginPanel.noAddressWritten();
                    loginPanel.revalidate();
                    frame.pack();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // When in Username text field hits Enter set cursor in password
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.requestFocusInWindow();
            }
        });

        // When in Password field hits Enter perform button Login pressed
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick();
            }
        });
    }

    public void setupMailbox() throws MessagingException, IOException{
        // Create mailBox menu and configure it
        mailboxPanel = new MailboxPanel();
        appPanel.add(mailboxPanel, "2");
        appPanel.validate();
        appPanel.repaint();
        appCL.show(appPanel, "2");
        frame.setResizable(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim = new Dimension(dim.width/2,dim.height/2);
        frame.setSize(dim);
        frame.setLocationRelativeTo(null);
        mailboxPanel.setPreferredSize(dim);

        // Create the listeners
        JButton searchButton = mailboxPanel.getSearchButton();
        JButton sendButton = mailboxPanel.getSendButton();
        JButton logoutButton = mailboxPanel.getLogoutButton();
        JTextField searchField = mailboxPanel.getSearchField();
        JList foldersList = mailboxPanel.getFoldersList();

        // When logout button pressed go back to Login
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mailReceiver.disconnect();
                    frame.remove(mailboxPanel);
                    setupLogin();

                } catch (MessagingException messagingException) {
                    JOptionPane.showMessageDialog(null,
                            "Something went wrong while closing the session",
                            "Logout error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // When search button pressed, search in mails the keywords in text field
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String key = searchField.getText();
                if (key == "Search mail...") {
                    searchField.setText("Search mail...");
                }else{
                    try {
                        Message[] foundMessages = mailReceiver.search(key);
                        mailboxPanel.setupMailListPanel(messagesToMailList(foundMessages));
                    } catch (SearchException searchException) {
                        JOptionPane.showMessageDialog(null,
                                "The search term is too complex for the implementation to handle.",
                                "Search error",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (MessagingException messagingException) {
                        JOptionPane.showMessageDialog(null,
                                "Could not connect to the message store.",
                                "Search error",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(null,
                                "Could not get multipart correctly",
                                "Search error",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchButton.doClick();
            }
        });

        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                searchField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                searchField.setText("Search mail...");
            }
        });

        // When search button pressed, search in mails the keywords in text field
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupMailSenderPanel(SEND, new Mail());
            }
        });

        // When a folder is selected change to that list
        foldersList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if(!e.getValueIsAdjusting()) {
                    String selectedValue = foldersList.getSelectedValue().toString();
                    selectedValue = selectedValue.substring(selectedValue.indexOf(" ")+1);
                    try {
                        mailboxPanel.setupMailListPanel(receiveMailList(selectedValue));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                "Unable to reach "+ selectedValue +" folder",
                                "Server error ",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // Default content view
        setupMailListPanel();
    }

    public void setupMailListPanel() throws MessagingException, IOException{
        // Create the mail list panel
        List<Mail> mailList = receiveMailList(DEFAULT_FOLDER);
        mailboxPanel.setupMailListPanel(mailList);

        //Create listeners
        MailListPanel mailListPanel = mailboxPanel.getMailListPanel();
        JTable mailListTable = mailListPanel.getMailListTable();
        JButton reloadButton = mailListPanel.getReloadButton();
        JButton deleteButton = mailListPanel.getDeleteButton();
        JMenuItem menuItemOpen = mailListPanel.getMenuItemOpen();
        JMenuItem menuItemReply = mailListPanel.getMenuItemReply();
        JMenuItem menuItemRemove = mailListPanel.getMenuItemRemove();

        // When mail right clicked make row selection
        mailListTable.addMouseListener(new MouseAdapter() {
           @Override
           public void mousePressed(MouseEvent e) {
               Point point = e.getPoint();
               int currentRow = mailListTable.rowAtPoint(point);
               mailListTable.setRowSelectionInterval(currentRow, currentRow);

               JTable table =(JTable) e.getSource();
               int row = table.rowAtPoint(point);
               if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                   setupMailPanel(mailListPanel.getMailListTableModel().getRow(mailListTable.getSelectedRow()));
               }

           }
        });

        // What to do when menu item selected
        ActionListener popupMenuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem menu = (JMenuItem) e.getSource();
                if (menu == menuItemOpen) {
                    setupMailPanel(mailListPanel.getMailListTableModel().getRow(mailListTable.getSelectedRow()));

                } else if (menu == menuItemReply) {
                    setupMailSenderPanel(REPLY, mailboxPanel.getMailListPanel().getMailListTableModel().
                            getRow(mailboxPanel.getMailListPanel().getMailListTable().getSelectedRow()));

                } else if (menu == menuItemRemove) {
                    mailListTable.setValueAt(true, mailListTable.getSelectedRow(), 0);
                    deleteButton.doClick();
                }
            }
        };
        menuItemOpen.addActionListener(popupMenuListener);
        menuItemReply.addActionListener(popupMenuListener);
        menuItemRemove.addActionListener(popupMenuListener);

        // When reload pressed get the newest folder version
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Message[] reloadedMessages = mailReceiver.reload();
                    mailboxPanel.setupMailListPanel(messagesToMailList(reloadedMessages));
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Unable to reload the folder",
                            "Server error ",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Message> messageList = new ArrayList<>();
                    for (int i = 0; i < mailListTable.getRowCount(); i++) {
                        boolean delete = (boolean) mailListTable.getValueAt(i, 0);
                        if (delete){
                            messageList.add(mailReceiver.getMessage(i));
                        }
                    }
                    mailReceiver.moveToTrash(messageList.toArray(new Message[0]));

                    Message[] reloadedMessages = mailReceiver.reload();
                    mailboxPanel.setupMailListPanel(messagesToMailList(reloadedMessages));

                }catch(Exception exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Unable to delete messages",
                            "Server error ",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    public void setupMailSenderPanel(int mode, Mail mail){
        if (mailSenderPanelList == null || mailSenderPanelList.isEmpty()){
            mailSenderPanelList = new ArrayList<MailSenderPanel>();
        }

        if (mode == SEND){
            MailSenderPanel sender = mailboxPanel.sendMail();
            mailSenderPanelList.add(sender);

        }else if (mode == REPLY){
            MailSenderPanel sender = mailboxPanel.replyMail(mail);
            mailSenderPanelList.add(sender);
        }

        MailSenderPanel actualSenderPanel = mailSenderPanelList.get(mailSenderPanelList.size()-1);
        JTextField fieldSubject = mailSenderPanelList.get(mailSenderPanelList.size()-1).getFieldSubject();
        JTextField fieldCC = mailSenderPanelList.get(mailSenderPanelList.size()-1).getFieldCC() ;
        JTextField fieldTo = mailSenderPanelList.get(mailSenderPanelList.size()-1).getFieldTo();
        JTextField fieldAttachment = mailSenderPanelList.get(mailSenderPanelList.size()-1).getFieldAttachment();
        JTextArea textAreaMessage = mailSenderPanelList.get(mailSenderPanelList.size()-1).getTextAreaMessage();
        JButton cancelMailButton = mailSenderPanelList.get(mailSenderPanelList.size()-1).getCancelMailButton();
        JButton attachButton = mailSenderPanelList.get(mailSenderPanelList.size()-1).getAttachButton();
        JButton sendButton = mailSenderPanelList.get(mailSenderPanelList.size()-1).getSendButton();
        JFileChooser attachment = mailSenderPanelList.get(mailSenderPanelList.size()-1).getAttachment();

        fieldTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldCC.requestFocusInWindow();
            }
        });

        fieldCC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldSubject.requestFocusInWindow();
            }
        });

        fieldSubject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaMessage.requestFocusInWindow();
            }
        });

        cancelMailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //si el email esta vacio cerramos la ventana sin preguntar
                if (fieldAttachment.getText().isEmpty() && fieldCC.getText().isEmpty()
                        && fieldSubject.getText().isEmpty() && fieldTo.getText().isEmpty() && textAreaMessage.getText().isEmpty()) {
                    mailboxPanel.closeTab();
                    mailSenderPanelList.remove(actualSenderPanel);

                } else {
                    //choice vale 0 si es OK (JOptionPane.OK_OPTION), 2 si Cancel (JOptionPane.CANCEL_OPTION)
                    int option = JOptionPane.showConfirmDialog(null,
                            "Do you want to cancel the Email?", "Exit Dialog",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (option == JOptionPane.OK_OPTION) {
                        mailboxPanel.closeTab();
                        mailSenderPanelList.remove(actualSenderPanel);
                    }
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Mail mail = new Mail();
                    String to = fieldTo.getText();
                    String subject = fieldSubject.getText();

                    if(to.isEmpty() || subject.isEmpty()){
                        throw new MessagingException();
                    }

                    mail.setTo(to.split(";"));
                    mail.setCc(fieldCC.getText().split(";"));
                    mail.setFromAddress(prop.getProperty("mail.user"));
                    mail.setSubject(subject);
                    mail.setDate(new Date());
                    mail.setText(textAreaMessage.getText());
                    mail.setAttachmentsFileList(actualSenderPanel.getFilesList());
                    sendMail(mail);

                    JOptionPane.showMessageDialog(null,
                            "Message send succesfully", "Message Send",
                            JOptionPane.INFORMATION_MESSAGE);
                    mailboxPanel.closeTab();

                } catch (AddressException addressException) {
                    addressException.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Please, write an existent address",
                            "Invalid Address",
                            JOptionPane.ERROR_MESSAGE);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (MessagingException messagingException){
                    messagingException.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Could not create a mail, make sure you write in the fields with a proper format",
                            "Invalid Mail",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        attachButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(attachment.showSaveDialog(actualSenderPanel) == JFileChooser.APPROVE_OPTION){
                    actualSenderPanel.addFile(attachment.getSelectedFile());
                }
            }
        });

        actualSenderPanel.getInfo().addMouseListener((new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                JOptionPane.showOptionDialog(null,
                        "Make sure you separate with ; when multi addressing, without blank spaces!" +
                         "\n Example: user1@mail.com;user2@mail.com", "INFO",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new Object[]{}, null);
            }
        }));
    }

    public void setupMailPanel(Mail mail){
        mailboxPanel.setupMailPanel(mail);

        MailPanel mailPanel = mailboxPanel.getMailPanel();

        mailPanel.getGoBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mailboxPanel.goBackToMailList();
            }
        });

        mailPanel.getDownloadAttachButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser saveAttachment = mailPanel.getSaveAttachment();
                String directory = "";
                saveAttachment.setDialogTitle("Choose a directory to save your files: ");
                saveAttachment.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int sD = saveAttachment.showSaveDialog(null);

                if (sD == JFileChooser.APPROVE_OPTION) {
                    if (saveAttachment.getSelectedFile().isDirectory()) {
                        directory = saveAttachment.getSelectedFile().toString();

                        progressMonitor = new ProgressMonitor(frame,
                                "",
                                "Starting download", 0, 100);
                        progressMonitor.setProgress(0);
                        download = new Download(directory, mail);
                        download.addPropertyChangeListener(new PropertyChangeListener() {
                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                if ("progress" == evt.getPropertyName() ) {
                                    int progress = (int) evt.getNewValue();
                                    progressMonitor.setProgress(progress);
                                    String message =
                                            String.format("Completed %d%%.\n", progress);
                                    progressMonitor.setNote(message);
                                    if (progressMonitor.isCanceled() || download.isDone()) {
                                        Toolkit.getDefaultToolkit().beep();
                                        if (progressMonitor.isCanceled()) {
                                            download.cancel(true);
                                            progressMonitor.setNote("Task canceled.\n");
                                        } else {
                                            progressMonitor.setNote("Download completed.\n");
                                        }
                                    }
                                }
                            }
                        });
                        download.execute();
                    }
                }
            }
        });

        mailPanel.getReplyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupMailSenderPanel(REPLY, mailboxPanel.getMailPanel().getMail());
            }
        });

    }

    private class Download extends SwingWorker <Void, Void> {

        private String directory;
        private Mail mail;

        public Download (String directory, Mail mail){
            this.directory = directory;
            this.mail = mail;
        }

        @Override
        public Void doInBackground() {
            try {
                int progress = 0;
                setProgress(0);
                Message message = mailReceiver.getMessage(mail.getId());
                String contentType = message.getContentType();
                Thread.sleep(500);
                setProgress(10);

                if (contentType.contains("multipart")) {
                    // content may contain attachments
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    int progressParts = 80/numberOfParts;
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // this part is attachment
                            String fileName = part.getFileName();
                            part.saveFile(directory + File.separator + fileName);
                        }
                        progress = progress + progressParts;
                        setProgress(progress);
                    }
                }
                setProgress(99);
                Thread.sleep(800);
            }catch (Exception exception){
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "An error has occurred while trying to download, sorry try again",
                        "Download failed",
                        JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            progressMonitor.setProgress(100);
        }
    }

    public void sendMail(Mail mail) throws AddressException, MessagingException, IOException {
        mailSender.sendMail(prop, mail);
    }

    public List<Mail> receiveMailList(String folder) throws MessagingException, IOException{
        Message[] messages = mailReceiver.receiveMailList(folder);
        return messagesToMailList(messages);
    }

    public List<Mail> messagesToMailList(Message[] messages) throws MessagingException, IOException{
        List<Mail> mailList = new ArrayList<Mail>();
        int id = 0;
        for (Message msg : messages) {
            Mail mail = new Mail();
            mail.setCc(parseAddresses(msg.getRecipients(Message.RecipientType.CC)));
            mail.setTo(parseAddresses(msg.getRecipients(Message.RecipientType.TO)));
            String from = parseAddresses(msg.getFrom())[0];

            if (from.contains("<")){
                mail.setFromUsername(from.substring(0, from.indexOf("<") - 1));
                mail.setFromAddress(from.substring(from.indexOf("<")+1, from.length()-1));
            }
            else{
                mail.setFromUsername("(You)");
                mail.setFromAddress(from);
            }

            mail.setSubject(msg.getSubject());
            mail.setDate(msg.getSentDate());
            mail.setSelected(false);
            mail.setId(id);
            id++;

            String content = "";
            List<String> attachmentsName = new ArrayList<String>();
            String contentType = msg.getContentType();
            if (contentType.contains("multipart")) {
                Multipart multi = (Multipart) msg.getContent();
                int nParts = multi.getCount();
                for (int i = 0; i < nParts; i++) {
                    MimeBodyPart part = (MimeBodyPart) multi.getBodyPart(i);
                    content = getMimeBodyPartMessage(part);

                    if (content.contains("File:")){
                        attachmentsName.add(content.substring(content.indexOf(":")+1));
                    }else if (content.contains("Text:")){
                        mail.setText(content.substring(content.indexOf(":")+1));
                    }
                }
                if (!attachmentsName.isEmpty()){
                    mail.setAttachmentsName(attachmentsName.toArray(new String[0]));
                }
            } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                Object msgContent = msg.getContent();
                if (msgContent != null) {
                    mail.setText(msgContent.toString());
                }
            }

            mailList.add(mail);
        }

        return mailList;
    }

    public String getMimeBodyPartMessage(MimeBodyPart part) throws MessagingException, IOException {
        String content = "";

        if (part.isMimeType("text/html")) {
            String html = (String) part.getContent();
            content = "Text:" + org.jsoup.Jsoup.parse(html).text();

        } else if (part.isMimeType("text/plain")) {
            content = "Text:" + part.getContent().toString();
        } else if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
            String fileName = part.getFileName();
            content = "File:" + fileName;
        }
        return content;
    }

    /**
     * Returns a list of addresses in String array format
     *
     * @param address an array of Address objects
     * @return a string array that represents a list of addresses
     */
    private String[] parseAddresses(Address[] address) {
        List<String> listAddress = new ArrayList<String>();

        if (address != null) {
            for (int i = 0; i < address.length; i++) {
                listAddress.add(address[i].toString());
            }
        }
        return listAddress.toArray(new String[0]);
    }


}
