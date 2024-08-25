package com.metene.user;

import com.metene.utiles.RequestUtils;
import com.metene.user.dto.UserRequest;
import com.metene.user.dto.UserResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserResponse> getUserInfo(WebRequest request) {
        UserResponse user;
        try {
            user = userService.getUser(RequestUtils.extractTokenFromRequest(request));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return ResponseEntity.internalServerError().body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> updateUserData(WebRequest request, @RequestBody UserRequest user) {
        if (user == null) return ResponseEntity.badRequest().body("Error en los datos del usuario");

        try {
            userService.updateUser(user, RequestUtils.extractTokenFromRequest(request));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenceException e) {
            return ResponseEntity.internalServerError().body("Error en el servidor");
        }

        return ResponseEntity.ok("Usuario actualizado correctamente");
    }

    @PostMapping("/subscriptions/{planeName}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> changeSuscriptionPlan(WebRequest request, @PathVariable String planeName) {
        if (RequestUtils.isNoValidSuscriptionName(planeName))
            return ResponseEntity.badRequest().build();

        try {
            userService.changeSuscriptionPlan(planeName.toLowerCase(), RequestUtils.extractTokenFromRequest(request));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("OK");
    }
}
