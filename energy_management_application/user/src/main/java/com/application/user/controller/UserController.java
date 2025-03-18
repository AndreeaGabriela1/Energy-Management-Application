package com.application.user.controller;

import com.application.user.dtos.request.LoginRequest;
import com.application.user.dtos.request.RegisterRequest;
import com.application.user.dtos.request.UserRequest;
import com.application.user.dtos.response.AuthentificationResponse;
import com.application.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController
{
    @Autowired
    UserService iUserService;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<AuthentificationResponse> login(@RequestBody LoginRequest loginRequest) {
        return iUserService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return iUserService.register(registerRequest);
    }

    @PostMapping("/registerasadmin")
    public ResponseEntity<?> registerAsAdmin(@RequestBody RegisterRequest registerRequest) {
        return iUserService.registerAsAdmin(registerRequest);
    }

    @GetMapping("/showallusers")
    public ResponseEntity<?> showAllUsers(Authentication accessToken) {
        return iUserService.showAllUsers(accessToken);
    }

    @PutMapping("/modifyuser")
    public ResponseEntity<?> modifyUser(Authentication accessToken, @RequestBody UserRequest user) {
        return iUserService.modifyUser(accessToken, user);
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity<?> deleteUser(Authentication accessToken, @RequestParam(name = "userEmail")String userEmail){
        return iUserService.deleteUser(accessToken, userEmail);
    }
}

