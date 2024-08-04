package com.metene.controller;

import com.metene.common.JWTUtils;
import com.metene.service.SubscriptionService;
import com.metene.service.dto.SubscriptionRequest;
import com.metene.service.dto.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService suscriptionService;

    @GetMapping("/subscriptions")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<SubscriptionResponse>> getAll() {
        return ResponseEntity.ok(suscriptionService.getAllSuscriptionsPlan());
    }

    @GetMapping("/subscriptions/{planeName}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<SubscriptionResponse> getSuscription(@PathVariable String planeName) {
        if (JWTUtils.isNoValidSuscriptionName(planeName))
            return ResponseEntity.badRequest().build();

        SubscriptionResponse response;

        try {
            response = suscriptionService.getSuscription(planeName.toLowerCase());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/subscriptions")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> createSuscription(@RequestBody SubscriptionRequest subscriptionPlanRequest) {
        // Acutalizar después con validaciones
        if (isNoValidSucriptionPlan(subscriptionPlanRequest))
            return ResponseEntity.badRequest().build();

        try {
            suscriptionService.addSuscription(subscriptionPlanRequest);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Created");
    }

    @PutMapping("/subscriptions/{planeName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateSuscription(@RequestBody SubscriptionRequest request, @PathVariable String planeName) {
        // Acutalizar después con validaciones
        if (isNoValidSucriptionPlan(request))
            return ResponseEntity.badRequest().build();

        try {
            suscriptionService.updateSuscription(request, planeName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/subscriptions/{planeName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteSuscription(@PathVariable String planeName) {
        if (JWTUtils.isNoValidSuscriptionName(planeName))
            return ResponseEntity.badRequest().build();

        try {
            suscriptionService.deleteSuscription(planeName);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Deleted");
    }

    private boolean isNoValidSucriptionPlan(SubscriptionRequest request) {
        return request.getName() == null || request.getName().isEmpty();
    }
}
