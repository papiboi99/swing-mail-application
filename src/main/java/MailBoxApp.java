import api.SwingMailApi;
import controller.SwingMailController;

import javax.swing.*;

public class MailBoxApp {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingMailApi app = new SwingMailController();
            }
        });
    }
}
