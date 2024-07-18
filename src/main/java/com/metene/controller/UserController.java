package com.metene.controller;

import com.metene.common.JWTUtils;
import com.metene.service.UserService;
import com.metene.service.dto.*;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/info")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserResponse> getUserInfo(WebRequest request) {
        UserResponse user;
        try {
            user = userService.getUser(JWTUtils.extractTokenFromRequest(request));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return ResponseEntity.internalServerError().body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> updateUserData(WebRequest request, @RequestBody UserRequest user) {
        if (user == null) return ResponseEntity.badRequest().body("Error en los datos del usuario");

        try {
            userService.updateUser(user, JWTUtils.extractTokenFromRequest(request));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return ResponseEntity.internalServerError().body("Error en el servidor");
        }

        return ResponseEntity.ok("Usuario actualizado correctamente");
    }
}
