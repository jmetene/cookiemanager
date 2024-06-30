package com.metene.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String getUsernameFromToken(String token);
    String generateToken(UserDetails user);
    boolean isTokenValid(String token, UserDetails userDetails);
}
