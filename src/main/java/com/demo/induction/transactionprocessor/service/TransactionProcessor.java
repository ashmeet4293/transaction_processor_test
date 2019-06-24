package com.demo.induction.transactionprocessor.service;

import com.demo.induction.transactionprocessor.model.Transaction;
import com.demo.induction.transactionprocessor.model.Violation;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionProcessor {

    abstract void importTransactions(InputStream is);

    abstract List<Transaction> getImportedTransactions();

    public List<Violation> validate(List<Transaction> transactions){
        List<Violation> violations = new ArrayList<>();

        checkTransactionViolation(violations);
        checkTransactionBalanced(violations);

        return violations;
    }

    public boolean isBalanced(){
        BigDecimal debitSum = new BigDecimal(0.0);
        BigDecimal creditSum = new BigDecimal(0.0);
        for (Transaction transaction : getImportedTransactions()) {
            if (transaction.getType().equals("D")) {
                debitSum = debitSum.add(transaction.getAmount());
            } else if (transaction.getType().equals("C")) {
                creditSum = creditSum.add(transaction.getAmount());
            }
        }

        if (debitSum.equals(creditSum))
            return true;
        else
            return false;
    }


    /**
     * Checking transaction violation based on requirements
     */
    private void checkTransactionViolation(List<Violation> violations) {
        getImportedTransactions().forEach(data -> {
            Violation violation = new Violation();
            if (!(data.getType().contains("C") || data.getType().contains("D"))) {
                violation.setOrder(1);
                violation.setProperty("TYPE");
                violation.setDescription("Unknown Transaction Type !");
            }
            if ((data.getAmount() == null)) {
                violation.setOrder(2);
                violation.setProperty("Amount");
                violation.setDescription("Amount is null !");
            }

            /**
             * we are just checking whether the violation instance is null or not
             * in order to any other violation logic
             * */
            if (violation.getProperty() != null) {
                violations.add(violation);
            }
        });
    }

    /**
     * Checking transaction is balanced or not
     */
    private void checkTransactionBalanced(List<Violation> violations) {
        if (!isBalanced()) {
            violations.add(new Violation(4, "Balance Property", "Debit or credit is not balanced"));
        }
    }
}
