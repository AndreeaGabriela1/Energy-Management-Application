package com.application.user.services;


import com.application.user.dtos.UserDto;
import com.application.user.dtos.request.LoginRequest;
import com.application.user.dtos.request.RegisterRequest;
import com.application.user.dtos.request.UserRequest;
import com.application.user.dtos.response.AuthentificationResponse;
import com.application.user.dtos.response.UserResponse;
import com.application.user.entities.Role;
import com.application.user.entities.Role_enum;
import com.application.user.entities.User;
import com.application.user.repositories.RoleRepository;
import com.application.user.repositories.UserRepository;
import com.application.user.security.JwtUtils;
import com.application.user.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    UserRepository iUserRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository iRoleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @Transactional
    @Override
    public ResponseEntity<AuthentificationResponse> login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = iUserRepository.findByEmail(userDetails.getEmail()).orElse(null);
        UserDto userDTO = new UserDto(user);
        String role = user.getRole().toString();
        System.out.println(role);
        Pattern pattern = Pattern.compile("role=(\\w+)");
        Matcher matcher = pattern.matcher(role);
        String extractedRole = null;
        if (matcher.find()) {
            extractedRole = matcher.group(1);
            System.out.println(extractedRole);
        } else {
            System.out.println("No match found");
        }
        String jwt = jwtUtils.generateJwtToken(authentication, extractedRole);
        return ResponseEntity.ok(new AuthentificationResponse(userDTO, jwt));

    }

    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (iUserRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // Create new user's account
        User user = new User();
        Role userRole = iRoleRepository.findByRole(Role_enum.CLIENT).orElse(null);

        if (userRole == null)
        {
            Role tempRole = new Role();
            tempRole.setRole(Role_enum.CLIENT);
            userRole = iRoleRepository.save(tempRole);
        }

        //Set<Role> roles = new HashSet<>();
        //roles.add(userRole);
        user.setRole(userRole);
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        ///user.setPassword(registerRequest.getPassword());
        user.setPassword(encoder.encode(registerRequest.getPassword()));

        user = iUserRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), registerRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication, Role_enum.CLIENT.toString());

        return ResponseEntity.ok(new AuthentificationResponse(new UserDto(user), jwt));
    }

    @Override
    public ResponseEntity<?> registerAsAdmin(RegisterRequest registerRequest) {
        if (iUserRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // Create new user's account
        User user = new User();
        Role userRole = iRoleRepository.findByRole(Role_enum.ADMIN).orElse(null);

        if (userRole == null) {
            Role tempRole = new Role();
            tempRole.setRole(Role_enum.ADMIN);
            userRole = iRoleRepository.save(tempRole);
        }

        //Set<Role> roles = new HashSet<>();
        //roles.add(userRole);
        user.setRole(userRole);
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));

        user = iUserRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), registerRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication, Role_enum.ADMIN.toString());

        return ResponseEntity.ok(new AuthentificationResponse(new UserDto(user), jwt));
    }

    //@Secured("ADMIN")
    @Override
    public ResponseEntity<?> showAllUsers(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            // Handle the case where the user is not authenticated
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required.");
        }
        List<User> list = iUserRepository.findAllClients();
        System.out.println("Lista cu useri: " + list);
        List<UserResponse> userResponses = new ArrayList<UserResponse>();
        for (User m : list)
        {
            UserResponse userResponse = new UserResponse();
            userResponse.setName(m.getName());
            userResponse.setEmail(m.getEmail());
            userResponse.setRoles(m.getRole().toString());

            userResponses.add(userResponse);
        }
        return ResponseEntity.ok(userResponses);
    }

    private boolean userHasAdminRole(Authentication authentication) {
        String email = authentication.getName();
        User user = iUserRepository.findByEmail(email).orElse(null);
        if(user != null) {
            //Role role = user.getRole();
            //Set<Role> roles = user.getRole();
            //boolean hasAdminRole = role.stream()
            //        .anyMatch(role -> role.getRole() == Role_enum.ADMIN);
            //return hasAdminRole;
            return user.getRole().getRole() == Role_enum.ADMIN;
        }
        return false;
    }

    @Override
    public ResponseEntity<?> modifyUser(Authentication authentication, UserRequest user)
    {
        if (!userHasAdminRole(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        String userEmailToChange = user.getEmail();
        User userToUpdate = iUserRepository.findByEmail(userEmailToChange).orElse(null);

        userToUpdate.setName(user.getName());
        userToUpdate.setPassword(user.getPassword());
        iUserRepository.save(userToUpdate);

        System.out.println("User modified!");
        return ResponseEntity.ok(userToUpdate);
    }

    public ResponseEntity<?> deleteUser(Authentication authentication, String userEmail)
    {
        if (!userHasAdminRole(authentication))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        iUserRepository.deleteByEmail(userEmail);
        return ResponseEntity.ok(Collections.singletonMap("message", "User " + userEmail + " deleted successfully"));
    }
}
