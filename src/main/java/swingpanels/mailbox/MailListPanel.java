package swingpanels.mailbox;

import model.Mail;
import model.MailListTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class MailListPanel extends JPanel {
    // Define the principal table and its utilities for the list of mails
    private JScrollPane scrollPane;
    private JTable mailListTable;
    private MailListTableModel mailListTableModel;
    private JPopupMenu actionPopup;
    private JMenuItem menuItemOpen;
    private JMenuItem menuItemReply;
    private JMenuItem menuItemRemove;

    // Define components
    private JButton reloadButton;
    private JButton deleteButton;

    // Define the layout configurations
    private GridBagConstraints constraints;

    public MailListPanel(List<Mail> mailList) {
        super(new GridBagLayout());
        constraints = new GridBagConstraints();

        // Create the table and utilities
        Collections.reverse(mailList);
        mailListTableModel = new MailListTableModel(mailList);
        mailListTable = new JTable(mailListTableModel);

        // The popup menu when right click
        actionPopup = new JPopupMenu();
        menuItemOpen = new JMenuItem("\uD83D\uDCC3 Open");
        menuItemRemove = new JMenuItem("\uD83D\uDDD1 Move to trash");
        menuItemReply = new JMenuItem("↩ Reply");
        actionPopup.add(menuItemOpen);
        actionPopup.add(menuItemRemove);
        actionPopup.add(menuItemReply);
        mailListTable.setComponentPopupMenu(actionPopup);

        // Create the components
        reloadButton = new JButton("\uD83D\uDD04");
        deleteButton = new JButton("\uD83D\uDDD1");

        setupGUI();
    }

    public void setupGUI() {

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, 20, 5, 5);
        add(reloadButton, constraints);

        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 20, 5, 5);
        add(deleteButton, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 20, 0, 5);
        mailListTable.setTableHeader(null);
        mailListTable.setShowVerticalLines(false);
        mailListTable.setDefaultRenderer(String.class, new CustomCellRenderer());
        scrollPane = new JScrollPane(mailListTable);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, constraints);
        validate();
        repaint();
    }

    public void reloadMailList(List<Mail> mailList){
        // Reloads the table
        mailListTableModel = new MailListTableModel(mailList);
        mailListTable.setModel(mailListTableModel);

        validate();
        repaint();
    }

    public MailListTableModel getMailListTableModel() { return mailListTableModel; }
    public JTable getMailListTable() { return mailListTable; }
    public JMenuItem getMenuItemOpen() { return menuItemOpen; }
    public JMenuItem getMenuItemReply() { return menuItemReply; }
    public JMenuItem getMenuItemRemove() { return menuItemRemove; }
    public JButton getReloadButton() { return reloadButton; }
    public JButton getDeleteButton() { return deleteButton; }

    public class CustomCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBorder(noFocusBorder);
            return this;
        }
    }
}
