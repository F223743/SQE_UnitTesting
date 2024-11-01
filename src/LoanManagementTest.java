import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class LoanManagementTest {
    private LoanManagement loanManagement;

    @BeforeMethod
    public void setUp() {
        loanManagement = new LoanManagement();
    }

    // DataProvider for applyLoan method
    @DataProvider(name = "loanDataProvider")
    public Object[][] loanDataProvider() {
        return new Object[][]{
            {12345, 5000.00, 5.0, true},    // Valid input
            {12345, -5000.00, 5.0, false},   // Invalid loan amount
            {12345, 5000.00, -5.0, false},   // Invalid interest rate
            {12345, 0.0, 5.0, false}         // Zero loan amount
        };
    }

    @Test(dataProvider = "loanDataProvider")
    public void testApplyLoan(int accountNumber, double loanAmount, double interestRate, boolean shouldAddLoan) {
        loanManagement.applyLoan(accountNumber, loanAmount, interestRate);
        if (shouldAddLoan) {
            Assert.assertEquals(loanManagement.getLoans().size(), 1);
            Assert.assertEquals(loanManagement.getLoanByAccount(accountNumber).loanAmount, loanAmount);
            Assert.assertEquals(loanManagement.getLoanByAccount(accountNumber).interestRate, interestRate);
        } else {
            Assert.assertEquals(loanManagement.getLoans().size(), 0);
        }
    }

    // DataProvider for getLoans method
    @DataProvider(name = "getLoansDataProvider")
    public Object[][] getLoansDataProvider() {
        return new Object[][]{
            {true}, // No loans
            {false} // One loan
        };
    }

    @Test(dataProvider = "getLoansDataProvider")
    public void testGetLoans(boolean shouldBeEmpty) {
        if (!shouldBeEmpty) {
            loanManagement.applyLoan(12345, 5000.00, 5.0);
        }
        Assert.assertEquals(loanManagement.getLoans().isEmpty(), shouldBeEmpty);
    }

    // DataProvider for getLoanByAccount method
    @DataProvider(name = "getLoanByAccountDataProvider")
    public Object[][] getLoanByAccountDataProvider() {
        return new Object[][]{
            {12345, 5000.00, 5.0, true},   // Existing account
            {67890, 0.0, 0.0, false}        // Non-existing account
        };
    }

    @Test(dataProvider = "getLoanByAccountDataProvider")
    public void testGetLoanByAccount(int accountNumber, double expectedAmount, double expectedRate, boolean shouldExist) {
        if (shouldExist) {
            loanManagement.applyLoan(12345, 5000.00, 5.0);
        }
        Loan loan = loanManagement.getLoanByAccount(accountNumber);
        if (shouldExist) {
            Assert.assertNotNull(loan);
            Assert.assertEquals(loan.accountNumber, accountNumber);
            Assert.assertEquals(loan.loanAmount, expectedAmount);
            Assert.assertEquals(loan.interestRate, expectedRate);
        } else {
            Assert.assertNull(loan);
        }
    }

    // DataProvider for calculateTotalPayable method
    @DataProvider(name = "calculateTotalPayableDataProvider")
    public Object[][] calculateTotalPayableDataProvider() {
        return new Object[][]{
            {12345, 5000.00, 5.0, 5250.00},  // Existing loan
            {67890, 0.0, 0.0, 0.0}            // Non-existing loan
        };
    }

    @Test(dataProvider = "calculateTotalPayableDataProvider")
    public void testCalculateTotalPayable(int accountNumber, double loanAmount, double interestRate, double expectedTotalPayable) {
        if (loanAmount > 0 && interestRate >= 0) {
            loanManagement.applyLoan(accountNumber, loanAmount, interestRate);
        }
        double totalPayable = loanManagement.calculateTotalPayable(accountNumber);
        Assert.assertEquals(totalPayable, expectedTotalPayable);
    }

    // DataProvider for listAllLoans method
    @DataProvider(name = "listAllLoansDataProvider")
    public Object[][] listAllLoansDataProvider() {
        return new Object[][]{
            {0}, // No loans
            {2}  // Two loans
        };
    }

    @Test(dataProvider = "listAllLoansDataProvider")
    public void testListAllLoans(int numberOfLoans) {
        if (numberOfLoans == 2) {
            loanManagement.applyLoan(12345, 5000.00, 5.0);
            loanManagement.applyLoan(67890, 10000.00, 3.5);
        }
        loanManagement.listAllLoans(); // Check console output
        Assert.assertEquals(loanManagement.getLoans().size(), numberOfLoans);
    }
}
