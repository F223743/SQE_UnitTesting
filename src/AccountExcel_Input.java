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

public class AccountExcel_Input {
    private AccountManagement accountManagement;

    @BeforeMethod
    public void setUp() {
        accountManagement = new AccountManagement();
    }

    @DataProvider(name = "accountData")
    public Object[][] readAccountData() throws IOException {
        FileInputStream file = null;
        Workbook workbook = null;
        ArrayList<Object[]> data = new ArrayList<>();

        try {
            file = new FileInputStream(new File("AccountExcel.xlsx"));
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet("AccountData");

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header row
                int accountNumber = (int) row.getCell(0).getNumericCellValue();
                String accountHolder = row.getCell(1).getStringCellValue();
                double initialDeposit = row.getCell(2).getNumericCellValue();
                double expectedBalance = row.getCell(3).getNumericCellValue();

                data.add(new Object[]{accountNumber, accountHolder, initialDeposit, expectedBalance});
            }
        } finally {
            // Close workbook and FileInputStream in reverse order of their creation
            if (workbook != null) {
                workbook.close();
            }
            if (file != null) {
                file.close();
            }
        }

        return data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "accountData")
    public void testCreateAccount(int accountNumber, String accountHolder, double initialDeposit, double expectedBalance) {
        accountManagement.createAccount(accountNumber, accountHolder, initialDeposit);
        Account account = accountManagement.getAccount(accountNumber);
        
        if (initialDeposit >= AccountManagement.getMinBalance()) {
            Assert.assertNotNull(account, "Account should be created successfully.");
            Assert.assertEquals(account.balance, expectedBalance, "Account balance should match expected balance.");
        } else {
            Assert.assertNull(account, "Account should not be created for insufficient initial deposit.");
        }
    }

    @Test(dataProvider = "accountData")
    public void testUpdateAccountHolderName(int accountNumber, String accountHolder, double initialDeposit, double expectedBalance) {
        accountManagement.createAccount(accountNumber, accountHolder, initialDeposit);
        String newHolderName = "Updated Name";
        
        accountManagement.updateAccountHolderName(accountNumber, newHolderName);
        Account updatedAccount = accountManagement.getAccount(accountNumber);

        if (initialDeposit >= AccountManagement.getMinBalance()) {
            Assert.assertEquals(updatedAccount.accountHolder, newHolderName, "Account holder name should be updated.");
        }
    }
}
