package com.springboilerplate.springboilerplate.elasticSearch.elasticQueryBuilder;

import com.springboilerplate.springboilerplate.elasticSearch.elasticModel.EsUser;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Component
public class EsUserQueryBuilderImpl implements EsUserQueryBuilder {

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    public EsUserQueryBuilderImpl() {
    }

    @Override
    public Page<EsUser> findAllUsers(String text, Pageable pageable) {
        String keyword = text.toLowerCase(Locale.ROOT);
        final BoolQueryBuilder builder = boolQuery();
        builder.should(queryStringQuery("*" + keyword + "*")
                .lenient(true)
                .field("email").field("firstName").field("lastName"))
                .should(queryStringQuery(keyword)
                .lenient(true)
                .field("email").field("firstName").field("lastName"));
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForPage(build, EsUser.class);
    }
}
