package com.demo.induction.transactionprocessor.service;

import com.demo.induction.transactionprocessor.model.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionProcessorCSVServiceTest {

    @Mock
    private TransactionProcessorCSVService transactionProcessorCsv;

    @Test
    void importTransactions() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/main/resources/sample_csv.csv"))));
        assertAll(
                () -> assertNotNull(br, ()-> "Should not return null") );
    }


    @Test
    void getImportedTransactions() {
        List<Transaction> expectedTransactions = getExpectedTransactions();

        given(transactionProcessorCsv.getImportedTransactions()).willReturn(expectedTransactions);

        List<Transaction> actualTransactions = transactionProcessorCsv.getImportedTransactions();

        assertEquals(expectedTransactions.toString(), actualTransactions.toString(), "Transaction imported successfully");

        verify(transactionProcessorCsv, times(1)).getImportedTransactions();
    }

    private List<Transaction> getExpectedTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction("D", new BigDecimal("61.22"), "Electricity bill"));
        transactionList.add(new Transaction("D", new BigDecimal("52.14"), "Social security payment"));
        transactionList.add(new Transaction("D", new BigDecimal("200"), "Payment sent to x"));
        transactionList.add(new Transaction("C", new BigDecimal("1920"), "Salary"));
        transactionList.add(new Transaction("D", new BigDecimal("150"), "Car rental"));
        transactionList.add(new Transaction("E", null, null));

        return transactionList;
    }
}