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

public class LoanExcel_Input {
    private LoanManagement loanManagement;

    @BeforeMethod
    public void setUp() {
        loanManagement = new LoanManagement();
    }

    @DataProvider(name = "loanData")
    public Object[][] readLoanData() throws IOException {
        FileInputStream file = null;
        Workbook workbook = null;
        ArrayList<Object[]> data = new ArrayList<>();

        try {
            file = new FileInputStream(new File("LoanExcel.xlsx"));
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet("LoanData");

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header row
                int accountNumber = (int) row.getCell(0).getNumericCellValue();
                double loanAmount = row.getCell(1).getNumericCellValue();
                double interestRate = row.getCell(2).getNumericCellValue();
                int expectedLoanCount = (int) row.getCell(3).getNumericCellValue();
                double expectedTotalPayable = row.getCell(4).getNumericCellValue();

                data.add(new Object[]{accountNumber, loanAmount, interestRate, expectedLoanCount, expectedTotalPayable});
            }
        } finally {
            // Close workbook and FileInputStream in reverse order of their creation
            if (workbook != null) {
                workbook.close(); // Close workbook
            }
            if (file != null) {
                file.close(); // Close FileInputStream
            }
        }
        
        return data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "loanData")
    public void testApplyLoan(int accountNumber, double loanAmount, double interestRate, int expectedLoanCount, double expectedTotalPayable) {
        loanManagement.applyLoan(accountNumber, loanAmount, interestRate);
        Assert.assertEquals(loanManagement.getLoans().size(), expectedLoanCount);
    }

    @Test(dataProvider = "loanData")
    public void testCalculateTotalPayable(int accountNumber, double loanAmount, double interestRate, int expectedLoanCount, double expectedTotalPayable) {
        loanManagement.applyLoan(accountNumber, loanAmount, interestRate);
        double totalPayable = loanManagement.calculateTotalPayable(accountNumber);
        Assert.assertEquals(totalPayable, expectedTotalPayable);
    }
}