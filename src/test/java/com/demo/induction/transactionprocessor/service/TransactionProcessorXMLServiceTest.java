package com.demo.induction.transactionprocessor.service;

import com.demo.induction.transactionprocessor.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionProcessorXMLServiceTest {

    @Mock
    TransactionProcessorXMLService transactionProcessorXMLService;


    @Test
    void getImportedTransactions() {
        List<Transaction> expectedTransactions = getExpectedTransactions();

        given(transactionProcessorXMLService.getImportedTransactions()).willReturn(expectedTransactions);

        List<Transaction> actualTransactions = transactionProcessorXMLService.getImportedTransactions();

        assertNotNull(actualTransactions);
        assertEquals(expectedTransactions.toString(), actualTransactions.toString(), "Transaction are equal");

        verify(transactionProcessorXMLService, times(1)).getImportedTransactions();

    }

    private List<Transaction> getExpectedTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction("D", new BigDecimal("61.22"), "Electricity bill"));
        transactionList.add(new Transaction("D", new BigDecimal("52.14"), "Social security payment"));
        transactionList.add(new Transaction("D", new BigDecimal("200.00"), "Payment sent to x"));
        transactionList.add(new Transaction("C", new BigDecimal("1920.00"), "Salary"));
        transactionList.add(new Transaction("D", new BigDecimal("150.00"), "Car rental"));

        return transactionList;
    }
}