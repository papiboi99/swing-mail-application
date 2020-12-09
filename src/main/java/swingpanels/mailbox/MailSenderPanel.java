package swingpanels.mailbox;

import model.Mail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MailSenderPanel extends JPanel {

    private JButton sendButton;
    private JButton attachButton;
    private JButton cancelMailButton;
    private JTextArea textAreaMessage;
    private GridBagConstraints constraints;
    private JLabel labelTo;
    private JLabel labelSubject;
    private JLabel labelAttachment;
    private JLabel labelCC;
    private JTextField fieldTo;
    private JTextField fieldSubject;
    private JTextField fieldAttachment;
    private JTextField fieldCC;
    private JFileChooser attachment;
    private String attachmentText;
    private JLabel info;

    private List<File> fileList;

    public MailSenderPanel(){
        super(new GridBagLayout());
        constraints = new GridBagConstraints();
        sendButton = new JButton("Send");
        attachButton = new JButton("Open File");
        cancelMailButton = new JButton("Exit");
        textAreaMessage = new JTextArea(10, 30);
        labelTo = new JLabel("To: ");
        labelSubject = new JLabel("Subject: ");
        labelAttachment = new JLabel("Attachment ");
        labelCC = new JLabel("Cc: ");
        fieldTo = new JTextField(30);
        fieldSubject = new JTextField(30);
        fieldAttachment = new JTextField(30);
        fieldCC = new JTextField(30);
        attachment = new JFileChooser();
        fileList = new ArrayList<>();
        attachmentText = "";
        info = new JLabel("Info");

        setupGUI();
    }

    public MailSenderPanel(Mail mail){
        super(new GridBagLayout());
        constraints = new GridBagConstraints();
        sendButton = new JButton("Send");
        attachButton = new JButton("Open File");
        cancelMailButton = new JButton("Exit");
        textAreaMessage = new JTextArea(10, 30);
        labelTo = new JLabel("To: ");
        labelSubject = new JLabel("Subject: ");
        labelAttachment = new JLabel("Attachment ");
        labelCC = new JLabel("Cc: ");
        fieldTo = new JTextField(mail.getFromAddress(),30);
        fieldSubject = new JTextField("Re: "+mail.getSubject(),30);
        fieldAttachment = new JTextField(30);
        fieldCC = new JTextField(30);
        attachment = new JFileChooser();
        fileList = new ArrayList<>();
        attachmentText = "";
        info = new JLabel("Info");

        setupGUI();
    }

    public void setupGUI() {

        //campo del destinatario
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(labelTo, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(fieldTo, constraints);

        //campo CC
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        add(labelCC, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(fieldCC, constraints);

        //campo de asunto
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        add(labelSubject, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 0, 0, 0);
        add(fieldSubject, constraints);

        //campo attachment // probamos seleccionar archivo
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(labelAttachment, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(fieldAttachment, constraints);

        constraints.gridx = 2;
        constraints.weightx = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        add(attachButton, constraints);

        //campo boton enviar
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.ipady = 10;
        constraints.ipadx = 20;
        add(sendButton, constraints);

        //campo boton cancelar
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.ipady = 0;
        constraints.ipadx = 0;
        add(cancelMailButton,constraints);

        //texto a enviar
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.ipady = 0;
        constraints.ipadx = 0;
        constraints.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(textAreaMessage), constraints);

        //info
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        add(info,constraints);

    }

    public JButton getAttachButton() { return attachButton; }
    public JTextArea getTextAreaMessage() { return textAreaMessage; }
    public JTextField getFieldTo() { return fieldTo; }
    public JTextField getFieldSubject() { return fieldSubject; }
    public JTextField getFieldCC() { return fieldCC; }
    public JButton getCancelMailButton() {
        return cancelMailButton;
    }
    public JButton getSendButton() {
        return sendButton;
    }
    public JTextField getFieldAttachment() { return fieldAttachment; }
    public JFileChooser getAttachment() { return attachment; }
    public File[] getFiles() { return fileList.toArray(new File[0]); }
    public void addFile(File file){
        fileList.add(file);
        String fileName = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\") + 1);

        if(attachmentText == ""){
            attachmentText = fileName;
        }else{
            attachmentText += ", " + fileName;
        }
        fieldAttachment.setText(this.attachmentText);
    }

    public JLabel getInfo() { return info; }
}
