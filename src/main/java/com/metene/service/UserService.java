package com.metene.service;

import com.metene.service.dto.UserRequest;
import com.metene.service.dto.UserResponse;

public interface UserService {
    void updateUser(UserRequest userRequest, String token);

    UserResponse getUser(String token);

    void changeSuscriptionPlan(String planeName, String userToken);
}
