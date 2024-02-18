package com.enxolist.enxolist.data.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.enxolist.enxolist.data.model.Response.UserResponse;
import com.enxolist.enxolist.domain.persistence.entity.user.User;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

    @Query(value = "{ 'email' : ?0 }", fields = "{ 'email' : 1, 'name' : 1, 'createdAt' : 1 }")
    UserResponse findUserResponseByEmail(String email);

    @Query("{ 'email' : ?0 }")
    public User findByEmail(String email);

    public List<User> findAll();

}
