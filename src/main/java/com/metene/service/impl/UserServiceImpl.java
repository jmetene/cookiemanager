package com.metene.service.impl;

import com.metene.domain.entity.User;
import com.metene.domain.repository.UserRepository;
import com.metene.service.JWTService;
import com.metene.service.UserService;
import com.metene.service.dto.*;
import com.metene.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Override
    public void updateUser(UserRequest userRequest, String token) {
        User user = userRepository.findByUsername(getUserName(token)).orElseThrow();

        Integer id = user.getId();
        User userToUpdate = UserMapper.toEntity(userRequest);
        userToUpdate.setId(id);
        userRepository.save(userToUpdate);
    }

    @Override
    public UserResponse getUser(String token) {
        return UserMapper.toDTO(userRepository.findByUsername(getUserName(token)).orElseThrow());
    }

    private String getUserName(String token) {
        return jwtService.getUsernameFromToken(token);
    }
}
