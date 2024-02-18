package com.enxolist.enxolist.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enxolist.enxolist.data.model.Response.UserResponse;
import com.enxolist.enxolist.data.repositories.IUserRepository;
import com.enxolist.enxolist.domain.persistence.entity.user.User;


@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    IUserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }

    public UserResponse createUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail()); 
        response.setName(user.getName());
        response.setCreatedAt(user.getCreatedAt());
        response.setRole(user.getRole());
        return response;
    }

    
    public void delteUser(String id) {
        repository.deleteById(id);
    }


     public List<User> listUsers(){ 
        List<User> users = this.repository.findAll();
        return users;
     }

}
