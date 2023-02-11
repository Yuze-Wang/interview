package com.example.interview.Controller;

import com.example.interview.controller.CustomerController;
import com.example.interview.model.Customer;
import com.example.interview.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCustomerController {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer expectedCustomer;
    @Before
    public void setup(){
        expectedCustomer = new Customer();
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
    public void testGetCustomerPointsPerMonth() {
        long id = 1L;
        int[] expectedPointsPerMonth = new int[12];
        Arrays.fill(expectedPointsPerMonth, 0);

        when(customerService.getPointsPerMonth(id)).thenReturn(expectedPointsPerMonth);

        ResponseEntity<int[]> response = customerController.getCustomerPointsPerMonth(id);

        assertEquals(expectedPointsPerMonth, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetCustomerById() {
        long id = 1L;
        expectedCustomer.setId(id);
        when(customerService.getCustomerById(id)).thenReturn(expectedCustomer);

        ResponseEntity<Customer> response = customerController.getCustomerById(id);

        assertEquals(expectedCustomer, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSaveCustomer() {
        String name = "Peter";
        int age = 30;
        expectedCustomer.setName(name);
        expectedCustomer.setAge(30);
        when(customerService.saveCustomer(name, age)).thenReturn(expectedCustomer);

        ResponseEntity<Customer> response = customerController.saveCustomer(name, age);

        assertEquals(expectedCustomer, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateCustomer() {
        long id = 1L;
        String name = "Peter";
        int age = 30;
        expectedCustomer.setId(id);
        expectedCustomer.setName(name);
        expectedCustomer.setAge(30);
        when(customerService.updateCustomer(id, name, age)).thenReturn(expectedCustomer);

        ResponseEntity<Customer> response = customerController.updateCustomer(id, name, age);

        assertEquals(expectedCustomer, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}