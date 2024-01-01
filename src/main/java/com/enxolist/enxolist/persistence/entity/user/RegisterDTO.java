package com.enxolist.enxolist.persistence.entity.user;

public record RegisterDTO(String email, String password, String name, UserRole role) {
    
}
