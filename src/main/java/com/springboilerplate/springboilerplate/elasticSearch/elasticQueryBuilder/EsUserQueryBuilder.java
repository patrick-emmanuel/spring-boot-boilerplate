package com.springboilerplate.springboilerplate.elasticSearch.elasticQueryBuilder;

import com.springboilerplate.springboilerplate.elasticSearch.elasticModel.EsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EsUserQueryBuilder {
    Page<EsUser> findAllUsers(String text, Pageable pageable);
}
