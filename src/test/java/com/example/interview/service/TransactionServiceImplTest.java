package com.example.interview.service;

import com.example.interview.customizedException.CustomerNotFoundException;
import com.example.interview.customizedException.InvalidAgeException;
import com.example.interview.customizedException.TransactionNotFoundException;
import com.example.interview.model.Customer;
import com.example.interview.model.Transaction;
import com.example.interview.repo.CustomerRepository;
import com.example.interview.repo.TransactionRepository;
import com.example.interview.service.impl.TransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Customer expectedCustomer;

    private Transaction expectedTransaction;

    private final String defaultPointsPerMonth = "0,0,0,0,0,0,0,0,0,0,0,0";


    @Before
    public void setup() {
        expectedTransaction = new Transaction();
        expectedCustomer = new Customer();

    }

    @Test
    public void getTransactionTest_Success() throws TransactionNotFoundException {
        long id = 1L;
        expectedTransaction.setId(id);

        when(transactionRepository.findById(id)).thenReturn(Optional.of(expectedTransaction));

        Transaction result = transactionService.getTransactionById(id);

        assertEquals(expectedTransaction, result);
    }

    @Test(expected = TransactionNotFoundException.class)
    public void getTransactionTest_TransactionNotFound() throws TransactionNotFoundException {
        long id = 1L;

        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        transactionService.getTransactionById(id);
    }

    @Test
    public void saveTransactionTest_Success() {
        long id = 1L;
        long customerId = 1L;
        int expense = 100;
        Timestamp timestamp =  new Timestamp(System.currentTimeMillis());
        expectedTransaction.setId(id);
        expectedTransaction.setCustomerId(customerId);
        expectedTransaction.setExpense(expense);
        expectedTransaction.setTimestamp(timestamp);
        expectedCustomer.setId(customerId);
        expectedCustomer.setPointsPerMonth(defaultPointsPerMonth);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(expectedTransaction);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

        Transaction result = transactionService.saveTransaction(customerId, expense, timestamp);

        assertEquals(expectedTransaction, result);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void saveTransactionTest_CustomerNotFound() throws TransactionNotFoundException {
        long customerId = 1L;
        int expense = 100;
        Timestamp timestamp =  new Timestamp(System.currentTimeMillis());

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        transactionService.saveTransaction(customerId, expense, timestamp);
    }

    @Test
    public void updateTransaction_Success() throws CustomerNotFoundException, InvalidAgeException {
        long id = 1L;
        long customerId = 1L;
        int expense = 100;
        Timestamp timestamp =  new Timestamp(System.currentTimeMillis());

        expectedCustomer.setId(customerId);
        Transaction transaction = Transaction.builder().id(id).customerId(customerId).expense(expense).timestamp(timestamp).build();


        when(transactionRepository.findById(id)).thenReturn(Optional.of(expectedTransaction));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction updatedTransaction = transactionService.updateTransaction(id, customerId, expense, timestamp);

        assertEquals(transaction, updatedTransaction);

    }

    @Test(expected = TransactionNotFoundException.class)
    public void updateTransaction_TransactionNotFound() throws CustomerNotFoundException, InvalidAgeException {
        long id = 1L;
        long wrongId = 2L;
        long customerId = 1L;
        int expense = 100;
        Timestamp timestamp =  new Timestamp(System.currentTimeMillis());

        expectedCustomer.setId(customerId);
        Transaction transaction = Transaction.builder().id(id).customerId(customerId).expense(expense).timestamp(timestamp).build();


        when(transactionRepository.findById(wrongId)).thenReturn(Optional.of(expectedTransaction));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.updateTransaction(id, customerId, expense, timestamp);

    }

    @Test(expected = CustomerNotFoundException.class)
    public void updateTransaction_CustomerNotFound() throws CustomerNotFoundException, InvalidAgeException {
        long id = 1L;
        long wrongCustomerId = 2L;
        long customerId = 1L;
        int expense = 100;
        Timestamp timestamp =  new Timestamp(System.currentTimeMillis());

        expectedCustomer.setId(customerId);
        Transaction transaction = Transaction.builder().id(id).customerId(customerId).expense(expense).timestamp(timestamp).build();


        when(transactionRepository.findById(id)).thenReturn(Optional.of(expectedTransaction));
        when(customerRepository.findById(wrongCustomerId)).thenReturn(Optional.of(expectedCustomer));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.updateTransaction(id, customerId, expense, timestamp);

    }
}
