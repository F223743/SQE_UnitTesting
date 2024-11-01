import java.util.ArrayList;

class Loan {
    int accountNumber;
    double loanAmount;
    double interestRate;

    public Loan(int accountNumber, double loanAmount, double interestRate) {
        this.accountNumber = accountNumber;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "Loan for Account No: " + accountNumber + ", Amount: $" + loanAmount + ", Interest Rate: " + interestRate + "%";
    }
}

class LoanManagement {
    ArrayList<Loan> loans = new ArrayList<>();

    // Method to apply for a loan
    public void applyLoan(int accountNumber, double loanAmount, double interestRate) {
        // Here you might want to add conditions, like checking if the account exists
        if (loanAmount > 0 && interestRate >= 0) {
            loans.add(new Loan(accountNumber, loanAmount, interestRate));
            System.out.println("Loan applied for Account No: " + accountNumber);
        } else {
            System.out.println("Invalid loan amount or interest rate.");
        }
    }

    // Method to retrieve all loans
    public ArrayList<Loan> getLoans() {
        return loans;
    }

    // Method to check loan details by account number
    public Loan getLoanByAccount(int accountNumber) {
        for (Loan loan : loans) {
            if (loan.accountNumber == accountNumber) {
                return loan;
            }
        }
        return null; // No loan found for the given account number
    }

    // Method to calculate total amount to be paid back (Principal + Interest)
    public double calculateTotalPayable(int accountNumber) {
        Loan loan = getLoanByAccount(accountNumber);
        if (loan != null) {
            // Assuming the loan is to be paid back in one installment
            return loan.loanAmount + (loan.loanAmount * loan.interestRate / 100);
        }
        return 0; // No loan found
    }

    // Method to list all loans
    public void listAllLoans() {
        if (loans.isEmpty()) {
            System.out.println("No loans available.");
        } else {
            System.out.println("List of Loans:");
            for (Loan loan : loans) {
                System.out.println(loan);
            }
        }
    }
}