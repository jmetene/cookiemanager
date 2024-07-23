package com.metene.service;

import com.metene.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String getUsernameFromToken(String token);

    String generateToken(User user);

    boolean isTokenValid(String token, UserDetails userDetails);
}
