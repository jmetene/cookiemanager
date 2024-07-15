package com.metene.controller;

import com.metene.common.JWTUtils;
import com.metene.service.AuthService;
import com.metene.service.common.ResponseErrorBuilder;
import com.metene.service.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Controller to perform authentication operations")
public class AuthController {
    private final AuthService authService;
    private final ResponseErrorBuilder builder;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Operation(summary = "This method is for logging in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = HttpErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = HttpErrorResponse.class))})
    })
    @PostMapping(value = "/auth/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        Set<ConstraintViolation<Object>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(builder.buildBadRequestError(BAD_REQUEST, violations));
        }
        AuthResponse response;
        try {
            response = authService.login(request);
        }  catch (AuthenticationServiceException e) {
            return ResponseEntity.badRequest().body(builder.buildBadRequestError(BAD_REQUEST, "Incorrect user or password"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(builder.buildInternalServerError(INTERNAL_SERVER_ERROR));
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "This method is for registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = HttpErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict", content =
                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = HttpErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = HttpErrorResponse.class))})
    })
    @PostMapping(value = "/auth/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        Set<ConstraintViolation<Object>> violations = validator.validate(request);

        if (!violations.isEmpty())
            return ResponseEntity.badRequest().body(builder.buildBadRequestError(BAD_REQUEST, violations));
        String response;
        try {
            response = authService.register(request);
        } catch (EntityExistsException e) {
            return ResponseEntity.badRequest().body(builder.buildBadRequestError(CONFLICT, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(builder.buildInternalServerError(INTERNAL_SERVER_ERROR));
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "This method is for logging out")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = HttpErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
                    @Schema(implementation = HttpErrorResponse.class))})
    })
    @GetMapping(value = "/logout")
    public ResponseEntity<String> logout(WebRequest request) {
        return ResponseEntity.ok(authService.logout(JWTUtils.extractTokenFromRequest(request)));
    }

    @PutMapping(value = "/auth/resetPassword")
    public ResponseEntity<Object> changePassword(@RequestBody PasswordResetRequest passResetInfo) {
        Set<ConstraintViolation<Object>> violations = validator.validate(passResetInfo);

        if (!violations.isEmpty())
            return ResponseEntity.badRequest().body(builder.buildBadRequestError(BAD_REQUEST, violations));

        UserResponse response;

        try {
            response = authService.updatePassword(passResetInfo);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(builder.buildBadRequestError(BAD_REQUEST, "User not found"));
        } catch(Exception e) {
            return ResponseEntity.internalServerError().body(builder.buildInternalServerError(INTERNAL_SERVER_ERROR));
        }

        if (response == null)
            return ResponseEntity
                    .badRequest().body(builder.buildBadRequestError(BAD_REQUEST, "The password already exists"));

        return ResponseEntity.ok().build();
    }
}
