package com.dorjear.study.customer.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RestController;

@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.requestMatchers().antMatchers("/customer/**").and().authorizeRequests().anyRequest().authenticated()
                .and().authorizeRequests().antMatchers("/customer/list").access("hasRole('ADMIN')")
                .and().authorizeRequests().antMatchers("/customer/show").access("hasRole('USER')")
                .and().authorizeRequests().antMatchers("/customer/create").access("hasRole('ADMIN')")
                .and().authorizeRequests().antMatchers("/customer/update").access("hasRole('USER')")
                .and().authorizeRequests().antMatchers("/customer/delete").access("hasRole('ADMIN')")
        ;
    }
}