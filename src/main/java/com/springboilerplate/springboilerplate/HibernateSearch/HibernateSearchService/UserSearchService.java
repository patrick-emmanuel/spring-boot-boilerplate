package com.springboilerplate.springboilerplate.HibernateSearch.HibernateSearchService;

import com.springboilerplate.springboilerplate.model.User;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@Service
public class UserSearchService {

    HibernateSearchService hibernateSearchService;

    @Autowired
    public UserSearchService(HibernateSearchService hibernateSearchService) {
        this.hibernateSearchService = hibernateSearchService;
    }

    @PostConstruct
    public void initHibernateSearch() throws Exception{
        hibernateSearchService.initializeHibernateSearch();
    }
    @Transactional
    public List<User> findUsersByKeyword(String keyword){
        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(hibernateSearchService.getEntityManager());
        fullTextEntityManager.flushToIndexes();
        QueryBuilder queryBuilder = fullTextEntityManager
                .getSearchFactory().buildQueryBuilder().forEntity(User.class).get();
        Query luceneQuery = queryBuilder.keyword().fuzzy().withEditDistanceUpTo(1)
                .withPrefixLength(1).onFields("firstname", "lastname", "email")
                .matching(keyword).createQuery();
        javax.persistence.Query jpaQuery = fullTextEntityManager.
                createFullTextQuery(luceneQuery, User.class);
        return jpaQuery.getResultList();
    }
}
