package com.metene.controller;

import com.metene.service.CookieService;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.CookieResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CookieController {
    private final CookieService cookieService;

    @GetMapping(value = "/cookie/{domainId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<CookieResponse>> getCookiesByDomain(@PathVariable Long domainId) {
        List<CookieResponse> cookies;
        try {
            cookies = cookieService.getAllCookies(domainId);
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(cookies);
    }

    @GetMapping(value = "/cookies/{domainId}/{name}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CookieResponse> showCookieDetails(@PathVariable Long domainId, @PathVariable String name) {
        CookieResponse cookie;
        try {
            cookie = cookieService.getCookie(domainId, name);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(cookie);
    }

    @PutMapping(value = "/cookies/{domainId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> actualizarCookie(@PathVariable Long domainId, @RequestBody CookieRequest request) {
        try {
            cookieService.update(domainId, request);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/cookies/{domainId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> addCookie(@PathVariable Long domainId, @RequestBody CookieRequest request) {
        try {
            cookieService.addCookie(domainId, request);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/cookies/{domainId}/{name}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> eliminarCookie(@PathVariable Long domainId, @PathVariable String name) {
        try {
           cookieService.delete(domainId, name);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
