package api.dto;

import javax.mail.Address;

public class MailDTO {
    private Address[] to;
    private Address[] cc;
    private Address[] from;
    private String subject;
    private String text;
    //private ... attachment;

    public Address[] getTo() {
        return to;
    }

    public void setTo(Address[] to) {
        this.to = to;
    }

    public Address[] getCc() {
        return cc;
    }

    public void setCc(Address[] cc) {
        this.cc = cc;
    }

    public Address[] getFrom() {
        return from;
    }

    public void setFrom(Address[] from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
