package model;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Mail {

    private String[] to;
    private String[] cc;
    private String fromUsername;
    private String fromAddress;
    private String subject;
    private String text;
    private boolean selected;
    private Date date;
    private String[] attachmentsName;
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

    public String[] getAttachmentsName() { return attachmentsName; }
    public void setAttachmentsName(String[] attachmentsName) { this.attachmentsName = attachmentsName; }
    public String[] getAttachmentsType() {

        if (hasAttachmentNames()){
            String[] attachmentsType = new String[attachmentsName.length];

            int i = 0;
            for (String name: attachmentsName){
                attachmentsType[i] = name.substring(name.indexOf(".")+1);
                i++;
            }
            return attachmentsType;
        }else{
            return null;
        }
    }
    public File[] getAttachmentsFile() { return attachmentsFile; }
    public void setAttachmentsFile(File[] attachmentsFile) { this.attachmentsFile = attachmentsFile; }
    public void setAttachmentsFileList(List<File> attachmentsFileList) {
        attachmentsFile = new File[attachmentsFileList.size()];
        for (int i = 0; i < attachmentsFileList.size(); i++){
            attachmentsFile[i] = attachmentsFileList.get(i);
        }
    }
    public String attachmentsToString(){
        if(hasAttachmentNames()){
            String atString = "";
            for (String atAux : attachmentsName){
                if (atString == ""){
                    atString += atAux;
                }else{
                    atString += ", " + atAux;
                }
            }
            return atString;
        }else{
            return null;
        }
    }
    public boolean hasAttachmentFiles(){
        if (attachmentsFile != null && attachmentsFile.length > 0) {
            return true;
        }else{
            return false;
        }
    }
    public boolean hasAttachmentNames(){
        if (attachmentsName != null){
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
