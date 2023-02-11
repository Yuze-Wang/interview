package com.example.interview.controller;

import com.example.interview.customizedException.TransactionNotFoundException;
import com.example.interview.model.Transaction;
import com.example.interview.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable("id") long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> saveTransaction(@RequestParam("customerId") long customerId,
                                                       @RequestParam("points") int points,
                                                       @RequestParam("timestamp") Timestamp timestamp) {
        Transaction savedTransaction = transactionService.saveTransaction(customerId, points, timestamp);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }


    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable long id,
                                                         @RequestParam long customerId,
                                                         @RequestParam int points,
                                                         @RequestParam Timestamp timestamp) {
        Transaction transaction = transactionService.updateTransaction(id, customerId, points, timestamp);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable long id) throws TransactionNotFoundException {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
