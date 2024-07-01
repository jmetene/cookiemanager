package com.metene.controller;

import com.metene.common.JWTUtils;
import com.metene.service.UserService;
import com.metene.service.dto.CookieRequest;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profile")
    public String welcome() {
        return "Welcome to Cookie Manager";
    }


    @PostMapping(value = "/cookies")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> loadCookies(WebRequest request, @RequestBody List<CookieRequest> cookiesToLoad) {

        if (cookiesToLoad == null || cookiesToLoad.isEmpty())
            return  ResponseEntity.badRequest().body("La lista de cookies no puede ser nula o vacía");

        try {
            userService.saveCookies(cookiesToLoad, JWTUtils.extractTokenFromRequest(request));
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("Token has been revoke", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok("Successfully loaded cookies");
    }
}
