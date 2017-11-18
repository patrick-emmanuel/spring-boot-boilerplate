package com.springboilerplate.springboilerplate.config;

import com.springboilerplate.springboilerplate.HibernateSearch.HibernateSearchService.HibernateSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class HibernateSearchConfig {

    @Autowired
    private EntityManager entityManager;
    @Bean
    public HibernateSearchService hibernateSearchService(){
        return new HibernateSearchService(entityManager);
    }



}
