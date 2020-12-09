package utilities;

import model.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public interface SwingMailSender {

    void sendMail (Properties smtpProperties, Mail mail) throws AddressException, MessagingException, IOException;
}
