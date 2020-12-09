package utilities;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SearchTerm;
import java.util.List;
import java.util.Properties;

public interface SwingMailReceiver {
    void connect(Properties properties) throws AuthenticationFailedException, MessagingException;
    Message[] receiveMailList(String folder) throws MessagingException;
    Message[] search(String key) throws MessagingException;

    Message[] reload() throws MessagingException;

    Message getMessage(int id);

    void moveToTrash(Message[] messagesToMove) throws MessagingException;

    void disconnect() throws MessagingException;
}
