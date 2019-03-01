package com.dorjear.study.customer.services;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.dorjear.study.customer.domain.Customer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 
 * @author dorjear
 * @Note This implementation is just temporary solution as the API of the CRMS system is still in design stage and not yet finalized
 * 
 */

//@Service("customerServiceRestful")
public class CustomerServiceRestfulImpl implements CustomerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${crms.baseUrl}")
    private String baseUrl;
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

    private WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();

    @Override
    public Flux<Customer> listAllCustomers() {
        logger.debug("listAllCustomers called");
        return webClient.get().uri(baseUrl + listUrl)
                .exchange().subscribeOn(Schedulers.elastic()).timeout(Duration.ofSeconds(2))
                .flatMapMany(r -> r.bodyToFlux(Customer.class));
    }

    @Override
    public Mono<Customer> getCustomerById(Integer id) {
        logger.debug("getCustomerById called");
        return webClient.get().uri(baseUrl + detailUrl + id)
                .exchange().subscribeOn(Schedulers.elastic()).timeout(Duration.ofSeconds(2))
                .flatMap(r -> r.bodyToMono(Customer.class));
    }

    @Override
    public Mono<Customer> saveCustomer(Customer customer) {
        logger.debug("saveCustomer called");
        return webClient.post().uri(baseUrl + createUrl).body(BodyInserters.fromObject(customer))
                .exchange().subscribeOn(Schedulers.elastic()).timeout(Duration.ofSeconds(2))
                .flatMap(r -> r.bodyToMono(Customer.class));
    }

    @Override
    public void deleteCustomer(Integer id) {
        logger.debug("deleteCustomer called");
        webClient.delete().uri(baseUrl + deleteUrl + id)
                .exchange().subscribeOn(Schedulers.elastic()).timeout(Duration.ofSeconds(2));
    }
}
