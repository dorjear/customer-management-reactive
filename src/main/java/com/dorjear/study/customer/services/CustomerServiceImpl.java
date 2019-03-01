package com.dorjear.study.customer.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dorjear.study.customer.domain.Customer;
import com.dorjear.study.customer.repositories.CustomertRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CustomertRepository customerRepository;

    @Autowired
    public void setCustomerRepository(CustomertRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Flux<Customer> listAllCustomers() {
        logger.debug("listAllCustomers called");
        return Flux.fromIterable(customerRepository.findAll());
    }

    @Override
    public Mono<Customer> getCustomerById(Integer id) {
        logger.debug("getCustomerById called");
        return Mono.fromCallable(()->customerRepository.findById(id).orElse(null));
    }

    @Override
    public Mono<Customer> saveCustomer(Customer customer) {
        logger.debug("saveCustomer called");
        return Mono.fromCallable(()->customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Integer id) {
        logger.debug("deleteCustomer called");
        customerRepository.deleteById(id);
    }
}
