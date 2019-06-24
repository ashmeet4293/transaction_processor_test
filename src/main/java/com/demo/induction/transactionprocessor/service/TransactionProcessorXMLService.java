package com.demo.induction.transactionprocessor.service;

import com.demo.induction.transactionprocessor.model.Transaction;
import com.demo.induction.transactionprocessor.model.Violation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionProcessorXMLService extends TransactionProcessor{
    private List<Transaction> transactionList = new ArrayList<>();

    @Override
    public void importTransactions(InputStream is) {

        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList transactions = doc.getElementsByTagName("Transaction");

            for (int i = 0; i < transactions.getLength(); i++) {
                Transaction transaction = new Transaction();
                Element chapter = (Element) transactions.item(i);
                transaction.setType(chapter.getAttribute("type"));
                transaction.setAmount(new BigDecimal(chapter.getAttribute("amount")));
                transaction.setNarration(chapter.getAttribute("narration"));

                transactionList.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Transaction> getImportedTransactions() {
        return transactionList;
    }


}
