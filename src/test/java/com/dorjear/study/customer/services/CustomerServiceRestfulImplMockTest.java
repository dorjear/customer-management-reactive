package com.dorjear.study.customer.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.reactivestreams.Subscriber;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.dorjear.study.customer.domain.Customer;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;


/**
 * 
 * @author dorjear
 * @Note This implementation is just temporary solution as the API of the CRMS system is still in design stage and not yet finalized
 */
public class CustomerServiceRestfulImplMockTest {

    @Mock
    private WebClient webClient;
    @Mock
    private Customer customer;
    @Mock
    private ResponseEntity<List<Customer>> mockResponseEitity;
//    @Mock
//    private Mono<ClientResponse> clientResponseMono;
    @Mock
    private Function<ClientResponse, Mono<Customer>> subscribeFunction;
    @Mock
    private WebClient.RequestHeadersUriSpec uriSpecMock;
    @Mock
    private WebClient.RequestBodyUriSpec bodyUriSpecMock;
    @Mock
    private WebClient.RequestHeadersSpec headersSpecMock;
    @Mock
    private ClientResponse clientResponse;




    @InjectMocks
    private CustomerServiceRestfulImpl customerServiceImpl;
    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        customerServiceImpl=new CustomerServiceRestfulImpl();
        ReflectionTestUtils.setField(customerServiceImpl,"baseUrl", "http://host");
        ReflectionTestUtils.setField(customerServiceImpl,"detailUrl", "/detailUrl/");
        ReflectionTestUtils.setField(customerServiceImpl,"createUrl", "/createUrl/");
        ReflectionTestUtils.setField(customerServiceImpl,"deleteUrl", "/deleteUrl/");
    }

    private WebClient getWebClientMock(final Customer resp) {


        when(webClient.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(ArgumentMatchers.<String>notNull())).thenReturn(headersSpecMock);
        when(headersSpecMock.header(notNull(), notNull())).thenReturn(headersSpecMock);
        when(headersSpecMock.headers(notNull())).thenReturn(headersSpecMock);
        when(clientResponse.bodyToMono(Customer.class)).thenReturn(Mono.just(customer));

        Mono<ClientResponse> clientResponseMono = Mono.just(clientResponse);
        when(headersSpecMock.exchange()).thenReturn(clientResponseMono);

//        when(clientResponseMono.subscribeOn(any(Scheduler.class))).thenReturn(clientResponseMono);
//        when(clientResponseMono.timeout(ArgumentMatchers.<Duration>notNull())).thenReturn(clientResponseMono);
//        when(clientResponseMono.flatMap(ArgumentMatchers.<Function>notNull())).thenReturn(Mono.just(customer));
        return webClient;
    }
    @Test
    public void shouldReturnCustomer_whenGetCustomerByIdIsCalled() throws Exception {
        // Arrange
        when(webClient.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(ArgumentMatchers.<String>notNull())).thenReturn(headersSpecMock);
        when(headersSpecMock.header(notNull(), notNull())).thenReturn(headersSpecMock);
        when(headersSpecMock.headers(notNull())).thenReturn(headersSpecMock);
        when(clientResponse.bodyToMono(Customer.class)).thenReturn(Mono.just(customer));
        Mono<ClientResponse> clientResponseMono = Mono.just(clientResponse);
        when(headersSpecMock.exchange()).thenReturn(clientResponseMono);

        ReflectionTestUtils.setField(customerServiceImpl,"webClient", webClient);

        // Act
        Customer retrievedCustomer = customerServiceImpl.getCustomerById(5).block();
        // Assert
        assertThat(retrievedCustomer, is(equalTo(customer)));

        //TODO:
        //Verify
    }

    @Test
    public void shouldReturnCustomer_whenSaveCustomerIsCalled() throws Exception {
        // Arrange
        when(webClient.post()).thenReturn(bodyUriSpecMock);
        when(bodyUriSpecMock.uri(ArgumentMatchers.<String>notNull())).thenReturn(bodyUriSpecMock);
        when(bodyUriSpecMock.body(ArgumentMatchers.<BodyInserter>notNull())).thenReturn(headersSpecMock);
        when(headersSpecMock.header(notNull(), notNull())).thenReturn(headersSpecMock);
        when(headersSpecMock.headers(notNull())).thenReturn(headersSpecMock);
        when(clientResponse.bodyToMono(Customer.class)).thenReturn(Mono.just(customer));
        Mono<ClientResponse> clientResponseMono = Mono.just(clientResponse);
        when(headersSpecMock.exchange()).thenReturn(clientResponseMono);
        ReflectionTestUtils.setField(customerServiceImpl,"webClient", webClient);

        // Act
        Customer savedCustomer = customerServiceImpl.saveCustomer(customer).block();
        // Assert
        assertThat(savedCustomer, is(equalTo(customer)));

        //TODO:
        //Verify
    }

    @Test
    public void shouldCallDeleteMethodOfCustomerRepository_whenDeleteCustomerIsCalled() throws Exception {
        // Arrange
        when(webClient.delete()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(ArgumentMatchers.<String>notNull())).thenReturn(headersSpecMock);
        Mono<ClientResponse> clientResponseMono = Mono.just(clientResponse);
        when(headersSpecMock.exchange()).thenReturn(clientResponseMono);

        ReflectionTestUtils.setField(customerServiceImpl,"webClient", webClient);
        // Act
        customerServiceImpl.deleteCustomer(5);
        // Assert
        verify(webClient, times(1)).delete();
    }
}