package swingpanels.mailbox;

import model.Mail;

import javax.swing.*;
import java.awt.*;

public class MailPanel extends JPanel {

    private JButton downloadAttachButton;
    private JButton goBackButton;
    private JButton replyButton;
    private JTextArea textAreaMessage;
    private GridBagConstraints constraints;

    private JLabel labelAttachment;
    private JLabel labelCC;
    private JLabel labelFrom;
    private JLabel labelSubject;
    private JLabel labelDate;
    private JTextField fieldFrom;
    private JTextField fieldSubject;
    private JTextField fieldAttachment;
    private JTextField fieldCC;
    private JFileChooser saveAttachment;

    private Mail mail;

    public MailPanel (Mail mail) {
        super(new GridBagLayout());
        constraints = new GridBagConstraints();

        this.mail = mail;
        downloadAttachButton = new JButton("Download File");
        goBackButton = new JButton("\uD83D\uDD19");
        replyButton = new JButton("↩");
        textAreaMessage = new JTextArea(mail.getText());
        labelAttachment = new JLabel("Attachment ");
        labelCC = new JLabel("Cc: ");
        labelSubject = new JLabel("Subject: ");
        labelFrom = new JLabel("From: ");
        labelDate = new JLabel(mail.dateToString());
        fieldSubject = new JTextField(mail.getSubject());
        fieldAttachment = new JTextField(mail.attachmentsToString(), 40);
        fieldCC = new JTextField(mail.ccToString());
        fieldFrom = new JTextField(mail.getFromAddress());
        saveAttachment = new JFileChooser();

        setupGUI();
    }
    public void setupGUI(){

        //go back button
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(2, 10, 10, 10);
        add(goBackButton,constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        add(replyButton,constraints);

        //subject field
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        fieldSubject.setEditable(false);
        constraints.insets = new Insets(10, 0, 2, 20);
        add(fieldSubject,constraints);

        constraints.gridx = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(0, 0, 0, 0);
        add(labelSubject, constraints);

        //date field
        constraints.gridx=2;
        constraints.gridy = 2;
        constraints.gridheight = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(10, 10, 2, 20);
        add(labelDate,constraints);

        //from field
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 1.0;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        fieldFrom.setEditable(false);
        constraints.insets = new Insets(2, 0, 2, 20);
        add(fieldFrom,constraints);

        constraints.gridx = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(0, 0, 0, 0);
        add(labelFrom, constraints);

        //cc field
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        fieldCC.setEditable(false);
        constraints.insets = new Insets(2, 0, 2, 20);
        add(fieldCC,constraints);

        constraints.gridx = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(0, 0, 0, 0);
        add(labelCC, constraints);

        //email text Field
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridheight = 5;
        constraints.gridwidth = 3;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(2, 0, 2, 20);
        textAreaMessage.setEditable(false);
        textAreaMessage.setLineWrap(true);
        textAreaMessage.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textAreaMessage);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, constraints);

        System.out.println(mail.hasAttachmentNames());
        System.out.println(mail.getSubject());

        if(mail.hasAttachmentNames()){
            constraints.gridx = 2;
            constraints.gridy = 0;
            constraints.weightx = 1.0;
            constraints.weighty = 0.0;
            constraints.anchor = GridBagConstraints.EAST;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            fieldAttachment.setEditable(false);
            constraints.insets = new Insets(2, 20, 5, 20);
            add(fieldAttachment, constraints);

            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.weightx = 0.0;
            constraints.weighty = 0.0;
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.fill = GridBagConstraints.NONE;
            constraints.insets = new Insets(20, 20, 5, 20);
            add(downloadAttachButton, constraints);
        }
    }

    public JButton getDownloadAttachButton() { return downloadAttachButton; }
    public JButton getGoBackButton() { return goBackButton; }
    public JButton getReplyButton() { return replyButton; }
    public Mail getMail() { return mail; }
    public JFileChooser getSaveAttachment() { return saveAttachment; }
}
