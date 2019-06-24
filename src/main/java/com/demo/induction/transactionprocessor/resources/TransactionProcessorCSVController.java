package com.demo.induction.transactionprocessor.resources;

import com.demo.induction.transactionprocessor.model.Transaction;
import com.demo.induction.transactionprocessor.service.TransactionProcessorCSVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/csv")
@PropertySource(value ={"classpath:application.properties"})
public class TransactionProcessorCSVController {
    private static final Logger log= LoggerFactory.getLogger(TransactionProcessorCSVController.class);

    private final TransactionProcessorCSVService csvService;

    public TransactionProcessorCSVController(TransactionProcessorCSVService csvService) {
        this.csvService = csvService;
    }

    @GetMapping(value="/load")
    public ResponseEntity loadCsv(@Value("${config.csv-file-to-load}") String fileToLoad) throws Exception{

        log.info("Got an request to load CSV file {} ", fileToLoad);

        csvService.importTransactions(new FileInputStream(new File(fileToLoad)));

        log.info("CSV File Successfully loaded ");

        return ResponseEntity.ok(csvService.getImportedTransactions());
    }

    @GetMapping(value="/validate")
    public ResponseEntity validate(){
        log.info("Got an request to load CSV violation checking ");

        List<Transaction> transactions=csvService.getImportedTransactions();

        if (transactions != null) {
            return ResponseEntity.ok(csvService.validate(transactions));

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Violation found during validation !");

        }

    }

    @GetMapping(value="/check-balanced")
    public ResponseEntity checkBalanced(){
        log.info("Got an request to CSV check balanced or not");

        return ResponseEntity.ok(csvService.isBalanced());
    }

}
