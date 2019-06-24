package com.demo.induction.transactionprocessor.resources;

import com.demo.induction.transactionprocessor.model.Transaction;
import com.demo.induction.transactionprocessor.model.Violation;
import com.demo.induction.transactionprocessor.service.TransactionProcessorCSVService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value ={"classpath:application.properties"})
@DisplayName("Test class Transaction CSV Controller")
class TransactionProcessorCSVControllerTest {

    final String csvPath="src/main/resources/sample_csv.csv";

    @InjectMocks
    TransactionProcessorCSVController csvController;

    @Mock
    TransactionProcessorCSVService csvService;

    MockMvc mockMvc;

    List<Transaction> transactionList;

    @BeforeEach
    void setup(){
        transactionList=new ArrayList<>();
        transactionList.add(new Transaction("D", new BigDecimal("61.22"), "Electricity bill"));
        transactionList.add(new Transaction("D", new BigDecimal("52.14"), "Social security payment"));
        transactionList.add(new Transaction("D", new BigDecimal("200"), "Payment sent to x"));

        mockMvc= MockMvcBuilders.standaloneSetup(csvController).build();
    }

    @Test
    void loadXCSV() throws Exception {
        //when
        ResponseEntity<Transaction> transactions=csvController.loadCsv(csvPath);

        //then
        then(csvService).should().importTransactions(any(InputStream.class));

        assertNotNull(transactions);
    }

    @Test
    void validate() {
        //when
        List<Violation> violations=new ArrayList<>();
        Violation violation=new Violation();

        violations.add(violation);

        List<Transaction> transactions=new ArrayList<>();

        given(csvService.validate(transactions)).willReturn(violations);

        ResponseEntity entity =csvController.validate();

        assertNotNull(entity);

        verify(csvService).validate(transactions);

    }

    @Test
    void checkBalanced() {
    }

    @Test
    @DisplayName("Checking for CSV with rest end points for validation logic ")
    void validateCSVTest() throws Exception {
        csvService.importTransactions(any(InputStream.class));

        mockMvc.perform(get("/api/csv/validate")).
                andExpect(status().isOk());


    }

    @Test
    @DisplayName("Checking for CSV with rest end points for balanced")
    void checkCSVBalancedTest() throws Exception {
        csvService.importTransactions(any(InputStream.class));

        mockMvc.perform(get("/api/csv/check-balanced")).
                andExpect(status().isOk());


    }
}