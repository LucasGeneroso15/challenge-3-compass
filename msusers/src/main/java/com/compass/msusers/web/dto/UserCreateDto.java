package com.compass.msusers.web.dto;

import lombok.Data;

@Data
public class UserCreateDto {
    private String username;
    private String password;
    private String email;
    private String cep;
}
