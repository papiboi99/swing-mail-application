package model;

import java.io.File;
import java.util.Date;

public class Mail {

    private String[] to;
    private String[] cc;
    private String from;
    private String subject;
    private String text;
    private File[] attachments;
    private Date date;

    public String[] getTo() { return to; }
    public void setTo(String[] to) { this.to = to; }
    public String[] getCc() { return cc; }
    public void setCc(String[] cc) { this.cc = cc; }
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public File[] getAttachments() { return attachments; }
    public void setAttachments(File[] attachments) { this.attachments = attachments; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

}
