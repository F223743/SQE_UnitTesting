import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class AccountsXMLtestcases {
    AccountManagement accountManagement;

    @BeforeClass
    public void setUp() {
        // Initialize AccountManagement before running any tests
        accountManagement = new AccountManagement();
        
        // Create accounts to avoid failures in test cases
        accountManagement.createAccount(12345, "John Doe", 500.0); // Create an account with sufficient balance
        accountManagement.createAccount(67890, "Jane Smith", 150.0); // Create another account
    }

    // Test case for creating an account
    @Test
    public void createAccount() {
        Account account = accountManagement.getAccount(12345);
        Assert.assertNotNull(account, "Account should be created successfully for account number 12345.");
        Assert.assertEquals(account.accountHolder, "John Doe", "Account holder should be John Doe.");
        Assert.assertEquals(account.balance, 500.0, "Account balance should be $500.0.");
    }

    // Test case for retrieving account details by account number
    @Test
    @Parameters({"accountNumber"})
    public void getAccountDetails(@Optional("67890") int accountNumber) {
        Account account = accountManagement.getAccount(accountNumber);
        Assert.assertNotNull(account, "Account should exist for account: " + accountNumber);
        System.out.println("Account Details: " + account);
    }

    // Test case for updating account holder name
    @Test
    public void updateAccountHolderName() {
        accountManagement.updateAccountHolderName(12345, "Johnathan Doe");
        Account account = accountManagement.getAccount(12345);
        Assert.assertEquals(account.accountHolder, "Johnathan Doe", "Account holder name should be updated to Johnathan Doe.");
    }

    // Test case for depositing money
    @Test
    public void deposit() {
        accountManagement.deposit(67890, 100.0);
        Account account = accountManagement.getAccount(67890);
        Assert.assertEquals(account.balance, 250.0, "Account balance should be updated to $250.0 after deposit.");
    }

    // Test case for withdrawing money
    @Test
    public void withdraw() {
        accountManagement.withdraw(67890, 50.0);
        Account account = accountManagement.getAccount(67890);
        Assert.assertEquals(account.balance, 200.0, "Account balance should be updated to $200.0 after withdrawal.");
    }

    // Test case for closing an account
    @Test
    public void closeAccount() {
        accountManagement.closeAccount(67890);
        Account account = accountManagement.getAccount(67890);
        Assert.assertNull(account, "Account should be closed and not found.");
    }
}
