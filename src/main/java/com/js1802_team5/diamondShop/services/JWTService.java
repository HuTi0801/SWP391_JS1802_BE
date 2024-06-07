package com.js1802_team5.diamondShop.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    boolean checkTokenIsValid(String token);

    void invalidateToken(String token);
}
