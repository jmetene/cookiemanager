package com.metene.controller;

import com.metene.service.CookieService;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.CookieResponse;
import com.metene.service.dto.Statistic;
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

    @GetMapping(value = "/cookies/{cookieId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CookieResponse> showCookieDetails(@PathVariable Long cookieId) {
        CookieResponse cookie;
        try {
            cookie = cookieService.getCookie(cookieId);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(cookie);
    }

    @PutMapping(value = "/cookies/{cookieId}/estatistics")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> cargarEstadisticas(@PathVariable Long cookieId, @RequestBody List<Statistic> statistics) {
        try {
            cookieService.cargarEstadisticas(cookieId, statistics);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/cookies/{cookieId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> actualizarCookie(@PathVariable Long cookieId, @RequestBody CookieRequest request) {
        try {
            cookieService.update(cookieId,request);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/cookies/{cookieId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> eliminarCookie(@PathVariable Long cookieId) {
        try {
           cookieService.delete(cookieId);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
