package com.example.interview.service.impl;

import com.example.interview.customizedException.CustomerNotFoundException;
import com.example.interview.customizedException.TransactionNotFoundException;
import com.example.interview.model.Customer;
import com.example.interview.model.Transaction;
import com.example.interview.repo.CustomerRepository;
import com.example.interview.repo.TransactionRepository;
import com.example.interview.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import static com.example.interview.component.Tools.decodePointsPerMonth;
import static com.example.interview.component.Tools.encodePointsPerMonth;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Transaction saveTransaction(long customerId, int expense, Timestamp timestamp) {
        Transaction transaction = Transaction.builder().customerId(customerId).expense(expense).timestamp(timestamp).build();
        Customer customer = customerRepository.findById(transaction.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + transaction.getCustomerId()));
        int points = (expense > 100) ? (Math.max(50, expense - 50) + (expense - 100) * 2) : Math.max(50, expense - 50);
        customer.setPoints(customer.getPoints() + points);
        int[] pointsPerMonth = decodePointsPerMonth(customer.getPointsPerMonth());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        int month = calendar.get(Calendar.MONTH);
        pointsPerMonth[month] += points;
        customer.setPointsPerMonth(encodePointsPerMonth(pointsPerMonth));
        customerRepository.save(customer);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + id + " not found"));
    }

    @Override
    public Transaction updateTransaction(long id, long customerId, int expense, Timestamp timestamp) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (!transaction.isPresent()) {
            throw new TransactionNotFoundException(String.format("Transaction with id %d not found", id));
        }
        Transaction existingTransaction = transaction.get();
        existingTransaction.setCustomerId(customerId);
        existingTransaction.setExpense(expense);
        existingTransaction.setTimestamp(timestamp);
        return transactionRepository.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (!transaction.isPresent()) {
            throw new TransactionNotFoundException(String.format("Transaction with id %d not found", id));
        }
        transactionRepository.deleteById(id);
    }
}
