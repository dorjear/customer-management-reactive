package com.dorjear.study.customer.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dorjear.study.customer.domain.Customer;
/**
 * 
 * @author dorjear
 * @Note This implementation is just temporary solution as the API of the CRMS system is still in design stage and not yet finalized
 * 
 */

//@Service("customerServiceRestful")
public class CustomerServiceRestfulImpl implements CustomerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private RestTemplate restTemplate;
    
    @Value("${crms.listUrl}")
    private String listUrl;
    @Value("${crms.detailUrl}")
    private String detailUrl;
    @Value("${crms.createUrl}")
    private String createUrl;
    @Value("${crms.updateUrl}")
    private String updateUrl;
    @Value("${crms.deleteUrl}")
    private String deleteUrl;
    
    @Autowired
    public void setCustomerRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Customer> listAllCustomers() {
        logger.debug("listAllCustomers called");
        ResponseEntity<List<Customer>> response = restTemplate.exchange(listUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>(){});
        return response.getBody();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        logger.debug("getCustomerById called");
        return restTemplate.getForObject(detailUrl+id, Customer.class);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        logger.debug("saveCustomer called");
        return restTemplate.postForObject(createUrl, customer, Customer.class);
    }

    @Override
    public void deleteCustomer(Integer id) {
        logger.debug("deleteCustomer called");
        restTemplate.delete(deleteUrl+id);
    }
}
