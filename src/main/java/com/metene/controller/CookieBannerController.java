package com.metene.controller;

import com.metene.service.ICookieBannerService;
import com.metene.service.dto.CookieBannerRequest;
import com.metene.service.dto.CookieBannerResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CookieBannerController {

    private final ICookieBannerService bannerService;

    @GetMapping(value = "/banners/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CookieBannerResponse> getBanner(@PathVariable Long id) {
        CookieBannerResponse banner;
        try {
            banner = bannerService.getCookieBanner(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(banner);
    }

    @PutMapping(value = "/banner/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> updateBanner(@PathVariable Long id, @RequestBody CookieBannerRequest request) {
        try {
            bannerService.updateCookieBanner(id, request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/banner/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> deleteBanner(@PathVariable Long id) {
        try {
            bannerService.deleteCookieBanner(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
