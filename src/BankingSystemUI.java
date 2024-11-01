import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingSystemUI extends JFrame implements ActionListener {
    private AccountManagement accountManagement;
    private TransactionManagement transactionManagement;
    private LoanManagement loanManagement;

    public BankingSystemUI() {
        accountManagement = new AccountManagement();
        transactionManagement = new TransactionManagement(accountManagement);
        loanManagement = new LoanManagement();

        setTitle("Banking Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title Label at the Top-Center
        JLabel titleLabel = new JLabel("Banking System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Main Menu Panel
        JPanel menuPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton createAccountButton = new JButton("Create Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer");
        JButton applyLoanButton = new JButton("Apply Loan");
        JButton listAccountsButton = new JButton("List Accounts");

        createAccountButton.addActionListener(e -> openCreateAccountWindow());
        depositButton.addActionListener(e -> openDepositWindow());
        withdrawButton.addActionListener(e -> openWithdrawWindow());
        transferButton.addActionListener(e -> openTransferWindow());
        applyLoanButton.addActionListener(e -> openApplyLoanWindow());
        listAccountsButton.addActionListener(e -> openListAccountsWindow());

        menuPanel.add(createAccountButton);
        menuPanel.add(depositButton);
        menuPanel.add(withdrawButton);
        menuPanel.add(transferButton);
        menuPanel.add(applyLoanButton);
        menuPanel.add(listAccountsButton);

        add(menuPanel, BorderLayout.CENTER);
    }

    private void openCreateAccountWindow() {
        JFrame frame = new JFrame("Create Account");
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JTextField accountNumberField = new JTextField(10);
        JTextField holderField = new JTextField(10);
        JTextField depositField = new JTextField(10);

        frame.add(new JLabel("Account Number:"));
        frame.add(accountNumberField);
        frame.add(new JLabel("Account Holder:"));
        frame.add(holderField);
        frame.add(new JLabel("Deposit:"));
        frame.add(depositField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                int accountNumber = Integer.parseInt(accountNumberField.getText());
                String holder = holderField.getText();
                double initialDeposit = Double.parseDouble(depositField.getText());
                accountManagement.createAccount(accountNumber, holder, initialDeposit);
                JOptionPane.showMessageDialog(frame, "Account created for " + holder);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(submitButton);
        frame.setVisible(true);
    }

    private void openDepositWindow() {
        JFrame frame = new JFrame("Deposit");
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JTextField accountNumberField = new JTextField(10);
        JTextField depositField = new JTextField(10);

        frame.add(new JLabel("Account Number:"));
        frame.add(accountNumberField);
        frame.add(new JLabel("Deposit Amount:"));
        frame.add(depositField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                int accountNumber = Integer.parseInt(accountNumberField.getText());
                double amount = Double.parseDouble(depositField.getText());
                accountManagement.deposit(accountNumber, amount);
                JOptionPane.showMessageDialog(frame, "Deposited $" + amount + " to account " + accountNumber);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(submitButton);
        frame.setVisible(true);
    }

    private void openWithdrawWindow() {
        JFrame frame = new JFrame("Withdraw");
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        JTextField accountNumberField = new JTextField(10);
        JTextField withdrawField = new JTextField(10);

        frame.add(new JLabel("Account Number:"));
        frame.add(accountNumberField);
        frame.add(new JLabel("Withdraw Amount:"));
        frame.add(withdrawField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                int accountNumber = Integer.parseInt(accountNumberField.getText());
                double amount = Double.parseDouble(withdrawField.getText());
                accountManagement.withdraw(accountNumber, amount);
                JOptionPane.showMessageDialog(frame, "Withdrew $" + amount + " from account " + accountNumber);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(submitButton);
        frame.setVisible(true);
    }

    private void openTransferWindow() {
        JFrame frame = new JFrame("Transfer");
        frame.setSize(350, 200);
        frame.setLayout(new FlowLayout());

        JTextField fromField = new JTextField(10);
        JTextField toField = new JTextField(10);
        JTextField amountField = new JTextField(10);

        frame.add(new JLabel("From Account:"));
        frame.add(fromField);
        frame.add(new JLabel("To Account:"));
        frame.add(toField);
        frame.add(new JLabel("Amount:"));
        frame.add(amountField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                int fromAccount = Integer.parseInt(fromField.getText());
                int toAccount = Integer.parseInt(toField.getText());
                double amount = Double.parseDouble(amountField.getText());
                transactionManagement.transferMoney(fromAccount, toAccount, amount);
                JOptionPane.showMessageDialog(frame, "Transferred $" + amount + " from " + fromAccount + " to " + toAccount);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(submitButton);
        frame.setVisible(true);
    }

    private void openApplyLoanWindow() {
        JFrame frame = new JFrame("Apply Loan");
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JTextField accountNumberField = new JTextField(10);
        JTextField loanAmountField = new JTextField(10);
        JTextField interestRateField = new JTextField(10);

        frame.add(new JLabel("Account Number:"));
        frame.add(accountNumberField);
        frame.add(new JLabel("Loan Amount:"));
        frame.add(loanAmountField);
        frame.add(new JLabel("Interest Rate:"));
        frame.add(interestRateField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                int accountNumber = Integer.parseInt(accountNumberField.getText());
                double loanAmount = Double.parseDouble(loanAmountField.getText());
                double interestRate = Double.parseDouble(interestRateField.getText());
                loanManagement.applyLoan(accountNumber, loanAmount, interestRate);
                JOptionPane.showMessageDialog(frame, "Loan applied for account " + accountNumber);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(submitButton);
        frame.setVisible(true);
    }

    private void openListAccountsWindow() {
        JFrame frame = new JFrame("List Accounts");
        frame.setSize(400, 300);
        JTextArea accountsArea = new JTextArea(15, 30);
        accountsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(accountsArea);
        frame.add(scrollPane);

        StringBuilder accountsList = new StringBuilder("Accounts:\n");
        for (Account account : accountManagement.getAccounts().values()) {
            accountsList.append(account.toString()).append("\n");
        }
        accountsArea.setText(accountsList.toString());

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Individual button listeners handle actions
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankingSystemUI().setVisible(true));
    }
}
