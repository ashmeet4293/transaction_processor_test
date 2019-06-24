package com.demo.induction.transactionprocessor.resources;

import com.demo.induction.transactionprocessor.model.Transaction;
import com.demo.induction.transactionprocessor.model.Violation;
import com.demo.induction.transactionprocessor.service.TransactionProcessorCSVService;
import com.demo.induction.transactionprocessor.service.TransactionProcessorXMLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/xml")
@PropertySource(value ={"classpath:application.properties"})
public class TransactionProcessorXMLController {
    private static final Logger log= LoggerFactory.getLogger(TransactionProcessorXMLController.class);

    private final TransactionProcessorXMLService transactionProcessorXML;

    public TransactionProcessorXMLController( TransactionProcessorXMLService transactionProcessorXML) {
        this.transactionProcessorXML = transactionProcessorXML;
    }

    @GetMapping(value="/load")
    public ResponseEntity loadXML(@Value("${config.xml-file-to-load}") String fileToLoad) throws Exception{

        log.info("Got an request to load XML file {} ", fileToLoad);

        transactionProcessorXML.importTransactions(new FileInputStream(new File(fileToLoad)));

        log.info("XML File Successfully loaded ");

        return ResponseEntity.ok(transactionProcessorXML.getImportedTransactions());
    }

    @GetMapping(value="/validate")
    public ResponseEntity validate(){
        log.info("Got an request to load XML violation checking ");

        List<Transaction> transactions=transactionProcessorXML.getImportedTransactions();

        if (transactions != null) {
            return ResponseEntity.ok(transactionProcessorXML.validate(transactions));

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Violation found during validation !");
        }
    }

    @GetMapping(value="/check-balanced")
    public ResponseEntity  checkBalanced(){
        log.info("Got an request to check balanced or not");

        return ResponseEntity.ok(transactionProcessorXML.isBalanced());

    }

}
