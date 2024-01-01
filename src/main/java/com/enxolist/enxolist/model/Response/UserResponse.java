package com.enxolist.enxolist.model.Response;


import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.enxolist.enxolist.persistence.entity.user.UserRole;

import lombok.Data;

@Data
public class UserResponse {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String email;
    
    private String name;
    private UserRole role;
    @CreatedDate
    private Date createdAt;


}
