package com.metene.service.impl;

import com.metene.domain.entity.Cookie;
import com.metene.domain.entity.User;
import com.metene.domain.repository.UserRepository;
import com.metene.service.JWTService;
import com.metene.service.UserService;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.UserRequest;
import com.metene.service.mapper.CookieMapper;
import com.metene.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Override
    public void saveCookies(List<CookieRequest> cookies, String token) {
        String username = jwtService.getUsernameFromToken(token.substring(7));

        User user = userRepository.findByUsername(username).orElse(new User());

        List<Cookie> cookiesToSave =  cookies.stream().map(CookieMapper::toEntity).toList();

        for (Cookie cookie : cookiesToSave)
            user.add(cookie);

        userRepository.save(user);
    }

    @Override
    public boolean updateUser(UserRequest userRequest, String token) {
        boolean result = false;

        String username = jwtService.getUsernameFromToken(token.substring(7));

        if (userRepository.findByUsername(username).isEmpty()) return result;

        User user = userRepository.findByUsername(username).orElse(new User());
        if (user.getId() == null) return result;

        Integer id = user.getId();
        User userToUpdate = UserMapper.toEntity(userRequest);
        userToUpdate.setId(id);
        userRepository.save(userToUpdate);

        return true;
    }
}
