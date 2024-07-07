package com.metene.controller;

import com.metene.common.JWTUtils;
import com.metene.service.CookieService;
import com.metene.service.UserService;
import com.metene.service.dto.*;
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

    @PostMapping(value = "/cookies")
    @PreAuthorize("hasAuthority('USER')")
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
    @PreAuthorize("hasAuthority('USER')")
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
    @PreAuthorize("hasAuthority('USER')")
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
    @PreAuthorize("hasAuthority('USER')")
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

    @PostMapping(value = "/banners")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> createCookieBanner(WebRequest request, @RequestBody CookieBannerRequest banner) {

        if (banner == null)
            return  ResponseEntity.badRequest().body("Error en la información del banner");

        try {
            userService.saveCookieBanner(JWTUtils.extractTokenFromRequest(request), banner);
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok("Banner creado correctamente");
    }

    @GetMapping(value = "/banners")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<CookieBannerResponse>> getAllBanners(WebRequest request) {
        List<CookieBannerResponse> banners;
        try {
            banners = userService.getCookieBanners(JWTUtils.extractTokenFromRequest(request));
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(banners);
    }

    @PutMapping(value = "/banners/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> updateCookieBanner(WebRequest request, @PathVariable Long id, @RequestBody CookieBannerRequest banner) {
        try {
            userService.updateCookieBanner(JWTUtils.extractTokenFromRequest(request), banner, id);
        } catch (NoSuchFieldException e) {
            return  ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok("Banner actualizado correctamente");
    }
}
