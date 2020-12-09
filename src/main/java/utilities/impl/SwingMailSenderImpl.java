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
    public void sendMail (Properties properties, Mail mail) throws AddressException, MessagingException, IOException{
        final String userName = properties.getProperty("mail.user");
        final String password = properties.getProperty("mail.password");

            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            };
            Session session = Session.getInstance(properties, auth);

            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            InternetAddress[] toAddresses = new InternetAddress[mail.getTo().length];
            for (int i = 0; i < mail.getTo().length; i++ ){
                toAddresses[i] = new InternetAddress(mail.getTo()[i]);
            }

            if (mail.hasCc()) {
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
            if (mail.hasAttachmentFiles()) {
                for (File aFile : mail.getAttachmentsFile()) {
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

    }
}
