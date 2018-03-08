package com.springboilerplate.springboilerplate.app.search;


import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class HibernateSearchService {

    private EntityManager entityManager;

    @Autowired
    public HibernateSearchService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public HibernateSearchService() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    public void initializeHibernateSearch() throws InterruptedException {
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer().startAndWait();
    }
}