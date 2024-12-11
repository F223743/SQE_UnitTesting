import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.util.List;

public class TransactionTest {
    private TransactionManagement transactionManagement;
    private AccountManagement accountManagement;

    @BeforeClass
    public void setup() {
        accountManagement = new AccountManagement();
        accountManagement.createAccount(1001, "Sara", 500.0); 
        accountManagement.createAccount(1002, "Ali", 300.0);   
        transactionManagement = new TransactionManagement(accountManagement);
    }

    @DataProvider(name = "transferData")
    public Object[][] transferData() {
        return new Object[][] {
            { 1001, 1002, 500.0 },
            { 1002, 1001, 1000.0 },
            { 1001, 1002, 2000.0 },
        };
    }

    @DataProvider(name = "refundData")
    public Object[][] refundData() {
        return new Object[][] {
            { 1002, 1001, 200.0 },
            { 1001, 1002, 300.0 },
        };
    }

    @DataProvider(name = "transactionLimitData")
    public Object[][] transactionLimitData() {
        return new Object[][] {
            { 4000.0, true },
            { 6000.0, false },
            { 5000.0, true },
        };
    }

    @DataProvider(name = "voidTransactionData")
    public Object[][] voidTransactionData() {
        return new Object[][] {
            { 1001, 1002, 500.0 },
            { 1002, 1001, 300.0 },
        };
    }

    @Test(dataProvider = "transferData")
    public void testTransferMoney(int fromAccount, int toAccount, double amount) {
        double initialFromBalance = accountManagement.getAccount(fromAccount).balance;
        double initialToBalance = accountManagement.getAccount(toAccount).balance;

        transactionManagement.transferMoney(fromAccount, toAccount, amount);

        assertEquals(accountManagement.getAccount(fromAccount).balance, initialFromBalance - amount);
        assertEquals(accountManagement.getAccount(toAccount).balance, initialToBalance + amount);
    }

    @Test(dataProvider = "refundData")
    public void testRefundTransaction(int fromAccount, int toAccount, double amount) {
        double initialFromBalance = accountManagement.getAccount(fromAccount).balance;
        double initialToBalance = accountManagement.getAccount(toAccount).balance;

        transactionManagement.refundTransaction();

        assertEquals(accountManagement.getAccount(fromAccount).balance, initialFromBalance + amount);
        assertEquals(accountManagement.getAccount(toAccount).balance, initialToBalance - amount);
    }

    @Test(dataProvider = "transactionLimitData")
    public void testCheckTransactionLimit(double amount, boolean expected) {
        boolean result = transactionManagement.checkTransactionLimit(amount);
        assertEquals(result, expected);
    }

    @Test
    public void testListTransactions() {
        List<Transaction> transactions = transactionManagement.listTransactions();
        assertNotNull(transactions, "Transaction list should not be null");
    }

    @Test(dataProvider = "voidTransactionData")
    public void testVoidTransaction(int fromAccount, int toAccount, double amount) {
        double initialFromBalance = accountManagement.getAccount(fromAccount).balance;
        double initialToBalance = accountManagement.getAccount(toAccount).balance;

        transactionManagement.voidTransaction();

        assertEquals(accountManagement.getAccount(fromAccount).balance, initialFromBalance + amount);
        assertEquals(accountManagement.getAccount(toAccount).balance, initialToBalance - amount);
    }
}
