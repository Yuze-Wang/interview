package com.example.interview.controller;

import com.example.interview.customizedException.CustomerNotFoundException;
import com.example.interview.customizedException.InvalidAgeException;
import com.example.interview.model.Customer;
import com.example.interview.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers/{id}/points")
    public ResponseEntity<Integer> getCustomerPoints(@PathVariable Long id) throws CustomerNotFoundException {
        int points = customerService.getPoints(id);
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    @GetMapping("/customers/{id}/pointsPerMonth")
    public ResponseEntity<int[]> getPointsPerMonth(@PathVariable long id) throws CustomerNotFoundException {
        int[] pointsPerMonth = customerService.getPointsPerMonth(id);
        return new ResponseEntity<>(pointsPerMonth, HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> saveCustomer(@RequestParam String name, @RequestParam int age) throws InvalidAgeException {
        Customer customer = customerService.saveCustomer(name, age);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long id, @RequestParam String name,
                                                   @RequestParam int age) throws CustomerNotFoundException, InvalidAgeException {
        Customer customer = customerService.updateCustomer(id, name, age);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) throws CustomerNotFoundException {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
