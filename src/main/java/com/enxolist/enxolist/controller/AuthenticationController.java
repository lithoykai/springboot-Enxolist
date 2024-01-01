package com.enxolist.enxolist.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enxolist.enxolist.infra.security.TokenService;
import com.enxolist.enxolist.persistence.entity.user.AuthenticationDTO;
import com.enxolist.enxolist.persistence.entity.user.LoginResponseDTO;
import com.enxolist.enxolist.persistence.entity.user.RegisterDTO;
import com.enxolist.enxolist.persistence.entity.user.User;
import com.enxolist.enxolist.persistence.repositories.IUserRepository;
import com.enxolist.enxolist.services.AuthorizationService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthorizationService service;
    @Autowired
    private IUserRepository repository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), data.name(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok(this.service.createUserResponse(newUser));
    }
    
    
    
}
