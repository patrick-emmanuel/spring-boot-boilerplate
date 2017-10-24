package com.springboilerplate.springboilerplate.elasticRepository;

import com.springboilerplate.springboilerplate.model.elasticModel.EsUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsUserRepository extends ElasticsearchRepository<EsUser, String>{
}
