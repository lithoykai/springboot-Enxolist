package com.enxolist.enxolist.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enxolist.enxolist.data.model.Response.UserResponse;
import com.enxolist.enxolist.data.repositories.IUserRepository;
import com.enxolist.enxolist.data.services.AuthorizationService;
import com.enxolist.enxolist.domain.persistence.entity.user.AuthenticationDTO;
import com.enxolist.enxolist.domain.persistence.entity.user.LoginResponseDTO;
import com.enxolist.enxolist.domain.persistence.entity.user.RegisterDTO;
import com.enxolist.enxolist.domain.persistence.entity.user.User;
import com.enxolist.enxolist.infra.failure.ErrorResponse;
import com.enxolist.enxolist.infra.security.TokenService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/auth")
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
        User loginUser = repository.findByEmail(data.email());

      

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(loginUser.getId(), loginUser.getName(), loginUser.getEmail(), token, 720));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().body(new ErrorResponse("Email já existente."));

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), data.name(), encryptedPassword, data.role());

        this.repository.save(newUser);
        
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        UserResponse response = this.service.createUserResponse(newUser);

        return ResponseEntity.ok(new LoginResponseDTO(response.getId(), response.getName(),response.getEmail(), token, 720));
    }

    @DeleteMapping("/delUser/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){ 
        List<User> users = this.service.listUsers();
        this.service.delteUser(id);
        return ResponseEntity.ok(users); 
    }
    
    @GetMapping("/getUsers")
    public ResponseEntity getUsers() {
        List<User> users = this.service.listUsers();
        return ResponseEntity.ok(users);
    }
    

}
