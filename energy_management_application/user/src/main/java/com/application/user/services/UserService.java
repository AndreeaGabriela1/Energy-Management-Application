package com.application.user.services;

import com.application.user.dtos.request.LoginRequest;
import com.application.user.dtos.request.RegisterRequest;
import com.application.user.dtos.request.UserRequest;
import com.application.user.dtos.response.AuthentificationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


public interface UserService
    {
        ResponseEntity<AuthentificationResponse> login(LoginRequest loginRequest);

        ResponseEntity<?> register(RegisterRequest registerRequest);
        ResponseEntity<?> registerAsAdmin(RegisterRequest registerRequest);
        ResponseEntity<?> showAllUsers(Authentication authentication);
        ResponseEntity<?> modifyUser(Authentication authentication, UserRequest user);
        ResponseEntity<?> deleteUser(Authentication authentication, String username);
    }
