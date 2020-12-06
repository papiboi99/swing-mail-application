package swingpanels;

import swingpanels.mailbox.MailListPanel;
import swingpanels.mailbox.MailPanel;

import javax.swing.*;
import java.awt.*;

public class MailboxPanel extends JPanel {
    // Define the card manager for MailList and Mail panels
    private CardLayout contentCL;

    // Define the layout configurations
    private GridBagConstraints constraints;

    // Define components
    private JTextField searchField;
    private JLabel logoLabel;
    private JButton searchButton;
    private JButton logoutButton;
    private JButton sendButton;
    private JList<String> foldersList;
    private JPanel contentPanel;
    private MailListPanel mailListPanel;
    private MailPanel mailPanel;

    public MailboxPanel() {
        super(new GridBagLayout());
        constraints = new GridBagConstraints();

        // Create the CardLayout
        contentCL = new CardLayout();

        // Create components
        searchField = new JTextField("Search mail...",30);
        searchButton = new JButton("\uD83D\uDD0D");
        logoutButton = new JButton("Logout");
        sendButton = new JButton("✉ Compose");
        contentPanel = new JPanel(contentCL);
        mailListPanel = new MailListPanel();
        mailPanel = new MailPanel();

        // Creating the list
        DefaultListModel<String> foldersListModel = new DefaultListModel<>();
        foldersListModel.addElement("\uD83D\uDCE5 Inbox");
        foldersListModel.addElement("\uD83D\uDCE4 Sent");
        foldersListModel.addElement("\uD83D\uDCDB Spam");
        foldersListModel.addElement("\uD83D\uDDD1 Trash");
        foldersList = new JList<>(foldersListModel);

        // Creating and scaling the logo
        logoLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/media/" +
                "papiboi99_logo.png")).getImage().getScaledInstance(150, 75, Image.SCALE_SMOOTH)));

        setupGUI();
    }

    public void setupGUI(){
        // Set up the card layout
        contentPanel.add(mailListPanel, "1");
        contentPanel.add(mailPanel, "2");
        contentCL.show(contentPanel, "1");

        // // Add the components to the panel (first row will be different)
        setBackground(Color.WHITE);

        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 20, 0, 5);
        add(logoLabel, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 10;
        constraints.ipadx = 40;
        constraints.insets = new Insets(0, 5, 0, 0);
        add(searchField, constraints);

        constraints.gridx = 2;
        constraints.ipadx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(0, 2, 0, 5);
        add(searchButton, constraints);

        constraints.gridx = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(0, 5, 0, 0);
        add(logoutButton, constraints);

        JPanel subPanel = new JPanel(new GridBagLayout());
        subPanel.setBackground(Color.LIGHT_GRAY);

        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 40, 10, 10);
        subPanel.add(sendButton, constraints);

        constraints.weighty = 1.0;
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 40, 40, 10);
        subPanel.add(foldersList, constraints);

        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.insets = new Insets(10, 10, 40, 40);
        subPanel.add(mailListPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth=4;
        constraints.insets = new Insets(0, 40, 20, 40);
        add(subPanel, constraints);
    }
}
