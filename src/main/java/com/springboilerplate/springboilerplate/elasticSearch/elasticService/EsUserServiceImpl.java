package com.springboilerplate.springboilerplate.elasticSearch.elasticService;

import com.springboilerplate.springboilerplate.elasticSearch.elasticQueryBuilder.EsUserQueryBuilder;
import com.springboilerplate.springboilerplate.elasticSearch.elasticModel.EsUser;
import com.springboilerplate.springboilerplate.elasticSearch.elasticRepository.EsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EsUserServiceImpl implements EsUserService {

    private EsUserQueryBuilder userQueryBuilder;
    private EsUserRepository esUserRepository;

    @Autowired
    public EsUserServiceImpl(EsUserQueryBuilder esUserQueryBuilder, EsUserRepository esUserRepository) {
        this.userQueryBuilder = esUserQueryBuilder;
        this.esUserRepository = esUserRepository;
    }

    @Override
    public Page<EsUser> findUser(String query, Integer pageNumber) {
        if(!query.isEmpty()){
            PageRequest pageRequest = new PageRequest(pageNumber - 1, 10);
            return userQueryBuilder.findAllUsers(query, pageRequest);
        }
        return null;
    }

    @Override
    public void createEsUser(EsUser esUser, Long userId) {
        esUser.setId(userId);
        esUserRepository.save(esUser);
    }

    @Override
    public void updateUser(EsUser esUser) {
        EsUser previousUser = this.esUserRepository.findOne(esUser.getId());
        if(previousUser == null){
            throw new EntityNotFoundException("User with the id " + esUser.getId() + " could not be persisted in elastic search db");
        }
        update(esUser, previousUser);
    }

    @Override
    public void deleteUser(String email) {
        EsUser user = this.esUserRepository.findByEmail(email);
        if(user == null){
            throw new EntityNotFoundException("No user with username = "+email);
        }
        esUserRepository.delete(user);

    }

    @Override
    public void enableOrDisable(Long userId) {
        EsUser esUser = esUserRepository.findOne(userId);
        if(esUser == null){
            throw new EntityNotFoundException("User not found with id " + userId);
        }
        esUser.setEnabled(!esUser.isEnabled());
        esUserRepository.save(esUser);
    }

    private void update(EsUser userES, EsUser userDetails) {
        userDetails.setEmail(userES.getEmail());
        userDetails.setFirstname(userES.getFirstname());
        userDetails.setLastname(userES.getLastname());
        esUserRepository.save(userDetails);
    }
}
