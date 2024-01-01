package com.enxolist.enxolist.persistence.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.enxolist.enxolist.model.Response.UserResponse;
import com.enxolist.enxolist.persistence.entity.user.User;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

    @Query(value = "{ 'email' : ?0 }", fields = "{ 'email' : 1, 'name' : 1, 'createdAt' : 1 }")
    UserResponse findUserResponseByEmail(String email);

    @Query("{ 'email' : ?0 }")
    public User findByEmail(String email);

}
