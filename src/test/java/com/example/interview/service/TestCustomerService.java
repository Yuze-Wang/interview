package com.example.interview.service;


import com.example.interview.customizedException.CustomerNotFoundException;
import com.example.interview.customizedException.InvalidAgeException;
import com.example.interview.model.Customer;
import com.example.interview.repo.CustomerRepository;
import com.example.interview.service.impl.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TestCustomerService {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer expectedCustomer;

    private final String defaultPointsPerMonth = "0,0,0,0,0,0,0,0,0,0,0,0";

    @Before
    public void setUp() {
        expectedCustomer = new Customer();
    }

    @Test
    public void getCustomerById_Success() throws CustomerNotFoundException {
        long id = 1L;
        expectedCustomer.setId(id);

        when(customerRepository.findById(id)).thenReturn(Optional.of(expectedCustomer));

        Customer response = customerService.getCustomerById(1L);
        assertEquals(expectedCustomer, response);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void testGetCustomerById_CustomerNotFound() throws CustomerNotFoundException {
        long id = 1L;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        customerService.getCustomerById(id);
    }

    @Test
    public void testSaveCustomer_Success() throws InvalidAgeException {
        String name = "Peter";
        int age = 22;
        expectedCustomer.setName(name);
        expectedCustomer.setAge(age);

        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);

        Customer result = customerService.saveCustomer(name, age);

        assertEquals(expectedCustomer, result);

    }

    @Test(expected = InvalidAgeException.class)
    public void testSaveCustomer_InvalidAge() throws InvalidAgeException {
        String name = "Peter";
        int age = -1;

        customerService.saveCustomer(name, age);
    }

    @Test
    public void getPoints_ShouldReturnCorrectPoints() throws CustomerNotFoundException {
        long id = 1L;
        int expectedPoints = 100;
        expectedCustomer.setPoints(expectedPoints);

        when(customerRepository.findById(id)).thenReturn(Optional.of(expectedCustomer));

        int response = customerService.getPoints(id);
        assertEquals(expectedPoints, response);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void getPoints_CustomerNotFound() throws CustomerNotFoundException {
        long id = 1L;
        int expectedPoints = 100;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        customerService.getPoints(id);
    }

    @Test
    public void getPointsPerMonth_ShouldReturnCorrectPoints() throws CustomerNotFoundException {
        long id = 1L;
        int[] expectedPointsPerMonth = new int[12];
        Arrays.fill(expectedPointsPerMonth, 0);
        expectedCustomer.setPointsPerMonth(defaultPointsPerMonth);

        when(customerRepository.findById(id)).thenReturn(Optional.of(expectedCustomer));

        int[] response = customerService.getPointsPerMonth(id);
        for(int i = 0; i < expectedPointsPerMonth.length; i++) assertEquals(expectedPointsPerMonth[i], response[i]);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void getPointsPerMonth_CustomerNotFound() throws CustomerNotFoundException {
        long id = 1L;
        int expectedPoints = 100;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        customerService.getPointsPerMonth(id);
    }

    @Test
    public void updateCustomer_Success() throws CustomerNotFoundException, InvalidAgeException {
        long id = 1L;
        String name = "Mike";
        int age = 30;
        Customer customer = Customer.builder().id(id).name(name).age(age).build();


        when(customerRepository.findById(id)).thenReturn(Optional.of(expectedCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = customerService.updateCustomer(id, name, age);

        assertEquals(customer, updatedCustomer);

    }

    @Test(expected = CustomerNotFoundException.class)
    public void updateCustomer_CustomerNotFound() throws CustomerNotFoundException, InvalidAgeException {
        long id = 1L;
        long wrongId = 2L;
        String name = "Mike";
        int age = 30;
        Customer customer = Customer.builder().id(id).name(name).age(age).build();


        when(customerRepository.findById(wrongId)).thenReturn(Optional.of(expectedCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerService.updateCustomer(id, name, age);

    }

    @Test(expected = InvalidAgeException.class)
    public void updateCustomer_InvalidAge() throws CustomerNotFoundException, InvalidAgeException {
        long id = 1L;
        String name = "Mike";
        int age = 300;
        Customer customer = Customer.builder().id(id).name(name).age(age).build();


        when(customerRepository.findById(id)).thenReturn(Optional.of(expectedCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerService.updateCustomer(id, name, age);
    }



}