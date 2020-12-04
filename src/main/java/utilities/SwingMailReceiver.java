package utilities;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import java.util.Properties;

public interface SwingMailReceiver {
    public void receiveEmail(Properties properties) throws Exception, AuthenticationFailedException, MessagingException;
}
