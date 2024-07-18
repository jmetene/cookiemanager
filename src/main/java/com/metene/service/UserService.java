package com.metene.service;

import com.metene.service.dto.*;

public interface UserService {
    void updateUser(UserRequest userRequest, String token);
    UserResponse getUser(String token);
}
