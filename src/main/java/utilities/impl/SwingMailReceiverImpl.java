package utilities.impl;

import controller.SwingMailController;
import utilities.SwingMailReceiver;


import java.util.Properties;
import javax.mail.*;
import javax.mail.search.SearchTerm;

public class SwingMailReceiverImpl implements SwingMailReceiver {

    private Folder folderInUse;
    private Store store;
    private Message[] messagesInUse;
    private String folderInUseName;

    private static final String INBOX = "Inbox";
    private static final String SENT = "[Gmail]/Sent Mail";
    private static final String SPAM = "[Gmail]/Spam";
    private static final String TRASH = "[Gmail]/Trash";

    @Override
    public void connect(Properties properties) throws AuthenticationFailedException, MessagingException{
        // Create session object
        final String host = properties.getProperty("mail.imap.host");
        final int port = Integer.parseInt(properties.getProperty("mail.imap.port"));
        final String username = properties.getProperty("mail.user");
        final String password = properties.getProperty("mail.password");

        Session session = Session.getDefaultInstance(properties, null);

        // Connect to message store
        store = session.getStore("imaps");
        store.connect(host, port, username, password);
    }

    @Override
    public Message[] receiveMailList(String folderName) throws MessagingException {

        if (folderInUseName != null || folderInUseName == folderName){
            folderInUse.close();
        }

        folderInUseName = getProperFolderName(folderName);
        folderInUse = store.getFolder(folderInUseName);

        // Get the folder with permission to edit
        folderInUse.open(Folder.READ_WRITE);

        messagesInUse = folderInUse.getMessages();

        return messagesInUse;
    }

    @Override
    public Message[] search(String key) throws MessagingException{
        SearchTerm term = new SearchTerm() {
            public boolean match(Message message) {
                try {
                    if (message.getSubject().contains(key)) {
                        return true;
                    }
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        };

        messagesInUse = folderInUse.search(term);

        return messagesInUse;
    }

    @Override
    public Message[] reload() throws MessagingException {
        return receiveMailList(folderInUseName);
    }

    @Override
    public Message getMessage(int id){
        return messagesInUse[id];
    }

    @Override
    public void moveToTrash(Message[] messagesToMove) throws MessagingException{
        folderInUse.copyMessages(messagesToMove, store.getFolder(TRASH));
    }

    @Override
    public void disconnect() throws MessagingException{
        store.close();
        folderInUse = null;
        messagesInUse = null;
        folderInUseName = null;
    }

    private String getProperFolderName(String folder){
        String folderName = folderInUseName;
        switch (folder){
            case SwingMailController.INBOX:
                folderName = INBOX;
                break;
            case SwingMailController.SENT:
                folderName = SENT;
                break;
            case SwingMailController.SPAM:
                folderName = SPAM;
                break;
            case SwingMailController.TRASH:
                folderName = TRASH;
                break;
        }

        return folderName;
    }
}
