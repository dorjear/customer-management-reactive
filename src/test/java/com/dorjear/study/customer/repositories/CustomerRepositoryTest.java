package com.dorjear.study.customer.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dorjear.study.customer.configuration.RepositoryConfiguration;
import com.dorjear.study.customer.domain.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class CustomerRepositoryTest {
    private CustomertRepository customerRepository;
    @Autowired
    public void setCustomerRepository(CustomertRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Test
    public void testSaveCustomer(){
        //setup customer
        Customer customer = new Customer();
        customer.setFirstName("Tom");
        customer.setLastName("Smith");
        LocalDate localDate = LocalDate.of(1999, Month.AUGUST, 12);
        customer.setDateOfBirth(Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        //save customer, verify has ID value after save
        assertNull(customer.getId()); //null before save
        customerRepository.save(customer);
        assertNotNull(customer.getId()); //not null after save
        //fetch from DB
        Customer fetchedCustomer = customerRepository.findById(customer.getId()).orElse(null);
        //should not be null
        assertNotNull(fetchedCustomer);
        //should equal
        assertEquals(customer.getId(), fetchedCustomer.getId());
        assertEquals(customer.getFirstName(), fetchedCustomer.getFirstName());
        //update description and save
        fetchedCustomer.setFirstName("newFirstName");
        customerRepository.save(fetchedCustomer);
        //get from DB, should be updated
        Customer fetchedUpdatedCustomer = customerRepository.findById(fetchedCustomer.getId()).orElse(null);
        assertEquals(fetchedCustomer.getFirstName(), fetchedUpdatedCustomer.getFirstName());
        //verify count of customers in DB
        long customerCount = customerRepository.count();
        assertEquals(customerCount, 1);
        //get all customers, list should only have one
        Iterable<Customer> customers = customerRepository.findAll();
        assertEquals(1, StreamSupport.stream(customers.spliterator(), false).count());
    }
}
