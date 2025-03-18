package com.application.user.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest
{
    private Integer id;
    private String name;
    private String email;
    private String roles;
    private String password;
}
