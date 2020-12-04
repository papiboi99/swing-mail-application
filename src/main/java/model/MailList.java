package model;

import java.util.List;

public class MailList {

    private List mailList;
    private String folder;

    public MailList(List mailList, String folder) {
        this.mailList = mailList;
        this.folder = folder;
    }

    public List getMailList() { return mailList; }
    public String getFolder() { return folder; }

    public int listSize() {
        return mailList.size();
    }

}
