import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

public class AccountManagementTest {
    private AccountManagement accountManagement;

    @BeforeMethod
    public void setUp() {
        accountManagement = new AccountManagement();
    }

    // Test for createAccount method
    @Test
    public void testCreateAccountWithValidInitialDeposit() {
        accountManagement.createAccount(12345, "John Doe", 150.0);
        Account account = accountManagement.getAccount(12345);
        
        Assert.assertNotNull(account, "Account should be created.");
        Assert.assertEquals(account.accountHolder, "John Doe", "Account holder mismatch.");
        Assert.assertEquals(account.balance, 150.0, "Initial deposit should match the account balance.");
    }

    @Test
    public void testCreateAccountWithInsufficientInitialDeposit() {
        accountManagement.createAccount(12346, "Jane Doe", 50.0);
        Account account = accountManagement.getAccount(12346);

        Assert.assertNull(account, "Account should not be created with insufficient initial deposit.");
    }

    // Test for getAccount method
    @Test
    public void testGetExistingAccount() {
        accountManagement.createAccount(12345, "John Doe", 150.0);
        Account account = accountManagement.getAccount(12345);

        Assert.assertNotNull(account, "Account should be retrievable.");
        Assert.assertEquals(account.accountNumber, 12345, "Account number mismatch.");
    }

    @Test
    public void testGetNonExistingAccount() {
        Account account = accountManagement.getAccount(99999);

        Assert.assertNull(account, "Non-existing account should return null.");
    }

    // Test for updateAccountHolderName method
    @Test
    public void testUpdateAccountHolderNameForExistingAccount() {
        accountManagement.createAccount(12345, "John Doe", 150.0);
        accountManagement.updateAccountHolderName(12345, "John Smith");

        Account account = accountManagement.getAccount(12345);
        Assert.assertEquals(account.accountHolder, "John Smith", "Account holder name should be updated.");
    }

    @Test
    public void testUpdateAccountHolderNameForNonExistingAccount() {
        accountManagement.updateAccountHolderName(99999, "Non Existing");

        Account account = accountManagement.getAccount(99999);
        Assert.assertNull(account, "Non-existing account should not be updated.");
    }

    // Test for deposit method
    @Test
    public void testDepositToExistingAccount() {
        accountManagement.createAccount(12345, "John Doe", 150.0);
        accountManagement.deposit(12345, 50.0);

        Account account = accountManagement.getAccount(12345);
        Assert.assertEquals(account.balance, 200.0, "Balance should be updated after deposit.");
    }

    @Test
    public void testDepositToNonExistingAccount() {
        accountManagement.deposit(99999, 50.0);

        Account account = accountManagement.getAccount(99999);
        Assert.assertNull(account, "Deposit to non-existing account should have no effect.");
    }

    // Test for withdraw method
    @Test
    public void testWithdrawFromExistingAccountWithSufficientBalance() {
        accountManagement.createAccount(12345, "John Doe", 200.0);
        accountManagement.withdraw(12345, 50.0);

        Account account = accountManagement.getAccount(12345);
        Assert.assertEquals(account.balance, 150.0, "Balance should be reduced after withdrawal.");
    }

    @Test
    public void testWithdrawFromExistingAccountWithInsufficientBalance() {
        accountManagement.createAccount(12345, "John Doe", 150.0);
        accountManagement.withdraw(12345, 60.0); // This should leave balance below minimum balance

        Account account = accountManagement.getAccount(12345);
        Assert.assertEquals(account.balance, 150.0, "Withdrawal should not occur if it results in a balance below minimum.");
    }

    @Test
    public void testWithdrawFromNonExistingAccount() {
        accountManagement.withdraw(99999, 50.0);

        Account account = accountManagement.getAccount(99999);
        Assert.assertNull(account, "Withdrawal from non-existing account should have no effect.");
    }

    // Test for closeAccount method
    @Test
    public void testCloseExistingAccount() {
        accountManagement.createAccount(12345, "John Doe", 150.0);
        accountManagement.closeAccount(12345);

        Account account = accountManagement.getAccount(12345);
        Assert.assertNull(account, "Closed account should not be retrievable.");
    }

    @Test
    public void testCloseNonExistingAccount() {
        accountManagement.closeAccount(99999);

        Account account = accountManagement.getAccount(99999);
        Assert.assertNull(account, "Non-existing account should remain null after close attempt.");
    }

    // Test for getAccounts method
    @Test
    public void testGetAccounts() {
        accountManagement.createAccount(12345, "John Doe", 150.0);
        accountManagement.createAccount(67890, "Jane Doe", 200.0);

        HashMap<Integer, Account> accounts = accountManagement.getAccounts();
        Assert.assertEquals(accounts.size(), 2, "Number of accounts mismatch.");
        Assert.assertTrue(accounts.containsKey(12345), "Accounts should contain account number 12345.");
        Assert.assertTrue(accounts.containsKey(67890), "Accounts should contain account number 67890.");
    }

    // Test for getMinBalance static method
    @Test
    public void testGetMinBalance() {
        Assert.assertEquals(AccountManagement.getMinBalance(), 100.0, "Minimum balance should be 100.0.");
    }
}
