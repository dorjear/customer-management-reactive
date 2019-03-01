package com.dorjear.study.customer.controllers;

import com.dorjear.study.customer.domain.Customer;
import com.dorjear.study.customer.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.fromRunnable;

@RestController
@RequestMapping("/customer")
@Api(value = "customer-management", description = "Operations pertaining to customers")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public void setcustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "View a list of available customers")
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public Flux<Customer> list() {
        return customerService.listAllCustomers();
    }

    @ApiOperation(value = "Search a customer with an ID", response = Customer.class)
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET, produces = "application/json")
    public Mono<Customer> showcustomer(@PathVariable Integer id, Authentication authentication) {
        return customerService.getCustomerById(id).doOnNext(c -> verify(authentication, c));
    }


    private void verify(Authentication authentication, Customer customer) {
        if(authentication == null || customer==null) return;
        String userDetails = (String) authentication.getPrincipal();
        if(authentication.getAuthorities().stream().noneMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority())) && !customer.getCustomerId().equals(userDetails)) throw new AuthorizationServiceException("Not authorized");
    }

    @ApiOperation(value = "Add a customer")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public Mono<ResponseEntity<String>> saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer).map(newCust -> new ResponseEntity<>("Customer saved successfully", HttpStatus.OK));
    }

    @ApiOperation(value = "Update a customer")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json")
    public Mono<ResponseEntity<String>> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer, Authentication authentication) {
        return customerService.getCustomerById(id).flatMap(storedCustomer -> {
            verify(authentication, storedCustomer);
            storedCustomer.setFirstName(customer.getFirstName());
            storedCustomer.setLastName(customer.getLastName());
            storedCustomer.setDateOfBirth(customer.getDateOfBirth());
            return customerService.saveCustomer(storedCustomer).map(newCust -> new ResponseEntity<>("Customer updated successfully", HttpStatus.OK));
        });
    }

    @ApiOperation(value = "Delete a customer")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public Mono<ResponseEntity<String>> delete(@PathVariable Integer id) {
        return fromRunnable(() -> customerService.deleteCustomer(id)).map(aVoid -> new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK));
    }

}
