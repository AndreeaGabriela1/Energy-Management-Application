package com.application.user.dtos;


import com.application.user.entities.Role;
import com.application.user.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;

@Setter
@Getter
public class UserDto
{
    private Integer id;
    private String name;
    private String email;
    private String roles;

    public UserDto(User user)
    {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.roles = user.getRole().toString();
                //user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet()).toString();
    }

}
