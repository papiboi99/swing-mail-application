# swing-mail-application
 This is a Java Swing application that sends and receives e-mail messages (with an optional attachment).

## SETTING UP

### Gmail SMTP as our way to send mails

To use Gmail SMTP free server you will have to allow him the access from less secure external apps [here](https://myaccount.google.com/u/0/lesssecureapps?pli=1).

An example format would be:

**SMTP Server:** *smtp.gmail.com*  
**SMTP User:** Your complete Gmail user (email) *yourmail@gmail.com*  
**SMTP Password:** Your Gmail password   
**SMTP Port:** Default port forTLS *587*  

### Gmail IMAP as our way to import mails

To use Gmail IMAP you will have to enable the access from the Gmail configurations, follow the instructions [here](https://support.google.com/mail/answer/7126229?hl=es)

An example format would be:  

**IMAP Server:** *imap.gmail.com*  
**IMAP User:** Your complete Gmail user (email) *yourmail@gmail.com*  
**IMAP Password:** Your Gmail password  
**IMAP Port:** Default port for TLS *997*  

## IMPLEMENTATIONS TO DO LIST
1. Swing GUI (eMail sending & viewing)
   
   - Login menu
   - MailBox menu
     - Folders:  
       -- Inbox  
       -- Sent  
       -- Spam  
       -- Trash
     - Send mail option
     - Logout option
     - Mail list view
     - Search option
   
2. To Release Version 1.0

   - GUI completed
   - Send attachments
   - Receiver part
   
3. Future releases

   - Signatures
   - Text formats
   - Seen or unseen mails at list
