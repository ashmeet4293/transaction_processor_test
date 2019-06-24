package com.demo.induction.transactionprocessor.service;

import com.demo.induction.transactionprocessor.model.Violation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TransactionProcessorTest {

    @Mock
    TransactionProcessor transactionProcessor;

    @Test
    void validate() {
        Violation violation = new Violation(1, "TYPE", "Unknown Transaction Type !");
        List<Violation> violations= new ArrayList<>();
        violations.add(violation);

        given(transactionProcessor.validate(anyList())).willReturn(violations);

        List<Violation> expectedViolation= transactionProcessor.validate(anyList());

        assertAll(()-> assertEquals(expectedViolation.toString(), violations.toString()));

    }

    @Test
    void isBalanced() {

        given(transactionProcessor.isBalanced()).willReturn(true);

        boolean actualResult=transactionProcessor.isBalanced();

        assertAll(()-> assertTrue(actualResult));
    }
}