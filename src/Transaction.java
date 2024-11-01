import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    int fromAccount;
    int toAccount;
    double amount;

    public Transaction(int fromAccount, int toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer from account " + fromAccount + " to account " + toAccount + " of amount $" + amount;
    }
}

class TransactionManagement {
    ArrayList<Transaction> transactions = new ArrayList<>();
    AccountManagement accountManagement;
    Scanner scanner = new Scanner(System.in);
    private static final double MAX_TRANSACTION_LIMIT = 5000.0;

    public TransactionManagement(AccountManagement accountManagement) {
        this.accountManagement = accountManagement;
    }

    // 1. Transfer money between accounts with user input
    public void transferMoney() {
        System.out.print("Enter source account number: ");
        int fromAccount = scanner.nextInt();
        System.out.print("Enter destination account number: ");
        int toAccount = scanner.nextInt();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        if (amount > 0 && amount <= MAX_TRANSACTION_LIMIT) {
            Account fromAcc = accountManagement.getAccount(fromAccount);
            Account toAcc = accountManagement.getAccount(toAccount);

            if (fromAcc != null && toAcc != null) {
                if (fromAcc.balance - amount >= AccountManagement.getMinBalance()) {
                    fromAcc.balance -= amount;
                    toAcc.balance += amount;
                    transactions.add(new Transaction(fromAccount, toAccount, amount));
                    System.out.println("Transferred $" + amount + " from account " + fromAccount + " to " + toAccount);
                } else {
                    System.out.println("Insufficient funds. Minimum balance of $" + AccountManagement.getMinBalance() + " must be maintained.");
                }
            } else {
                System.out.println("One or both accounts not found.");
            }
        } else {
            System.out.println("Transfer amount must be greater than 0 and less than $" + MAX_TRANSACTION_LIMIT);
        }
    }

    // 2. Refund transaction with user input
    public void refundTransaction() {
        System.out.print("Enter source account number: ");
        int fromAccount = scanner.nextInt();
        System.out.print("Enter destination account number: ");
        int toAccount = scanner.nextInt();
        System.out.print("Enter refund amount: ");
        double amount = scanner.nextDouble();

        transferMoney(toAccount, fromAccount, amount);
        System.out.println("Refunded $" + amount + " from account " + toAccount + " to " + fromAccount);
    }

    // Overloaded transferMoney method to handle refunds
    public void transferMoney(int fromAccount, int toAccount, double amount) {
        if (amount > 0 && amount <= MAX_TRANSACTION_LIMIT) {
            Account fromAcc = accountManagement.getAccount(fromAccount);
            Account toAcc = accountManagement.getAccount(toAccount);

            if (fromAcc != null && toAcc != null) {
                if (fromAcc.balance - amount >= AccountManagement.getMinBalance()) {
                    fromAcc.balance -= amount;
                    toAcc.balance += amount;
                    transactions.add(new Transaction(fromAccount, toAccount, amount));
                    System.out.println("Transferred $" + amount + " from account " + fromAccount + " to " + toAccount);
                } else {
                    System.out.println("Insufficient funds. Minimum balance of $" + AccountManagement.getMinBalance() + " must be maintained.");
                }
            } else {
                System.out.println("One or both accounts not found.");
            }
        } else {
            System.out.println("Transfer amount must be greater than 0 and less than $" + MAX_TRANSACTION_LIMIT);
        }
    }

    // 3. Check transaction limit
    public boolean checkTransactionLimit(double amount) {
        if (amount > MAX_TRANSACTION_LIMIT) {
            System.out.println("Transaction amount exceeds the maximum limit of $" + MAX_TRANSACTION_LIMIT);
            return false;
        }
        return true;
    }

    // 4. List all transactions
    public List<Transaction> listTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
		return transactions;
    }

    // 5. Void a transaction (undo)
    public void voidTransaction() {
        System.out.print("Enter source account number: ");
        int fromAccount = scanner.nextInt();
        System.out.print("Enter destination account number: ");
        int toAccount = scanner.nextInt();
        System.out.print("Enter amount to void: ");
        double amount = scanner.nextDouble();

        Account fromAcc = accountManagement.getAccount(toAccount);
        Account toAcc = accountManagement.getAccount(fromAccount);

        if (fromAcc != null && toAcc != null && amount > 0) {
            fromAcc.balance -= amount;
            toAcc.balance += amount;
            System.out.println("Voided transaction. $" + amount + " returned to account " + fromAccount);
        } else {
            System.out.println("Transaction void failed.");
        }
    }
}
