package com.enxolist.enxolist.domain.persistence.entity.user;

public record LoginResponseDTO(String id, String name, String email, String token, int expiryDate) {
    
}
