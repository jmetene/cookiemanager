package com.metene.controller;

import com.metene.common.JWTUtils;
import com.metene.service.IDomainService;
import com.metene.service.dto.CookieBannerRequest;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.DomainRequest;
import com.metene.service.dto.DomainResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DomainController {
    private  final IDomainService domainService;

    @GetMapping(value = "/domain")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<DomainResponse>> getDominio(WebRequest request) {
        List<DomainResponse> domains;
        try {
            domains = domainService.getAll(JWTUtils.extractTokenFromRequest(request));
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(domains);
    }

    @GetMapping(value = "/domain/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<DomainResponse> getDetalles(@PathVariable Long id) {
        DomainResponse domain;
        try {
            domain = domainService.getDetails(id);
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(domain);
    }

    @PostMapping(value = "/domain")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> crearDominio(WebRequest request, @RequestBody DomainRequest domain) {
        if (Objects.isNull(domain) || domain.getName().isEmpty())
            return  ResponseEntity.badRequest().body("Error en los parámetros de entrada");

        try {
            domainService.create(domain.getName(), JWTUtils.extractTokenFromRequest(request));
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok("Domain successfully created");
    }

    @DeleteMapping(value = "/domain/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> crearDominio(@PathVariable Long id) {

        try {
            domainService.delete(id);
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/domain")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> actualizarDominio(@RequestBody DomainRequest request) {
        if (Objects.isNull(request) || request.getName().isEmpty())
            return  ResponseEntity.badRequest().body("El los parámetros de entrada");

        try {
            domainService.update(request.getId(), request.getName());
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok("Domain successfully created");
    }

    @PostMapping(value = "/domain/{id}/loadCookies")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> cargarCookies(@PathVariable Long id, @RequestBody List<CookieRequest> cookies) {

        if (cookies == null || cookies.isEmpty())
            return  ResponseEntity.badRequest().body("La lista de cookies no puede ser nula o vacía");

        try {
            domainService.addCookies(id, cookies);
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/domain/{id}/banner")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> createCookieBanner(@PathVariable Long id, @RequestBody CookieBannerRequest banner) {

        if (banner == null)
            return  ResponseEntity.badRequest().body("Error en la información del banner");

        try {
            domainService.addBanner(id, banner);
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
