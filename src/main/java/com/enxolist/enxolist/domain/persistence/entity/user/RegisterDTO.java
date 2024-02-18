package com.enxolist.enxolist.domain.persistence.entity.user;

public record RegisterDTO(String email, String password, String name, UserRole role) {
    
}
