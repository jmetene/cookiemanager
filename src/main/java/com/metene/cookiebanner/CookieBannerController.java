package com.metene.cookiebanner;

import com.metene.cookiebanner.dto.CookieBannerRequest;
import com.metene.cookiebanner.dto.CookieBannerResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
public class CookieBannerController {

    private final CookieBannerService bannerService;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CookieBannerResponse> getBanner(@PathVariable Long id) {
        CookieBannerResponse banner;
        try {
            banner = bannerService.getCookieBanner(id);
        } catch (CookieBannerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(banner);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> updateBanner(@PathVariable Long id, @RequestBody CookieBannerRequest request) {
        try {
            bannerService.updateCookieBanner(id, request);
        } catch (CookieBannerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value ="/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> deleteBanner(@PathVariable Long id) {
        try {
            bannerService.deleteCookieBanner(id);
        } catch (CookieBannerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return  ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
