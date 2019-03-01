package com.dorjear.study.customer;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import com.dorjear.study.customer.controllers.CustomerController;
import com.dorjear.study.customer.domain.Customer;
import com.dorjear.study.customer.services.CustomerService;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(CustomerController.class)
//@WithMockUser(username = "ram", roles={"ADMIN"})
public class CustomerControllerWebMockTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CustomerService service;
    
    @Test
    public void testGetCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("first");
        String expectJson = "{\"version\":null,\"id\":null,\"customerId\":null,\"firstName\":\"first\",\"lastName\":null,\"dateOfBirth\":null,\"homeAddress\":null,\"postalAddress\":null,\"workAddress\":null}";
        when(service.getCustomerById(1)).thenReturn(Mono.just(customer));
        webTestClient.get().uri("/customer/show/1").exchange().expectStatus().is2xxSuccessful().expectBody().consumeWith(System.out::print).json(expectJson);
    }

    @Test
    public void testListCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("first");
        when(service.getCustomerById(1)).thenReturn(Mono.just(customer));
        webTestClient.get().uri("/customer/list").exchange().expectStatus().is2xxSuccessful().expectBody().consumeWith(System.out::print).json("[]");
    }

    //TODO: Other test cases
    
}