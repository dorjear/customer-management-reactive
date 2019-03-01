package com.dorjear.study.customer;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.dorjear.study.customer.controllers.CustomerController;
import com.dorjear.study.customer.domain.Customer;
import com.dorjear.study.customer.services.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(secure = false)
public class CustomerControllerWebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;
    
    @Test
    public void testGetCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("first");
        String expectJson = "{\"version\":null,\"id\":null,\"customerId\":null,\"firstName\":\"first\",\"lastName\":null,\"dateOfBirth\":null,\"homeAddress\":null,\"postalAddress\":null,\"workAddress\":null}";
        when(service.getCustomerById(1)).thenReturn(customer);
        this.mockMvc.perform(get("/customer/show/1")).andDo(print()).andExpect(status().isOk()).andExpect(content().json(expectJson));
    }
    
    //TODO: Other test cases
    
}