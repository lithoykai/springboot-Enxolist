package com.enxolist.enxolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enxolist.enxolist.model.Response.UserResponse;
import com.enxolist.enxolist.persistence.entity.user.User;
import com.enxolist.enxolist.persistence.repositories.IUserRepository;


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
}
