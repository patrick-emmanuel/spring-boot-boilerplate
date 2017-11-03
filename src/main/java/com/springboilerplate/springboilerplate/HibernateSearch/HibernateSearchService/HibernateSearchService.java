package com.springboilerplate.springboilerplate.HibernateSearch.HibernateSearchService;


import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

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

    public void initializeHibernateSearch() throws InterruptedException{
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer().startAndWait();
    }
}
