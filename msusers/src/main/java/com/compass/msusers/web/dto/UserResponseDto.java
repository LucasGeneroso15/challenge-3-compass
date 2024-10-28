package com.compass.msusers.web.dto;

import com.compass.msusers.entity.util.Address;
import lombok.Data;

@Data
public class UserResponseDto {
    private String username;
    private String email;
    private Address address;
}
