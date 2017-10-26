package com.springboilerplate.springboilerplate.elasticSearch.elasticService;

import com.springboilerplate.springboilerplate.elasticSearch.elasticModel.EsUser;
import org.springframework.data.domain.Page;

public interface EsUserService {

    Page<EsUser> findUser(String query, Integer pageNumber);

    void createEsUser(EsUser esUser, Long userId);

    void updateUser(EsUser esUser);

    void deleteUser(String email);

    void enableOrDisable(Long userId);
}
