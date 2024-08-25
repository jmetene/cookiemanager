package com.metene.subscription;

import com.metene.utiles.RequestUtils;
import com.metene.subscription.dto.SubscriptionRequest;
import com.metene.subscription.dto.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService suscriptionService;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<SubscriptionResponse>> getAll() {
        return ResponseEntity.ok(suscriptionService.getAllSuscriptionsPlan());
    }

    @GetMapping("/{planeName}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<SubscriptionResponse> getSuscription(@PathVariable String planeName) {
        if (RequestUtils.isNoValidSuscriptionName(planeName))
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

    @PostMapping("/")
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

    @PutMapping("/{planeName}")
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

    @DeleteMapping("/{planeName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteSuscription(@PathVariable String planeName) {
        if (RequestUtils.isNoValidSuscriptionName(planeName))
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
