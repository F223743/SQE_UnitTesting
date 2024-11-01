import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.util.List;

class TransactionManagementTest {
    private TransactionManagement transactionManagement;
    private AccountManagement accountManagement;

    @BeforeClass
    public void setup() {
        accountManagement = new AccountManagement();
        accountManagement.createAccount(1001, "Alice", 5000.0); // Initial balance
        accountManagement.createAccount(1002, "Bob", 3000.0);   // Initial balance
        transactionManagement = new TransactionManagement(accountManagement);
    }

    @Test
    @Parameters({"fromAccount", "toAccount", "amount"})
    public void testTransferMoney(int fromAccount, int toAccount, double amount) {
        double initialFromBalance = accountManagement.getAccount(fromAccount).balance;
        double initialToBalance = accountManagement.getAccount(toAccount).balance;

        transactionManagement.transferMoney(fromAccount, toAccount, amount);

        assertEquals(accountManagement.getAccount(fromAccount).balance, initialFromBalance - amount);
        assertEquals(accountManagement.getAccount(toAccount).balance, initialToBalance + amount);
    }

    @Test
    @Parameters({"fromAccount", "toAccount", "amount"})
    public void testRefundTransaction(int fromAccount, int toAccount, double amount) {
        // First, perform the transfer
        transactionManagement.transferMoney(fromAccount, toAccount, amount);

        // Now perform the refund
        transactionManagement.refundTransaction(); // Prompt user for index, implement accordingly in method

        // Validate balances after refund
        double initialFromBalance = accountManagement.getAccount(fromAccount).balance + amount;
        double initialToBalance = accountManagement.getAccount(toAccount).balance - amount;

        assertEquals(accountManagement.getAccount(fromAccount).balance, initialFromBalance);
        assertEquals(accountManagement.getAccount(toAccount).balance, initialToBalance);
    }

    @Test
    @Parameters({"amount"})
    public void testCheckTransactionLimit(double amount) {
        boolean result = transactionManagement.checkTransactionLimit(amount);
        System.out.println("Transaction limit check for amount $" + amount + ": " + result);
        assertTrue(result, "Transaction limit should be within the allowable range");
    }

    @Test
    public void testListTransactions() {
        List<Transaction> transactions = transactionManagement.listTransactions();
        assertNotNull(transactions, "Transaction list should not be null");
        assertFalse(transactions.isEmpty(), "Transaction list should not be empty");
    }

    @Test
    @Parameters({"fromAccount", "toAccount", "amount"})
    public void testVoidTransaction(int fromAccount, int toAccount, double amount) {
        transactionManagement.transferMoney(fromAccount, toAccount, amount); // Make a transfer first

        // Execute the void transaction
        transactionManagement.voidTransaction(); // Prompt user input for actual implementation

        // Validate balances after voiding the transaction
        double initialFromBalance = accountManagement.getAccount(fromAccount).balance + amount;
        double initialToBalance = accountManagement.getAccount(toAccount).balance - amount;

        assertEquals(accountManagement.getAccount(fromAccount).balance, initialFromBalance);
        assertEquals(accountManagement.getAccount(toAccount).balance, initialToBalance);
    }
}