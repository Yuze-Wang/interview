package com.example.interview.Controller;

import com.example.interview.controller.CustomerController;
import com.example.interview.customizedException.TransactionNotFoundException;
import com.example.interview.model.Customer;
import com.example.interview.model.Transaction;
import com.example.interview.repo.TransactionRepository;
import com.example.interview.service.CustomerService;
import com.example.interview.service.impl.TransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCustomerController {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Before
    public void setup(){

    }

    @Test
    public void testGetCustomerPoints() {
        long id = 1L;
        Integer expectedPoints = 100;

        when(customerService.getPoints(id)).thenReturn(expectedPoints);

        ResponseEntity<Integer> response = customerController.getCustomerPoints(id);

        assertEquals(expectedPoints, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetCustomerById() {
        long id = 1L;
        Customer expectedCustomer = new Customer();

        when(customerService.getCustomerById(id)).thenReturn(expectedCustomer);

        ResponseEntity<Customer> response = customerController.getCustomerById(id);

        assertEquals(expectedCustomer, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSaveCustomer() {
        String name = "John Doe";
        int age = 30;
        Customer expectedCustomer = new Customer();

        when(customerService.saveCustomer(name, age)).thenReturn(expectedCustomer);

        ResponseEntity<Customer> response = customerController.saveCustomer(name, age);

        assertEquals(expectedCustomer, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateCustomer() {
        long id = 1L;
        String name = "John Doe";
        int age = 30;
        Customer expectedCustomer = new Customer();

        when(customerService.updateCustomer(id, name, age)).thenReturn(expectedCustomer);

        ResponseEntity<Customer> response = customerController.updateCustomer(id, name, age);

        assertEquals(expectedCustomer, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomer() {
        long id = 1L;

        customerController.deleteCustomer(id);
    }
}