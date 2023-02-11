package com.example.interview.service;

import com.example.interview.customizedException.TransactionNotFoundException;
import com.example.interview.model.Transaction;

import java.sql.Timestamp;

public interface TransactionService {

    Transaction saveTransaction(long customerId, int expense, Timestamp timestamp);
    Transaction getTransactionById(long id);

    Transaction updateTransaction(long id, long customerId, int expense, Timestamp timestamp) throws TransactionNotFoundException;

    void deleteTransaction(long id) throws TransactionNotFoundException;
}