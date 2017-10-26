package com.springboilerplate.springboilerplate.mapper;

import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.elasticSearch.elasticModel.EsUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EsUserMapper {

    private ModelMapper modelMapper;
    public EsUserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EsUser toEsUser(User user) {
        return modelMapper.map(user, EsUser.class);
    }

    public User toUser(EsUser esUser) {
        return modelMapper.map(esUser, User.class);
    }
}
