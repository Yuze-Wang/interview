package com.example.interview.service.impl;

import com.example.interview.customizedException.CustomerNotFoundException;
import com.example.interview.customizedException.InvalidAgeException;
import com.example.interview.model.Customer;
import com.example.interview.repo.CustomerRepository;
import com.example.interview.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.interview.component.Tools.decodePointsPerMonth;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + customerId + " not found"));
    }

    @Override
    public int getPoints(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return customer.getPoints();
    }

    @Override
    public int[] getPointsPerMonth(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return decodePointsPerMonth(customer.getPointsPerMonth());
    }
    @Override
    public Customer saveCustomer(String name, int age) {
        if (age <= 0 || age >= 200) {
            throw new InvalidAgeException("Invalid age: " + age);
        }

        Customer customer = Customer.builder().name(name).age(age).points(0).pointsPerMonth("0,0,0,0,0,0,0,0,0,0,0,0").build();

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(long customerId, String name, int age) {
        Customer customer = getCustomerById(customerId);
        if (age <= 0 || age >= 200) {
            throw new InvalidAgeException("Invalid age: " + age);
        }

        customer.setName(name);
        customer.setAge(age);


        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(long customerId) {
        Customer customer = getCustomerById(customerId);
        customerRepository.delete(customer);
    }
}
