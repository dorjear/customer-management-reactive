package com.dorjear.study.customer.services;

import java.util.List;

import com.dorjear.study.customer.domain.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<Customer> listAllCustomers();

    Mono<Customer> getCustomerById(Integer id);

    Mono<Customer> saveCustomer(Customer customer);

    void deleteCustomer(Integer id);
}
