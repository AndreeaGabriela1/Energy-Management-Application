package com.application.user.dtos.response;


import com.application.user.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthentificationResponse
{
    private UserDto user;
    private String accessToken;
}