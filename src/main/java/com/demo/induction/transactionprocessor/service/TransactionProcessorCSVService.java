package com.demo.induction.transactionprocessor.service;

import com.demo.induction.transactionprocessor.model.Transaction;
import com.demo.induction.transactionprocessor.model.Violation;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TransactionProcessorCSVService extends TransactionProcessor {
    private List<Transaction> transactionList;

    @Override
    public void importTransactions(InputStream is) {
        transactionList= new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        transactionList = br.lines().map(mapToItem).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getImportedTransactions() {
        return transactionList;
    }

    /***
     * This method normally splits the values based on comma and stores into the String array
     * The first element of CSV value will be type so the first element was set to type of transaction instance
     * we are also checking the index 2 (amount) should not be empty as well as index 1(narration) before setting the
     * value to them.
     */
    protected Function<String, Transaction> mapToItem = (line) -> {
        String[] p = line.split(",");// a CSV has comma separated lines
        Transaction item = new Transaction();
        item.setType(p[0]);
        if (p[2] != null && p[1].trim().length() > 0) {
            item.setAmount(new BigDecimal(p[1]));
            item.setNarration(p[2]);
        }
        return item;
    };
}
