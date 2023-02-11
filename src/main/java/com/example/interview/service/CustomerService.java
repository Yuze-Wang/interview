package com.example.interview.service;

import com.example.interview.customizedException.CustomerNotFoundException;
import com.example.interview.customizedException.InvalidAgeException;
import com.example.interview.model.Customer;

import java.util.List;

public interface CustomerService {
    int[] getPointsPerMonth(Long id);
    int getPoints(Long id) throws CustomerNotFoundException;
    Customer getCustomerById(long id) throws CustomerNotFoundException;
    Customer saveCustomer(String name, int age) throws InvalidAgeException;
    Customer updateCustomer(long id, String name, int age) throws CustomerNotFoundException, InvalidAgeException;
    void deleteCustomer(long id) throws CustomerNotFoundException;
}
