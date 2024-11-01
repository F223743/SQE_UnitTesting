import java.util.HashMap;

class Account {
    int accountNumber;
    String accountHolder;
    double balance;

    public Account(int accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account No: " + accountNumber + ", Holder: " + accountHolder + ", Balance: $" + balance;
    }
}

class AccountManagement {
    HashMap<Integer, Account> accounts = new HashMap<>();
    private static final double MIN_BALANCE = 100.0;

    public void createAccount(int accountNumber, String holder, double initialDeposit) {
        if (initialDeposit >= MIN_BALANCE) {
            Account account = new Account(accountNumber, holder, initialDeposit);
            accounts.put(accountNumber, account);
        }
    }

    public Account getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }

    public void updateAccountHolderName(int accountNumber, String newHolderName) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.accountHolder = newHolderName;
        }
    }

    public void deposit(int accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.balance += amount;
        }
    }

    public void withdraw(int accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null && account.balance - amount >= MIN_BALANCE) {
            account.balance -= amount;
        }
    }

    public void closeAccount(int accountNumber) {
        accounts.remove(accountNumber);
    }

    public HashMap<Integer, Account> getAccounts() {
        return accounts;
    }

    // Getter for MIN_BALANCE
    public static double getMinBalance() {
        return MIN_BALANCE;
    }
}
