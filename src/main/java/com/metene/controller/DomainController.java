package com.metene.controller;

import com.metene.common.JWTUtils;
import com.metene.service.CookieService;
import com.metene.service.DomainStatisticsService;
import com.metene.service.IDomainService;
import com.metene.service.dto.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DomainController {

    private final IDomainService domainService;
    private final CookieService cookieService;
    private final DomainStatisticsService statisticsService;

    @GetMapping(value = "/domains")
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

    @GetMapping(value = "/domains/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<DomainResponse> getDetalles(@PathVariable Long id) {
        DomainResponse domain;
        try {
            domain = domainService.getDetails(id);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(domain);
    }

    @PostMapping(value = "/domains")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> crearDominio(WebRequest request, @RequestBody DomainRequest domain) {
        if (Objects.isNull(domain) || domain.getNombre().isEmpty())
            return  ResponseEntity.badRequest().body("Error en los parámetros de entrada");

        try {
            domainService.create(domain, JWTUtils.extractTokenFromRequest(request));
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/domains/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> borrarDominio(@PathVariable Long id) {

        try {
            domainService.delete(id);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/domains/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> actualizarDominio(@PathVariable Long id, @RequestBody DomainRequest domain) {
        if (Objects.isNull(domain) || domain.getNombre().isEmpty())
            return  ResponseEntity.badRequest().body("El los parámetros de entrada");

        try {
            domainService.update(id, domain);
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/domains/{id}/cookies")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> cargarCookies(@PathVariable Long id, @RequestBody List<CookieRequest> cookies) {

        if (cookies == null || cookies.isEmpty())
            return  ResponseEntity.badRequest().body("La lista de cookies no puede ser nula o vacía");

        try {
            domainService.addCookies(id, cookies);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/domains/{domainId}/cookies")
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

    @PostMapping(value = "/domains/{id}/cookie")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> cargarCookie(@PathVariable Long id, @RequestBody CookieRequest cookie) {

        if (cookie == null)
            return  ResponseEntity.badRequest().body("Error en la información introducida");

        try {
            domainService.addCookie(id, cookie);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>("Ya existe la cookie", HttpStatus.CONFLICT);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/domains/{id}/banner")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> createCookieBanner(@PathVariable Long id, @RequestBody CookieBannerRequest banner) {

        if (banner == null)
            return  ResponseEntity.badRequest().body("Error en la información del banner");

        try {
            domainService.addBanner(id, banner);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/domains/{domainId}/cookies/{cookieId}/statistics")
    public ResponseEntity<List<StatisticResponse>> getStatistics(WebRequest request,
                                                                 @PathVariable Long domainId,
                                                                 @PathVariable Long cookieId) {

        if (!JWTUtils.getParametrosNoValidos(request).isEmpty())
            return ResponseEntity.badRequest().build();

        List<StatisticResponse> respone;
        try {
            respone = statisticsService
                    .getCookieStatistics(domainId, cookieId, request.getParameterMap().entrySet().stream().toList());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(respone);
    }

    @GetMapping(value = "/domains/{domainId}/statistics")
    public ResponseEntity<List<StatisticResponse>> getStatisticsByDomain(WebRequest request, @PathVariable Long domainId) {

        if (!JWTUtils.getParametrosNoValidos(request).isEmpty())
            return ResponseEntity.badRequest().build();

        List<StatisticResponse> respone;
        try {
            respone = statisticsService.getStatisticsByDomain(domainId, request.getParameterMap().entrySet().stream().toList());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(respone);
    }
}
