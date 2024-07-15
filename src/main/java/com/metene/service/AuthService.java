package com.metene.service;

import com.metene.service.dto.*;

public interface AuthService {
    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    String logout(String token);
    UserResponse updatePassword(PasswordResetRequest passReset);
}
