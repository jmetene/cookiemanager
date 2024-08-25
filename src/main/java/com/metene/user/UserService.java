package com.metene.user;

import com.metene.auth.JWTService;
import com.metene.user.dto.UserMapper;
import com.metene.user.dto.UserRequest;
import com.metene.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private static final String USER_NOT_FOUND_MESSAGE = "User not found";

    public void updateUser(UserRequest userRequest, String token) {
        User user = userRepository.findByUsername(jwtService.getUserFromToken(token))
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        Integer id = user.getId();
        User userToUpdate = UserMapper.toEntity(userRequest);
        userToUpdate.setId(id);
        userRepository.save(userToUpdate);
    }

    public UserResponse getUser(String token) {
        return UserMapper.toDTO(userRepository.findByUsername(jwtService.getUserFromToken(token))
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE)));
    }

    public void changeSuscriptionPlan(String planeName, String userToken) {
        User user = userRepository.findByUsername(jwtService.getUserFromToken(userToken))
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        user.setSuscriptionPlan(planeName);
        userRepository.save(user);
    }
}
