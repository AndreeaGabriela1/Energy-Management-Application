package com.application.user.dtos.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest
{
    private String email;
    private String password;
}
