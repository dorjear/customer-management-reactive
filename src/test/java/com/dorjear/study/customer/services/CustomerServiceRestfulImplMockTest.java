package com.dorjear.study.customer.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.dorjear.study.customer.domain.Customer;


/**
 * 
 * @author dorjear
 * @Note This implementation is just temporary solution as the API of the CRMS system is still in design stage and not yet finalized
 */
public class CustomerServiceRestfulImplMockTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private Customer customer;
    @Mock
    private ResponseEntity<List<Customer>> mockResponseEitity;
    @InjectMocks
    private CustomerServiceRestfulImpl customerServiceImpl;
    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        customerServiceImpl=new CustomerServiceRestfulImpl();
        customerServiceImpl.setCustomerRepository(restTemplate);
        ReflectionTestUtils.setField(customerServiceImpl,"detailUrl", "httpd//host/detailUrl/");
        ReflectionTestUtils.setField(customerServiceImpl,"createUrl", "http://host/createUrl/");
        ReflectionTestUtils.setField(customerServiceImpl,"deleteUrl", "http://host/deleteUrl/");
    }
    
    @Test
    public void shouldReturnCustomer_whenGetCustomerByIdIsCalled() throws Exception {
        // Arrange
        when(restTemplate.getForObject("httpd//host/detailUrl/5", Customer.class)).thenReturn(customer);
        // Act
        Customer retrievedCustomer = customerServiceImpl.getCustomerById(5);
        // Assert
        assertThat(retrievedCustomer, is(equalTo(customer)));

    }
    @Test
    public void shouldReturnCustomer_whenSaveCustomerIsCalled() throws Exception {
        // Arrange
        when(restTemplate.postForObject("http://host/createUrl/", customer, Customer.class)).thenReturn(customer);
        // Act
        Customer savedCustomer = customerServiceImpl.saveCustomer(customer);
        // Assert
        assertThat(savedCustomer, is(equalTo(customer)));
    }
    
    @Test
    public void shouldCallDeleteMethodOfCustomerRepository_whenDeleteCustomerIsCalled() throws Exception {
        // Arrange
        doNothing().when(restTemplate).delete("http://host/deleteUrl/5");
        // Act
        customerServiceImpl.deleteCustomer(5);
        // Assert
        verify(restTemplate, times(1)).delete("http://host/deleteUrl/5");
    }
}