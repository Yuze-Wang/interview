package com.example.interview.service;

import com.example.interview.customizedException.TransactionNotFoundException;
import com.example.interview.model.Transaction;
import com.example.interview.repo.TransactionRepository;
import com.example.interview.service.impl.TransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;

    @Before
    public void setup() {
        transaction = Transaction.builder().id(1L).customerId(2L).expense(100).timestamp(new Timestamp(System.currentTimeMillis())).build();

    }

    @Test
    public void getTransactionTest_Success() throws TransactionNotFoundException {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        Transaction result = transactionService.getTransactionById(1L);
        assertEquals(transaction, result);
    }

    @Test(expected = TransactionNotFoundException.class)
    public void getTransactionTest_NotFound() throws TransactionNotFoundException {
        when(transactionRepository.findById(2L)).thenReturn(Optional.empty());
        transactionService.getTransactionById(2L);
    }

    @Test
    public void saveTransactionTest_Success() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        Transaction result = transactionService.saveTransaction(2L, 100, new Timestamp(System.currentTimeMillis()));
        assertEquals(transaction, result);
    }
}
