package utilities;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public interface SwingMailSender {

    void sendEmail(Properties smtpProperties, String toAddress,
                   String subject, String message, File[] attachFiles);
}
