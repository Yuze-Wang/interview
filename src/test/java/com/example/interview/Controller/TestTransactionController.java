package com.example.interview.Controller;

import com.example.interview.controller.TransactionController;
import com.example.interview.customizedException.TransactionNotFoundException;
import com.example.interview.model.Transaction;
import com.example.interview.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestTransactionController {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    private Transaction expectedTransaction;

    @Before
    public void setup(){
        expectedTransaction = new Transaction();
    }

    @Test
    public void testGetTransactionById() throws TransactionNotFoundException {
        long id = 1L;
        expectedTransaction.setId(id);
        when(transactionService.getTransactionById(id)).thenReturn(expectedTransaction);

        ResponseEntity<Transaction> response = transactionController.getTransaction(1L);
        assertEquals(expectedTransaction, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }

    @Test
    public void testSaveTransaction() {
        long customerId = 2L;
        int expense = 100;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        expectedTransaction.setCustomerId(customerId);
        expectedTransaction.setExpense(expense);
        expectedTransaction.setTimestamp(timestamp);

        when(transactionService.saveTransaction(customerId, expense, timestamp))
                .thenReturn(expectedTransaction);

        ResponseEntity<Transaction> response = transactionController.saveTransaction(2L, 100, timestamp);

        assertEquals(expectedTransaction, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void updateTransaction_shouldUpdateTransaction() {
        long id = 1L;
        long customerId = 2L;
        int expense = 100;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        expectedTransaction.setId(id);
        expectedTransaction.setCustomerId(customerId);
        expectedTransaction.setExpense(expense);
        expectedTransaction.setTimestamp(timestamp);
        when(transactionService.updateTransaction(id, customerId, expense, timestamp)).thenReturn(expectedTransaction);

        ResponseEntity<Transaction> response= transactionController.updateTransaction(id, customerId, expense, timestamp );

        assertEquals(expectedTransaction, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}