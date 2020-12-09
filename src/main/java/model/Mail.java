package model;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mail {

    private String[] to;
    private String[] cc;
    private String fromUsername;
    private String fromAddress;
    private String subject;
    private String text;
    private boolean selected;
    private Date date;
    private String[] attachments;
    private File[] attachmentsFile;
    private int id;

    public String[] getTo() { return to; }
    public void setTo(String[] to) { this.to = to; }

    public String[] getCc() { return cc; }
    public void setCc(String[] cc) { this.cc = cc; }
    public String ccToString(){
        String ccString = "";
        for (String ccAux : cc){

            if (ccString == ""){
                ccString += ccAux;
            }else{
                ccString += ", " + ccAux;
            }
        }
        return ccString;
    }
    public boolean hasCc(){
        if (cc != null){
            if(cc[0] != null && !cc[0].trim().isEmpty()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public String getFromUsername() { return fromUsername; }
    public void setFromUsername(String fromUsername) { this.fromUsername = fromUsername; }

    public String getFromAddress() { return fromAddress; }
    public void setFromAddress(String fromAddress) { this.fromAddress = fromAddress; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String[] getAttachments() { return attachments; }
    public void setAttachments(String[] attachments) { this.attachments = attachments; }
    public String[] getAttachmentsType() {
        String[] attachmentsType = new String[attachments.length];

        int i = 0;
        for (String name: attachments){
            attachmentsType[i] = name.substring(name.indexOf(".")+1);
            i++;
        }
        return attachmentsType;
    }
    public File[] getAttachmentsFile() { return attachmentsFile; }
    public void setAttachmentsFile(File[] attachmentsFile) { this.attachmentsFile = attachmentsFile; }
    public String attachmentsToString(){
        String atString = "";
        for (String atAux : attachments){
            if (atString == ""){
                atString += atAux;
            }else{
                atString += ", " + atAux;
            }
        }
        return atString;
    }
    public boolean hasAttachments(){
        if (attachments != null && attachments.length > 0) {
            return true;
        }else{
            return false;
        }
    }

    public void setSelected(boolean selected) {this.selected = selected; }
    public boolean isSelected() { return selected; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String dateToString(){
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm");
        return dateFormat.format(this.date);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}
