package swingpanels;


import javax.swing.*;
import java.awt.*;


public class LoginPanel extends JPanel {
    // Define the layout configurations
    private GridBagConstraints constraints;

    // Define components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel titleLabel;
    private JLabel logoLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JLabel errorLabel;

    public LoginPanel() {
        // The login panel will be managed by GridBagLayout
        super(new GridBagLayout());
        constraints = new GridBagConstraints();

        // Create components
        usernameField = new JTextField(25);
        passwordField = new JPasswordField(25);
        usernameLabel = new JLabel("Email address:");
        passwordLabel = new JLabel("Password:");
        titleLabel = new JLabel("Sign in using your Gmail Account");
        loginButton = new JButton("Sign in");
        errorLabel = new JLabel();

        // Creating and scaling the logo
        logoLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/media/" +
                "papiboi99_logo.png")).getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH)));

        setupGUI();
    }

    public void setupGUI(){
        setBackground(Color.WHITE);

        // Add the components to the panel
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(40, 40, 2, 40);
        add(logoLabel, constraints);

        constraints.gridy = 1;
        titleLabel.setFont(new java.awt.Font("Calibri", Font.BOLD,20));
        constraints.insets = new Insets(2, 40, 10, 40);
        add(titleLabel, constraints);

        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        constraints.fill = GridBagConstraints.NONE;
        usernameLabel.setFont(new java.awt.Font("Calibri", Font.PLAIN,14));
        constraints.insets = new Insets(10, 40, 0, 40);
        add(usernameLabel, constraints);

        constraints.gridy = 4;
        passwordLabel.setFont(new java.awt.Font("Calibri", Font.PLAIN,14));
        constraints.insets = new Insets(5, 40, 0, 40);
        add(passwordLabel, constraints);

        constraints.gridy = 3;
        constraints.ipady = 8;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 40, 5, 40);
        add(usernameField, constraints);

        constraints.gridy = 5;
        constraints.insets = new Insets(0, 40, 10, 40);
        add(passwordField, constraints);

        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 40, 40, 40);
        loginButton.setFont(new java.awt.Font("Calibri", Font.BOLD,14));
        add(loginButton, constraints);
    }

    public void authFailed (){
        errorLabel.setText("<html><font color=red size=3><b>" +
                "Incorrect username or password</b></html>");

        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(2, 40, 10, 40);
        add(errorLabel, constraints);

        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 40, 40, 40);
        add(loginButton, constraints);
    }

    public void noAddressWritten(){
        errorLabel.setText("<html><font color=red size=3><b>" +
                "Please write a valid email address</b></html>");

        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(2, 40, 10, 40);
        add(errorLabel, constraints);

        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 40, 40, 40);
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
