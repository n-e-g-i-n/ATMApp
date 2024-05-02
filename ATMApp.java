import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class ATMApp {
    private static int balance = 100000;
    private static String password = "1234";
    private static ResourceBundle messages;

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        Locale[] supportedLocales = {new Locale("en", "US"), new Locale("fa", "IR")};
        messages = ResourceBundle.getBundle("MessagesBundle", supportedLocales[0]);

        JFrame frame = new JFrame(messages.getString("title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel passwordLabel = new JLabel(messages.getString("enter_password"));
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton(messages.getString("login"));

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                char[] inputPassword = passwordField.getPassword();
                String enteredPassword = new String(inputPassword);
                if (enteredPassword.equals(password)) {
                    showMainMenu();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, messages.getString("incorrect_password"));
                }
            }
        });

        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void showMainMenu() {
        JFrame mainMenuFrame = new JFrame(messages.getString("main_menu"));
        mainMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainMenuFrame.setSize(400, 300);

        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(4, 1));

        // Add buttons to the panel
        JButton withdrawButton = new JButton(messages.getString("withdraw"));
        JButton depositButton = new JButton(messages.getString("deposit"));
        JButton checkBalanceButton = new JButton(messages.getString("check_balance"));
        JButton changePasswordButton = new JButton(messages.getString("change_password"));

        withdrawButton.addActionListener(e -> performWithdraw());
        depositButton.addActionListener(e -> performDeposit());
        checkBalanceButton.addActionListener(e -> displayBalance());
        changePasswordButton.addActionListener(e -> changePassword());

        mainMenuPanel.add(withdrawButton);
        mainMenuPanel.add(depositButton);
        mainMenuPanel.add(checkBalanceButton);
        mainMenuPanel.add(changePasswordButton);

        mainMenuFrame.add(mainMenuPanel);
        mainMenuFrame.setVisible(true);
    }

    private static void performWithdraw() {
        String input = JOptionPane.showInputDialog("Enter withdrawal amount:");
        try {
            int withdrawAmount = Integer.parseInt(input);
            if (withdrawAmount > 0 && balance >= withdrawAmount) {
                balance -= withdrawAmount;
                JOptionPane.showMessageDialog(null, "Please collect your money.");
                displayBalance();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid withdrawal amount or insufficient balance.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.");
        }
    }

    private static void performDeposit() {
        String input = JOptionPane.showInputDialog("Enter deposit amount:");
        try {
            int depositAmount = Integer.parseInt(input);
            if (depositAmount > 0) {
                balance += depositAmount;
                JOptionPane.showMessageDialog(null, "Your money has been successfully deposited.");
                displayBalance();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid deposit amount.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.");
        }
    }

    private static void displayBalance() {
        JOptionPane.showMessageDialog(null, "Balance: $" + balance);
    }

    private static void changePassword() {
        String newPassword = JOptionPane.showInputDialog("Enter new password:");
        if (newPassword != null && !newPassword.isEmpty()) {
            password = newPassword;
            JOptionPane.showMessageDialog(null, "Password changed successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid password. Please try again.");
        }
    }
}
