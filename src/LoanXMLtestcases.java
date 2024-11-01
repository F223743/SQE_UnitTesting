import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class LoanXMLtestcases {
    LoanManagement loanManagement;

    @BeforeClass
    public void setUp() {
        // Initialize LoanManagement before running any tests
        loanManagement = new LoanManagement();
        
        // Apply loans for both accounts to avoid failures in test cases
        loanManagement.applyLoan(12345, 10000, 5.0); // Apply for account 12345
        loanManagement.applyLoan(67890, 20000, 3.5); // Apply for account 67890
    }

    // Test case for applying a loan
    @Test
    public void applyLoan() {
        Loan loan = loanManagement.getLoanByAccount(12345);
        Assert.assertNotNull(loan, "Loan should be applied successfully for account 12345.");
        Assert.assertEquals(loan.loanAmount, 10000.0, "Loan amount should be 10000.");
        Assert.assertEquals(loan.interestRate, 5.0, "Interest rate should be 5.0.");
    }

    // Test case for retrieving loan details by account number
    @Test
    @Parameters({"accountNumber"}) 
    public void getLoanDetails(@Optional("12345") int accountNumber) {
        Loan loan = loanManagement.getLoanByAccount(accountNumber);
        Assert.assertNotNull(loan, "Loan should exist for account: " + accountNumber);
        System.out.println("Loan Details: " + loan);
    }

    // Test case for calculating total payable amount
    @Test
    @Parameters({"accountNumber"})
    public void calculateTotalPayable(@Optional("67890")int accountNumber) {
        double totalPayable = loanManagement.calculateTotalPayable(accountNumber);
        Loan loan = loanManagement.getLoanByAccount(accountNumber);
        if (loan != null) {
            double expectedTotalPayable = loan.loanAmount + (loan.loanAmount * loan.interestRate / 100);
            Assert.assertEquals(totalPayable, expectedTotalPayable, "Total payable should match the calculated value for account: " + accountNumber);
        } else {
            Assert.fail("Loan not found for account: " + accountNumber);
        }
        System.out.println("Total Payable for Account " + accountNumber + ": $" + totalPayable);
    }
}
