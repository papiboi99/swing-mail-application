package utilities.impl;

import model.Mail;
import utilities.SwingMailSender;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

//String toAddress, String subject, String message, File[] attachFiles
public class SwingMailSenderImpl implements SwingMailSender {
    public void sendMail (Properties smtpProperties, Mail mail) {
        final String userName = smtpProperties.getProperty("connection.user");
        final String password = smtpProperties.getProperty("connection.password");

        try {
            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            };
            Session session = Session.getInstance(smtpProperties, auth);

            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            InternetAddress[] toAddresses = new InternetAddress[mail.getTo().length];
            for (int i = 0; i < mail.getTo().length; i++ ){
                toAddresses[i] = new InternetAddress(mail.getTo()[i]);
            }

            if (mail.getCc() != null && mail.getCc().length > 0) {
                InternetAddress[] ccAddresses = new InternetAddress[mail.getCc().length];
                for (int i = 0; i < mail.getCc().length; i++) {
                    ccAddresses[i] = new InternetAddress(mail.getCc()[i]);
                }
                msg.setRecipients(Message.RecipientType.CC, ccAddresses);
            }

            msg.setFrom(new InternetAddress(userName));
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(mail.getSubject());
            msg.setSentDate(mail.getDate());

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mail.getText(), "text/html");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // adds attachments
            if (mail.getAttachments() != null && mail.getAttachments().length > 0) {
                for (File aFile : mail.getAttachments()) {
                    MimeBodyPart attachPart = new MimeBodyPart();

                    try {
                        attachPart.attachFile(aFile);
                    } catch (IOException ex) {
                        throw ex;
                    }

                    multipart.addBodyPart(attachPart);
                }
            }

            // sets the multi-part as e-mail's content
            msg.setContent(multipart);

            // sends the e-mail
            Transport.send(msg);
        } catch (AddressException e){

        } catch (MessagingException e) {

        } catch (IOException e) {

        }

    }
}
