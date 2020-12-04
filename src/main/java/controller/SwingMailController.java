package controller;

import swingpanels.*;
import utilities.SwingMailReceiver;
import utilities.impl.SwingMailReceiverImpl;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Properties;

public class SwingMailController {

    //Create the window
    private JFrame frame;

    // Create the "container" panel of the "pages"
    private JPanel appPanel;
    // Create the different "pages"
    private LoginPanel loginPanel;
    private MailboxPanel mailboxPanel;

    // Create the top-level pages manager
    private CardLayout appCL;
    // Create the mailbox pages manager
    private CardLayout mailboxCL;

    public SwingMailController() {

        // Create the CardLayout managers
        appCL = new CardLayout();
        mailboxCL = new CardLayout();

        // Create the top-level Panels
        frame = new JFrame("MailBox by papiboi99");
        appPanel = new JPanel(appCL);
        loginPanel = new LoginPanel();
        mailboxPanel = new MailboxPanel(mailboxCL);

        setupGUI();

        frame.pack();
        frame.setVisible(true);
    }

    private void setupGUI() {

        //Set up the window.
        frame.add(appPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        //
        appPanel.add(loginPanel, "1");
        appPanel.add(mailboxPanel, "2");
        appCL.show(appPanel, "1");

        //
        setupLogin();

    }

    public void setupLogin(){

        // When Login button pressed check the credentials to go next
        loginPanel.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Properties imapProperties = new Properties();
                    InputStream input =
                            SwingMailController.class.getClassLoader().getResourceAsStream("imap.properties");

                    if (input == null) {
                        System.out.println("Sorry, unable to find config.properties");
                        return;
                    }

                    //load a properties file from class path, inside static method
                    imapProperties.load(input);

                    SwingMailReceiver receiver = new SwingMailReceiverImpl();
                    receiver.receiveEmail(imapProperties);

                    appCL.show(appPanel, "2");

                } catch (AuthenticationFailedException ex) {
                    System.out.println("Holaaaa1");
                } catch (
                        MessagingException ex) {
                    System.out.println("Holaaaa2");
                } catch (Exception ex) {
                    System.out.println("Holaaaa3");
                }
            }
        });

        // When in Username text field hits Enter set cursor in password
        loginPanel.getUsernameField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPanel.getPasswordField().requestFocusInWindow();
            }
        });

        // When in Password field hits Enter perform button Login pressed
        loginPanel.getPasswordField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPanel.getLoginButton().doClick();
            }
        });
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
