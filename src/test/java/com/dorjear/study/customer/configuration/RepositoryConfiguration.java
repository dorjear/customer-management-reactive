package com.dorjear.study.customer.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.dorjear.study.customer.domain"})
@EnableJpaRepositories(basePackages = {"com.dorjear.study.customer.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
