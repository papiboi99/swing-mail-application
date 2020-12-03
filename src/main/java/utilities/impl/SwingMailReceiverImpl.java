package utilities.impl;

import api.dto.MailDTO;
import model.Mail;
import utilities.SwingMailReceiver;


import java.util.Properties;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Address;
import com.sun.mail.imap.IMAPFolder;

public class SwingMailReceiverImpl implements SwingMailReceiver {
@Override
   public void receiveEmail(Properties properties){
   //create session object
    final String host = properties.getProperty("mail.imap.host");
    final int port= Integer.parseInt(properties.getProperty("mail.imap.port"));
    final String username=properties.getProperty("mail.user");
    final String password=properties.getProperty("mail.password");



    Session session = Session.getDefaultInstance(properties, null);

        try {

        //connect to message store
            Store store = session.getStore("imaps");
            store.connect(host,port,username,password);

        //open the inbox folder
        //podemos mirar de acceder a otras carpetas(spam,enviados..)
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);

        //fetch messages
            Message[] messages = inbox.getMessages();

        //read messages
            //aqui hay que ver como retornamos las cosas para tratarlas mas alante
            for (Message msg : messages) {
                //falta añadir cada mail a la lista de mails
                //(convertir antes a mail)
                MailDTO mailDTO=new MailDTO();

                mailDTO.setCc(msg.getRecipients(Message.RecipientType.CC));
                mailDTO.setTo(msg.getRecipients(Message.RecipientType.TO));
                mailDTO.setSubject(msg.getSubject());
                mailDTO.setFrom(msg.getFrom());

                //distinguir entre tipos multipart
               // mailDTO.setText(...);

                System.out.println(mailDTO.getText());
            }

        }catch (AuthenticationFailedException e){
            System.out.println("Holaaaa1");
        }
        catch (MessagingException e) {
            System.out.println("Holaaaa2");
        }
        catch (Exception e) {
            System.out.println("Holaaaa3");
        }
    }
}
