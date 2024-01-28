package com.enxolist.enxolist.persistence.entity.user;

public record LoginResponseDTO(String id, String email, String token, int expiryDate) {
    
}
