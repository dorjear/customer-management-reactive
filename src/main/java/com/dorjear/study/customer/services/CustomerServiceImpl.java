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

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CustomertRepository customerRepository;

    @Autowired
    public void setCustomerRepository(CustomertRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> listAllCustomers() {
        logger.debug("listAllCustomers called");
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Customer getCustomerById(Integer id) {
        logger.debug("getCustomerById called");
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        logger.debug("saveCustomer called");
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        logger.debug("deleteCustomer called");
        customerRepository.deleteById(id);
    }
}
