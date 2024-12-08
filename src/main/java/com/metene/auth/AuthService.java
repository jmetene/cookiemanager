package com.metene.auth;

import com.metene.auth.dto.AuthResponse;
import com.metene.auth.dto.LoginRequest;
import com.metene.auth.dto.PasswordResetRequest;
import com.metene.auth.dto.RegisterRequest;
import com.metene.user.*;
import com.metene.user.dto.UserMapper;
import com.metene.user.dto.UserResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final TokenBlackList tokenBlackList;

    public String register(RegisterRequest request) {
        if (emailExists(request.getEmail())) {
            throw new EntityExistsException("Email already exists");
        }
        String lastName;
        String firstName = null;

        if (request.getSurnames().contains(" ")) {
            firstName = request.getSurnames().substring(0, request.getSurnames().indexOf(" "));
            lastName = request.getSurnames().substring(request.getSurnames().lastIndexOf(" ") + 1);
        } else  {
            lastName = request.getSurnames();
        }

        User user = User.builder()
//                .username(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .firstName(firstName)
                .lastName(lastName)
                .company(request.getCompany())
                .suscriptionPlan(request.getSuscriptionPlan())
                .role(Role.USER)
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            LOGGER.debug("[AuthService] - register: Error al registrar el usuario {}", e.getMessage(), e);
            throw  new PersistenceException("Error al registrar el usuario");
        }

        return "OK";
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication;
        User userDetails = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        try {
            authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getEmail(),
                    request.getPassword()));
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException("Error al autenticar el usuario");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(userDetails);
        UserResponse userResponse = UserMapper.toDTO(userDetails);
        return AuthResponse.builder().user(userResponse).token(token).build();
    }

    public String logout(String token) {
        tokenBlackList.addToBlackList(token);
        SecurityContextHolder.clearContext();
        return "Logget out successfully";
    }

    public UserResponse updatePassword(PasswordResetRequest passReset) {
        User user = userRepository.findByEmail(passReset.getEmail()).orElseThrow();

        // Si las contraseñas son iguales, no se efectúa el cambio
        if (passwordEncoder.matches(passReset.getPassword(), user.getPassword())) return null;

        user.setPassword(passwordEncoder.encode(passReset.getPassword()));

        return UserMapper.toDTO(userRepository.saveAndFlush(user));
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Método para renovar el token del usuario.
     * @param userToken token del usuario a renovar
     * @return {@link AuthResponse}
     */
    public AuthResponse renewUserToken(String userToken) {
        AuthResponse response;
        String email = jwtService.getUserFromToken(userToken);

        // Buscamos el usuario por su nombre
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        UserResponse userResponse = UserMapper.toDTO(user);
        // Probamos si el token está caducado
        if (!jwtService.isTokenValid(userToken, user )) {
            // Si lo está, lo renovamos
            String token = jwtService.generateToken(user);
            response = AuthResponse.builder().user(userResponse).token(token).build();
        } else {
            // Devolvemos el token actual
            response = AuthResponse.builder().user(userResponse).token(userToken).build();
        }

        return response;
    }
}
