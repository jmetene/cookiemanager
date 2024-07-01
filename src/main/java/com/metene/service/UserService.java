package com.metene.service;

import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.UserRequest;

import java.util.List;

public interface UserService {
    void saveCookies(List<CookieRequest> cookies, String token);
    boolean updateUser(UserRequest userRequest, String token);
}
