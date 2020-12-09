package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MailListTableModel extends AbstractTableModel {

    private List<Mail> mailList;

    // Define the attachments type
    private final static String[] PICTURE = {"jpeg", "jpg", "jpe", "jfif", "png", "tif", "tiff", "gif"};
    private final static String[] VIDEO = {"mp4", "avi", "mpeg", "flv", "mov", "qt", "wmv", "mpg"};
    private final static String[] AUDIO = {"aac", "flac", "mp3", "m4a", "wav", "wma"};
    private final static String[] WORK_FILE = {"pdf", "xls", "pptx", "pptm", "ppt"};

    // Create the structure
    private final String[] columnNames = new String[]{
            "Select", "Username", "Subject", "AttatchType", "Date"
    };
    private final Class[] columnClass = new Class[]{
            Boolean.class, String.class, String.class, String.class, String.class
    };

    public MailListTableModel(List<Mail> mailList) {
        this.mailList = mailList;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public int getRowCount() {
        return mailList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mail row = mailList.get(rowIndex);
        if (0 == columnIndex) {
            return row.isSelected();
        } else if (1 == columnIndex) {
            return row.getFromUsername();
        } else if (2 == columnIndex) {
            return row.getSubject();
        } else if (3 == columnIndex) {
            List<String> extensionIcon = new ArrayList<String>();

            for (String type : row.getAttachmentsType()) {
                if (Arrays.asList(PICTURE).contains(type)) {
                    extensionIcon.add("\uD83D\uDCF7");
                }
                else if (Arrays.asList(VIDEO).contains(type)) {
                    extensionIcon.add("\uD83C\uDFAC");
                }
                else if (Arrays.asList(AUDIO).contains(type)) {
                    extensionIcon.add("\uD83C\uDFB5");
                }
                else if (Arrays.asList(WORK_FILE).contains(type)) {
                    extensionIcon.add("\uD83D\uDCC4");
                }
                else {
                    extensionIcon.add("\uD83D\uDCCE");
                }
            }
            return extensionIcon.toString().replace("[", "").
                    replace("]", "").replace(",", "");
        } else if (4 == columnIndex) {
            return row.dateToString();

        }

        return null;
    }

    public Mail getRow (int rowIndex) {
        return mailList.get(rowIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Mail row = mailList.get(rowIndex);
        if(0 == columnIndex) {
            row.setSelected((Boolean) aValue);
        }
        else if(1 == columnIndex) {
            row.setFromUsername((String) aValue);
        }
        else if(2 == columnIndex) {
            row.setSubject((String) aValue);
        }
        else if(3 == columnIndex) {
            row.setAttachments((String[]) aValue);
        }
        else if(3 == columnIndex) {
            row.setDate((Date) aValue);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        if(0 == columnIndex) {
            return true;
        }
        else{
            return false;
        }
    }
}
