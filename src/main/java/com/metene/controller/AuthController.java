package com.metene.controller;

import com.metene.common.JWTUtils;
import com.metene.service.AuthService;
import com.metene.service.dto.AuthResponse;
import com.metene.service.dto.LoginRequest;
import com.metene.service.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Controller to perform authentication operations")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "This method is for logging in")
    @PostMapping(value = "/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "This method is for registration")
    @PostMapping(value = "/auth/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @Operation(summary = "This method is for logging out")
    @GetMapping(value = "/logout")
    public ResponseEntity<String> logout(WebRequest request) {
        return ResponseEntity.ok(authService.logout(JWTUtils.extractTokenFromRequest(request)));
    }
}
