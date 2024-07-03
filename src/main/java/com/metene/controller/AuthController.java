package com.metene.controller;

import com.metene.common.JWTUtils;
import com.metene.service.AuthService;
import com.metene.service.common.ResponseErrorBuilder;
import com.metene.service.dto.AuthResponse;
import com.metene.service.dto.LoginRequest;
import com.metene.service.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Controller to perform authentication operations")
public class AuthController {
    private final AuthService authService;
    private final ResponseErrorBuilder builder;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Operation(summary = "This method is for logging in")
    @PostMapping(value = "/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Set<ConstraintViolation<Object>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(builder.buildBadRequestError(BAD_REQUEST, violations));
        }
        ResponseEntity<AuthResponse> ok;
        try {
            ok = ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(builder.buildInternalServerError(INTERNAL_SERVER_ERROR));
        }
        return ok;
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
