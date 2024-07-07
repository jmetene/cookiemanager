package com.metene.service.impl;

import com.metene.domain.entity.Cookie;
import com.metene.domain.entity.CookieBanner;
import com.metene.domain.entity.User;
import com.metene.domain.repository.CookieBannerRepository;
import com.metene.domain.repository.CookieRepository;
import com.metene.domain.repository.UserRepository;
import com.metene.service.JWTService;
import com.metene.service.UserService;
import com.metene.service.dto.*;
import com.metene.service.mapper.CookieBannerMapper;
import com.metene.service.mapper.CookieMapper;
import com.metene.service.mapper.UserMapper;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final CookieRepository cookieRepository;
    private final CookieBannerRepository cookieBannerRepository;

    @Override
    public void saveCookies(List<CookieRequest> cookies, String token) {
        String username = getUserName(token);
        User user = userRepository.findByUsername(username).orElse(new User());
        List<Cookie> cookiesToSave =  cookies.stream().map(CookieMapper::toEntity).toList();

        for (Cookie cookie : cookiesToSave)
            user.add(cookie);

        userRepository.save(user);
    }

    @Override
    public void updateUser(UserRequest userRequest, String token) {
        User user = userRepository.findByUsername(getUserName(token)).orElseThrow();

        Integer id = user.getId();
        User userToUpdate = UserMapper.toEntity(userRequest);
        userToUpdate.setId(id);
        userRepository.save(userToUpdate);
    }

    @Override
    public void deleteCookie(String token, String cookieName) {
        String username = getUserName(token);
        User user = userRepository.findByUsername(username).orElseThrow();
        Cookie cookie = cookieRepository.findByNameAndUser(cookieName, username).orElseThrow();

        user.remove(cookie);
        userRepository.save(user);
    }

    @Override
    public void saveCookieBanner(String token, CookieBannerRequest banner) {
        String username = getUserName(token);

        User user = userRepository.findByUsername(username).orElseThrow();
        user.add(CookieBannerMapper.toEntity(banner));
        userRepository.save(user);
    }

    @Override
    public List<CookieBannerResponse> getCookieBanners(String token) {
        String username = getUserName(token);
        List<CookieBanner> banners = cookieBannerRepository.findCookieBannerByUserName(username).orElseThrow();
        return banners.stream().map(CookieBannerMapper::toDto).toList();
    }

    @Override
    public void updateCookieBanner(String token, CookieBannerRequest banner, Long id) throws NoSuchFieldException {
        String username = getUserName(token);
        User user = userRepository.findByUsername(username).orElseThrow();

        if (cookieBannerRepository.findById(id).isEmpty()) throw new NoSuchFieldException("Banner no encontrado");

        CookieBanner cookieBannerToUpdate = CookieBannerMapper.toEntity(banner);
        cookieBannerToUpdate.setId(id);

        user.add(cookieBannerToUpdate);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUser(String token) {
        return UserMapper.toDTO(userRepository.findByUsername(getUserName(token)).orElseThrow());
    }

    @Override
    public CookieBannerResponse getCookieBannerDetail(String token, Long id) {
        //1) Obtener el nombre del usuario
        String username = getUserName(token);

        CookieBanner banner;
        try {
            //2.1) Buscar el banner por el ID del banner y por el nombre del usuario
            //2.2 Hay que crear un método en el CookieBannerRepository para obtener esta información
            banner = cookieBannerRepository.findCookieBannerByIdAndUserName(username, id);
            if (banner == null) throw new NoSuchElementException("Elemento no encontrado");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(e);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        //3 Convertir el CookieBanner a CookieBannerRespose
        // y Devolver la info.
        return CookieBannerMapper.toDto(banner);
    }

    private String getUserName(String token) {
        return jwtService.getUsernameFromToken(token);
    }
}
