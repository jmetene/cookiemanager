package com.metene.controller;

import com.metene.common.JWTUtils;
import com.metene.service.CookieService;
import com.metene.service.UserService;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.CookieResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CookieService cookieService;

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
        }
        return ResponseEntity.ok("Successfully loaded cookies");
    }

    @GetMapping(value = "/cookies")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CookieResponse>> getAllCookies(WebRequest request) {
        List<CookieResponse> response;

        try {
            response = cookieService.getAllCookies(JWTUtils.extractTokenFromRequest(request));
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/cookies/{name}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CookieResponse> showCookieDetails(WebRequest request, @PathVariable String name) {
        CookieResponse cookie;
        try {
            cookie = cookieService.getCookie(JWTUtils.extractTokenFromRequest(request), name);
        } catch (NoSuchElementException  e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(cookie);
    }

    @DeleteMapping(value = "/cookies/{name}")
    public ResponseEntity<String> deleteCookie(WebRequest request, @PathVariable String name) {
        try {
            userService.deleteCookie(JWTUtils.extractTokenFromRequest(request), name);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Cookie removed");
    }
}
