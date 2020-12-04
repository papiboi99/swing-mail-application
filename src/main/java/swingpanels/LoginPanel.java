package swingpanels;

import javax.swing.*;
import java.awt.*;


public class LoginPanel extends JPanel {

    // Define components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private  JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;

    public LoginPanel() {
        // The login panel will be managed by GridBagLayout
        super(new GridBagLayout());

        // Create components
        usernameField = new JTextField(25);
        passwordField = new JPasswordField(25);
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        titleLabel = new JLabel("Sign in using your Gmail Account");
        loginButton = new JButton("Login");

        createGUI();
    }

    public void createGUI(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Add the components to the panel (from upper to lower)
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(titleLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(usernameLabel, constraints);

        constraints.gridx = 1;
        add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        add(passwordLabel, constraints);

        constraints.gridx = 1;
        add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(loginButton, constraints);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

}
