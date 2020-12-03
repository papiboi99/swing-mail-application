package controller;

import model.Mail;
import utilities.SwingMailReceiver;
import utilities.SwingMailSender;
import utilities.impl.SwingMailReceiverImpl;
import utilities.impl.SwingMailSenderImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class SwingMailController {

    public static void main(String[] args) {

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
/*
        String[] to = {"youssef3456@gmail.com"};
        Mail mail = new Mail ();

        mail.setTo(to);
        mail.setDate(new Date());
        mail.setSubject("test");
        mail.setText("test");

        mailSender.sendMail(smtpProperties, mail);*/
        SwingMailReceiver mailReceiver = new SwingMailReceiverImpl();
        Properties imapProperties = new Properties();
        try {
            InputStream input =
                    SwingMailController.class.getClassLoader().getResourceAsStream("imap.properties");

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            //load a properties file from class path, inside static method
            imapProperties.load(input);

        } catch (IOException ex) {

        }

        mailReceiver.receiveEmail(imapProperties);






        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });*/

    }
}
