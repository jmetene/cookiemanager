package com.metene.service;

import com.metene.service.dto.AuthResponse;
import com.metene.service.dto.LoginRequest;
import com.metene.service.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
