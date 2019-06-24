package com.demo.induction.transactionprocessor.resources;

import com.demo.induction.transactionprocessor.model.Transaction;
import com.demo.induction.transactionprocessor.model.Violation;
import com.demo.induction.transactionprocessor.service.TransactionProcessorXMLService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class Transaction Controller")
class TransactionProcessorXMLControllerTest {

    final String xmlPath="src/main/resources/sample_xml.xml";

    @InjectMocks
    TransactionProcessorXMLController controller;

    @Mock
    TransactionProcessorXMLService xmlService;

    MockMvc mockMvc;

    List<Transaction> transactionList;

    @BeforeEach
    void setup(){
        transactionList=new ArrayList<>();
        transactionList.add(new Transaction("D", new BigDecimal("61.22"), "Electricity bill"));
        transactionList.add(new Transaction("D", new BigDecimal("52.14"), "Social security payment"));
        transactionList.add(new Transaction("D", new BigDecimal("200"), "Payment sent to x"));

        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void loadXML() throws  Exception{
        //when
        ResponseEntity<Transaction>  transactions=controller.loadXML(xmlPath);

        //then
        then(xmlService).should().importTransactions(any(InputStream.class));

        assertNotNull(transactions);
    }

    @Test
    void validate() {
        //when
        List<Violation> violations=new ArrayList<>();
        Violation violation=new Violation();

        violations.add(violation);

        List<Transaction> transactions=new ArrayList<>();

        given(xmlService.validate(transactions)).willReturn(violations);

        ResponseEntity entity =controller.validate();

        assertNotNull(entity);

        verify(xmlService).validate(transactions);
    }

    @Test
    @DisplayName("Checking for XML with rest end points for validation logic ")
    void validateCSVTest() throws Exception {
        xmlService.importTransactions(any(InputStream.class));

        mockMvc.perform(get("/api/xml/validate")).
                andExpect(status().isOk());


    }

    @Test
    @DisplayName("Checking for XML with rest end points for balanced")
    void checkCSVBalancedTest() throws Exception {
        xmlService.importTransactions(any(InputStream.class));

        mockMvc.perform(get("/api/xml/check-balanced")).
                andExpect(status().isOk());


    }
}