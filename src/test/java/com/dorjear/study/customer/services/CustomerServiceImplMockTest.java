package com.dorjear.study.customer.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dorjear.study.customer.domain.Customer;
import com.dorjear.study.customer.repositories.CustomertRepository;



public class CustomerServiceImplMockTest {

    private CustomerServiceImpl customerServiceImpl;
    @Mock
    private CustomertRepository customerRepository;
    @Mock
    private Customer customer;
    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        customerServiceImpl=new CustomerServiceImpl();
        customerServiceImpl.setCustomerRepository(customerRepository);
    }
    @Test
    public void shouldReturnCustomer_whenGetCustomerByIdIsCalled() throws Exception {
        // Arrange
        when(customerRepository.findById(5)).thenReturn(Optional.of(customer));
        // Act
        Customer retrievedCustomer = customerServiceImpl.getCustomerById(5);
        // Assert
        assertThat(retrievedCustomer, is(equalTo(customer)));

    }
    @Test
    public void shouldReturnCustomer_whenSaveCustomerIsCalled() throws Exception {
        // Arrange
        when(customerRepository.save(customer)).thenReturn(customer);
        // Act
        Customer savedCustomer = customerServiceImpl.saveCustomer(customer);
        // Assert
        assertThat(savedCustomer, is(equalTo(customer)));
    }
    @Test
    public void shouldCallDeleteMethodOfCustomerRepository_whenDeleteCustomerIsCalled() throws Exception {
        // Arrange
        doNothing().when(customerRepository).deleteById(5);
//        Mockito.mock(CustomertRepository.class);
        // Act
        customerServiceImpl.deleteCustomer(5);
        // Assert
        verify(customerRepository, times(1)).deleteById(5);
    }
}