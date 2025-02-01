package com.metene.srciptbanner;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScriptBannerController {

    private final ScriptBannerService scriptBannerService;

    @CrossOrigin
    @GetMapping(value = "/scripts")
    public ResponseEntity<ScriptBannerResponse> getScriptBanner(WebRequest request, String apiKey) {

        ScriptBannerResponse respone;

        try {
            respone = scriptBannerService.getScriptBanner(apiKey);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(respone);
    }

    @CrossOrigin
    @PostMapping(value = "/scripts")
    public ResponseEntity<String> createScriptStatistics(WebRequest request, @RequestBody ScriptBannerRequest scriptBannerRequest) {

        String respone;

        try {
            respone = scriptBannerService.createStatistics(scriptBannerRequest);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(respone);
    }
}
