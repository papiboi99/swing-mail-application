package controller;

import api.SwingMailApi;
import swingpanels.*;
import utilities.SwingMailReceiver;
import utilities.impl.SwingMailReceiverImpl;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class SwingMailController implements SwingMailApi {

    // Create the window
    private JFrame frame;

    // Create the "container" panel of the "pages"
    private JPanel appPanel;
    // Create the different "pages"
    private LoginPanel loginPanel;
    private MailboxPanel mailboxPanel;

    // Create the top-level pages manager
    private CardLayout appCL;

    public SwingMailController() {

        // Create the CardLayout
        appCL = new CardLayout();

        // Create the top-level Panels
        frame = new JFrame("MailBox by papiboi99");
        appPanel = new JPanel(appCL);
        loginPanel = new LoginPanel();
        mailboxPanel = new MailboxPanel();

        setupGUI();

        frame.pack();
        frame.setVisible(true);
    }

    public void setupGUI() {
        // Set up the card layout
        appPanel.add(loginPanel, "1");
        appPanel.add(mailboxPanel, "2");
        //appCL.show(appPanel, "1"); ------------------------------------------
        setupMailbox(); // ---------------------------------------------------

        // Set up the the login menu
        // setupLogin(); -------------------------------------------------------

        // Set up the window.
        frame.add(appPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setupLogin(){
        // Login menu properties
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim = new Dimension(dim.width/5,dim.height/2);
        frame.setPreferredSize(dim);
        loginPanel.setPreferredSize(dim);

        JTextField usernameField = loginPanel.getUsernameField();
        JPasswordField passwordField = loginPanel.getPasswordField();
        JButton loginButton = loginPanel.getLoginButton();

        // When Login button pressed check the credentials to go next
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (OutputStream output = new FileOutputStream("src/main/resources/connection.properties", true)){

                    Properties prop = new Properties();

                    prop.setProperty("connection.user", usernameField.getText());
                    // The getPassword from JPasswordField returns a char array for security purpose
                    // but we are not worried about security because the one who will do that work
                    // is the java mail with imap connection
                    prop.setProperty("connection.password", new String(passwordField.getPassword()));

                    InputStream input =
                            SwingMailController.class.getClassLoader().getResourceAsStream("connection.properties");

                    if (input == null) {
                        System.out.println("Sorry, unable to find config.properties");
                        return;
                    }

                    //load a properties file from class path, inside static method
                    prop.load(input);

                    SwingMailReceiver receiver = new SwingMailReceiverImpl();
                    receiver.receiveEmail(prop);

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
                } catch (Exception ex) {
                    System.out.println("Exception");
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

    public void setupMailbox(){

        // Mailbox menu properties
        frame.setResizable(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim = new Dimension(dim.width/2,dim.height/2);
        frame.setPreferredSize(dim);
        mailboxPanel.setPreferredSize(dim);

        appCL.show(appPanel, "2");
        mailboxPanel.revalidate();
    }

    /*
        SwingMailSender mailSender = new SwingMailSenderImpl();

        //Temporal Testing
        Properties smtpProperties = new Properties();
        try {
            InputStream input =
                    SwingMailController.class.getClassLoader().getResourceAsStream("smtp.properties");

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            //load a properties file from class path, inside static method
            smtpProperties.load(input);

        } catch (IOException ex) {

        }

        String[] to = {"youssef3456@gmail.com"};
        Mail mail = new Mail ();

        mail.setTo(to);
        mail.setDate(new Date());
        mail.setSubject("test");
        mail.setText("test");

        mailSender.sendMail(smtpProperties, mail);
*/

}
