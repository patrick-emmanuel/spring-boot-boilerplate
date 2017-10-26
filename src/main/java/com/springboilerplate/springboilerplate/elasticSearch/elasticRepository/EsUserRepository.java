package com.springboilerplate.springboilerplate.elasticSearch.elasticRepository;

import com.springboilerplate.springboilerplate.elasticSearch.elasticModel.EsUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsUserRepository extends ElasticsearchRepository<EsUser, Long>{
    EsUser findByEmail(String email);
}
