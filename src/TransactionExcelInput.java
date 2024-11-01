import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TransactionExcelInput {
    private TransactionManagement transactionManagement;
    private AccountManagement accountManagement;

    @BeforeMethod
    public void setUp() {
        // Initialize AccountManagement and create test accounts with sample balances
        accountManagement = new AccountManagement();
        accountManagement.createAccount(1001, "Alice", 5000.0); // Initial balance
        accountManagement.createAccount(1002, "Bob", 3000.0);   // Initial balance
        transactionManagement = new TransactionManagement(accountManagement);
    }

    @DataProvider(name = "transactionData")
    public Object[][] readTransactionData() throws IOException {
        FileInputStream file = null;
        Workbook workbook = null;
        ArrayList<Object[]> data = new ArrayList<>();

        try {
            file = new FileInputStream(new File("TransactionData.xlsx"));
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet("TransactionData");

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                int fromAccount = (int) row.getCell(0).getNumericCellValue();
                int toAccount = (int) row.getCell(1).getNumericCellValue();
                double amount = row.getCell(2).getNumericCellValue();
                String expectedResult = row.getCell(3).getStringCellValue();

                data.add(new Object[]{fromAccount, toAccount, amount, expectedResult});
            }
        } finally {
            // Close resources in reverse order
            if (workbook != null) {
                workbook.close();
            }
            if (file != null) {
                file.close();
            }
        }

        return data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "transactionData")
    public void testTransferMoney(int fromAccount, int toAccount, double amount, String expectedResult) {
        // Capture console output (if needed) to check for messages
        String resultMessage;
        if (transactionManagement.checkTransactionLimit(amount)) {
            Account fromAcc = accountManagement.getAccount(fromAccount);
            Account toAcc = accountManagement.getAccount(toAccount);
            if (fromAcc != null && toAcc != null) {
                transactionManagement.transferMoney(fromAccount, toAccount, amount);
                resultMessage = "Transferred $" + amount + " from account " + fromAccount + " to " + toAccount;
            } else {
                resultMessage = "One or both accounts not found.";
            }
        } else {
            resultMessage = "Transfer amount must be greater than 0 and less than $5000.0";
        }

        Assert.assertEquals(resultMessage, expectedResult, "Transfer result did not match expected result.");
    }
}
