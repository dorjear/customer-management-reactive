package com.dorjear.study.customer.services;

import java.util.List;

import com.dorjear.study.customer.domain.Customer;

public interface CustomerService {
    List<Customer> listAllCustomers();

    Customer getCustomerById(Integer id);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Integer id);
}
