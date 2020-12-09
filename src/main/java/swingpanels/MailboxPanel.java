package swingpanels;

import controller.SwingMailController;
import model.Mail;
import swingpanels.mailbox.MailListPanel;
import swingpanels.mailbox.MailPanel;
import swingpanels.mailbox.MailSenderPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MailboxPanel extends JPanel {
    // Define the card managers
    private CardLayout contentCL;
    private CardLayout contentModeCL;

    // Define the layout configurations
    private GridBagConstraints constraints;

    // Define components
    private JTextField searchField;
    private JLabel logoLabel;
    private JButton searchButton;
    private JButton logoutButton;
    private JButton sendButton;
    private JList<String> foldersList;
    private JPanel contentModePanel;
    private JPanel contentPanel;
    private MailListPanel mailListPanel;
    private MailPanel mailPanel;
    private JTabbedPane mailTabbedPane;
    private JPanel subPanel;

    private String contentModePanelShownNum;

    public MailboxPanel() {
        super(new GridBagLayout());
        constraints = new GridBagConstraints();

        // Create the CardLayout
        contentCL = new CardLayout();
        contentModeCL = new CardLayout();

        // Create components
        searchField = new JTextField("Search mail...",30);
        searchButton = new JButton("\uD83D\uDD0D");
        logoutButton = new JButton("Logout");
        sendButton = new JButton("✉ Compose");
        contentModePanel = new JPanel(contentModeCL);
        contentPanel = new JPanel(contentCL);

        // Creating the list
        DefaultListModel<String> foldersListModel = new DefaultListModel<>();
        foldersListModel.addElement("\uD83D\uDCE5 " + SwingMailController.INBOX);
        foldersListModel.addElement("\uD83D\uDCE4 " + SwingMailController.SENT);
        foldersListModel.addElement("\uD83D\uDCDB " + SwingMailController.SPAM);
        foldersListModel.addElement("\uD83D\uDDD1 " + SwingMailController.TRASH);
        foldersList = new JList<>(foldersListModel);
        foldersList.setSelectedIndex(SwingMailController.DEFAULT_FOLDER_NUM);

        // Creating and scaling the logo
        logoLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/media/" +
                "papiboi99_logo.png")).getImage().getScaledInstance(150, 75, Image.SCALE_SMOOTH)));

        setupGUI();
    }

    public void setupGUI(){
        // Add the components to the panel
        setBackground(Color.WHITE);

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 20, 0, 5);
        add(logoLabel, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        constraints.ipadx = 0;
        constraints.insets = new Insets(0, 5, 0, 0);
        add(searchField, constraints);

        constraints.gridx = 2;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, 2, 0, 5);
        add(searchButton, constraints);

        constraints.gridx = 3;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.ipadx = 40;
        constraints.insets = new Insets(0, 5, 0, 40);
        add(logoutButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.ipadx = 5;
        constraints.ipady = 10;
        constraints.insets = new Insets(10, 40, 10, 0);
        add(sendButton, constraints);

        constraints.gridy = 2;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        constraints.insets = new Insets(0, 40, 0, 0);
        add(foldersList, constraints);

        subPanel = new JPanel(new GridBagLayout());

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        constraints.insets = new Insets(0, 10, 20, 20);
        subPanel.add(contentModePanel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth=3;
        constraints.gridheight=2;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 0, 0, 0);
        add(subPanel, constraints);

        // Set up the card layouts to show the default page
        contentModePanel.add(contentPanel, "1");
        contentModeCL.show(contentModePanel, "1");
        contentModePanelShownNum = "1";
    }

    public void setupMailListPanel (List<Mail> mailList){
        if (mailListPanel == null){
            mailListPanel = new MailListPanel(mailList);
        }else{
            mailListPanel.reloadMailList(mailList);
        }

        // Set up the card layout
        contentPanel.add(mailListPanel, "1");
        contentCL.show(contentPanel, "1");
    }

    public void setupMailPanel (Mail mail){
        mailPanel = new MailPanel(mail);

        // Set up the card layout
        contentPanel.add(mailPanel, "2");
        contentCL.show(contentPanel, "2");
    }

    public void setupMailSenderPanel(MailSenderPanel mailSenderPanel){
        //Setup the tabbed pane
        if (mailTabbedPane == null || contentModePanelShownNum == "1"){
            mailTabbedPane = new JTabbedPane();
            mailTabbedPane.addTab("MailBox", contentPanel);
            mailTabbedPane.addTab("New message", mailSenderPanel);

            mailTabbedPane.setSelectedComponent(mailSenderPanel);

            // Set up the card layout
            contentModePanel.add(mailTabbedPane, "2");
            contentModeCL.show(contentModePanel, "2");
            contentModePanelShownNum = "2";

            subPanel.validate();
            subPanel.repaint();

        }else{
            mailTabbedPane.addTab("New message "+(mailTabbedPane.getTabCount()), mailSenderPanel);
            mailTabbedPane.setSelectedComponent(mailSenderPanel);
            contentModeCL.show(contentModePanel, "2");
            contentModePanelShownNum = "2";
            contentModePanel.validate();
            contentModePanel.repaint();
        }
    }

    public MailSenderPanel sendMail(){
        MailSenderPanel mailSenderPanel = new MailSenderPanel();
        setupMailSenderPanel(mailSenderPanel);
        return mailSenderPanel;
    }

    public MailSenderPanel replyMail(Mail mail){
        MailSenderPanel mailSenderPanel = new MailSenderPanel(mail);
        setupMailSenderPanel(mailSenderPanel);
        return mailSenderPanel;
    }

    public void closeTab (){
        if (mailTabbedPane.getTabCount() > 2){
            mailTabbedPane.remove(mailTabbedPane.getSelectedComponent());
        }else{
            mailTabbedPane.remove(mailTabbedPane.getSelectedComponent());
            contentModePanel.add(contentPanel, "1");
            contentModeCL.show(contentModePanel, "1");
            contentModePanelShownNum = "1";
            contentModePanel.validate();
            contentModePanel.repaint();
        }
    }

    public void goBackToMailList(){
        // Set up the card layout
        contentPanel.remove(mailPanel);
        contentCL.show(contentPanel, "1");
        contentPanel.validate();
        contentPanel.repaint();
    }

    public JTextField getSearchField() { return searchField; }
    public JButton getSearchButton() { return searchButton; }
    public JButton getLogoutButton() { return logoutButton; }
    public JButton getSendButton() { return sendButton; }
    public JList<String> getFoldersList() { return foldersList; }
    public CardLayout getContentCL() { return contentCL; }
    public JPanel getContentPanel() { return contentPanel; }
    public MailListPanel getMailListPanel() { return mailListPanel; }
    public MailPanel getMailPanel() { return mailPanel; }
    public JTabbedPane getMailTabbedPane() { return mailTabbedPane; }
}
