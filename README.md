# swing-mail-application
 This is a Java Swing application that sends and receives e-mail messages (with an optional attachment).

## SETTING UP

### Gmail SMTP as our way to send mails

**Only works for English(USA) Gmail version, if you have a different Gmail client lenguaje change it or it may fail**

To use Gmail SMTP free server you will have to allow him the access from less secure external apps [here](https://myaccount.google.com/u/0/lesssecureapps?pli=1).

An example format would be:

**SMTP Server:** *smtp.gmail.com*  
**SMTP User:** Your complete Gmail user (email) *yourmail@gmail.com*  
**SMTP Password:** Your Gmail password   
**SMTP Port:** Default port forTLS *587* 

_Porperties file:_

    mail.smtp.host=smtp.gmail.com
    mail.smtp.port=587
    mail.smtp.starttls.enable=true
    mail.smtp.auth=true

### Gmail IMAP as our way to import mails

To use Gmail IMAP you will have to enable the access from the Gmail configurations, follow the instructions [here](https://support.google.com/mail/answer/7126229?hl=es)

An example format would be:  

**IMAP Server:** *imap.gmail.com*  
**IMAP User:** Your complete Gmail user (email) *yourmail@gmail.com*  
**IMAP Password:** Your Gmail password  
**IMAP Port:** Default port for TLS *997* 

_Porperties file:_

    mail.imap.host=imap.gmail.com
    mail.imap.port=993
    (this ones will be written by the app)
    //mail.user=papiboi99.sad@gmail.com 
    //mail.password=papiboi99-

## HOW IT WORKS?

### APP STRUCTURE

1. Swing GUI (eMail sending & viewing)

    - Login menu
    - MailBox menu
        - Folders:  
          - Inbox  
          - Sent  
          - Spam  
          - Trash
        - Send mail option
        - Logout option
        - Search option
        - Content viewer:
          - MailList:
            - Update option
            - Delete option
            - Double-click to open
            - Check box for multiple selection  
            - Quick menu by right click:
              - Open
              - Move to Trash
              - Reply
          - Mail view:
            - Mail fields
            - Download attachments option  
            - Go back to mail list option  
            - Reply mail option  
          - Mail editor:
            - Mail fields edition
            - Add attachment option
            - Exit mail option
            - Information popup 
            - Send mail option

                    
### DETAILED DESCRIPTION

1. **Log in** with your gmail account.  
2. **MailBox** menu will show up with the default folder mail list view (inbox).
   1. On the left side will be the different available folders (Spam,Inbox,Send,Trash), you can access by clicking them and automatically will update the list.
   2. Just in the top of the folders is situated the _compose button_ to send a new mail.
   3. You can also search a mail in the correspondent folder by writing a key in the _search field_ above and pressing _return_ or clicking the _search button_.
   4. If you want to log out then just press the _lout button_ and will immediately go back to the log in menu.  

3. Referring to the **mail list** itself, you can do different actions:
   - Click in one mail for visual selecting.
   - Double-click in one mail for open it.   
   - Right click in one mail for quick action menu (Open, Move to Trash or Reply).
   - Click on the _check box_ for multiple selection of mails to move to the trash folder when pressing the _paper bin button_.
   - Click on the _reload button_ to reload the mail list and check for new mails.
4. In the **mail view** a mode when open action you can:
    - See the different mail fields and select&copy them.
    - Go back to the mail list by clicking the _back button_ (left corner).
    - Reply the email when clicking the _reply button_ on the top
    - Download the files in it by clicking to the _download file button_
    > **REPLY:** It will create a new tab for not overriding the mail view.    
     **DOWNLOAD:** It will show a menu for selecting the route where you want the files to be downloaded. Then a progress dialog will let you know when the download is completed.   
   
5. As far as the **mail writting** menu is concerned:
   - If you came from compose it will create an empty mail editor, but if it was from a reply action in will have completed the to field with the destinatary and the subject respond.
   - You can set the other fields writting or putting an attachment clicking in the _open file button_
   - If you want to cancel the mail just press exit and if it wasn't empty an "Are you sure dialog" will show up, if it was empty no.
   - There is a strict format to write in some fields, this information is shown when you hover over the info label.
   - When you are finished click the _send button_ to send the mail and when completed it will show a dialog.
    
   >You can have many new mail tabs as you want

## IMPLEMENTATIONS TO DO LIST
### Future releases

   - Delete attachments option
   - Keep logged
   - Signatures
   - Text formats
   - Seen or unseen mails at list
   - Move to any folder
   - More folders  
   - More languages

