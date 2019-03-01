package com.dorjear.study.customer.bootstrap;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.dorjear.study.customer.domain.Address;
import com.dorjear.study.customer.domain.Customer;
import com.dorjear.study.customer.repositories.CustomertRepository;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CustomertRepository customerRepository;


    private Logger log = LogManager.getLogger(SpringJpaBootstrap.class);

    @Autowired
    public void setCustomerRepository(CustomertRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadCustomers();
    }

    private void loadCustomers() {
        Customer tomSmith = new Customer();
        tomSmith.setFirstName("Tom");
        tomSmith.setLastName("Smith");
        LocalDate localDate = LocalDate.of(1988, Month.AUGUST, 12);
        tomSmith.setDateOfBirth(Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        tomSmith.setCustomerId("TomSmith");
        Address tomAddress = new Address();
        tomAddress.setLine1("Tom St");
        tomAddress.setLine2("Tom city");
        tomSmith.setHomeAddress(tomAddress);
        tomSmith.setPostalAddress(tomAddress);
        customerRepository.save(tomSmith);

        log.info("Saved tom - id: " + tomSmith.getId());

        Customer davidSmith = new Customer();
        davidSmith.setFirstName("David");
        davidSmith.setLastName("Smith");
        LocalDate localDate2 = LocalDate.of(1999, Month.AUGUST, 12);
        davidSmith.setDateOfBirth(Date.from(localDate2.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        davidSmith.setCustomerId("DavidSmith");
        Address davidAddress = new Address();
        davidAddress.setLine1("David St");
        davidAddress.setLine2("David city");
        davidSmith.setHomeAddress(davidAddress);
        davidSmith.setPostalAddress(davidAddress);
        customerRepository.save(davidSmith);

        log.info("Saved david - id:" + davidSmith.getId());
    }


    }



