package com.metene.service.impl;

import com.metene.domain.entity.Role;
import com.metene.domain.entity.User;
import com.metene.domain.repository.UserRepository;
import com.metene.service.AuthService;
import com.metene.service.JWTService;
import com.metene.service.TokenBlackList;
import com.metene.service.dto.AuthResponse;
import com.metene.service.dto.LoginRequest;
import com.metene.service.dto.RegisterRequest;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final TokenBlackList tokenBlackList;


    @Override
    public String register(RegisterRequest request) {
        if (emailExists(request.getEmail())) {
            throw new AuthenticationServiceException("Email already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .company(request.getCompany())
                .role(Role.USER)
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            LOGGER.debug("[AuthServiceImpl] - register: Error al registrar el usuario {0}", e);
            throw  new PersistenceException("Error al registrar el usuario");
        }

        return "OK";
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException("Error al autenticar el usuario");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userDetails = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String token = jwtService.generateToken(userDetails);
        return AuthResponse.builder().token(token).build();
    }

    @Override
    public String logout(String token) {
        tokenBlackList.addToBlackList(token);
        SecurityContextHolder.clearContext();
        return "Logget out successfully";
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
