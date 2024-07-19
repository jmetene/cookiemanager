package com.metene.controller;

import com.metene.domain.entity.CookieStatistics;
import com.metene.service.CookieService;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.CookieResponse;
import com.metene.service.dto.Statistic;
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

    @GetMapping(value = "/cookie/{domainId}/{name}")
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

    @PostMapping(value = "/cookie/{domainId}/{name}/estatistics")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> cargarEstadisticas(@PathVariable Long domainId,
                                                             @PathVariable String name,
                                                             @RequestBody List<Statistic> statistics) {
        try {
            cookieService.cargarEstadisticas(domainId, name, statistics);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/cookie/{domainId}/{name}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> actualizarCookie(@PathVariable Long domainId, @PathVariable String name, @RequestBody CookieRequest request) {
        try {
            cookieService.update(domainId, name,request);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/cookie/{domainId}")
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

    @DeleteMapping(value = "/cookie/{domainId}/{name}")
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
