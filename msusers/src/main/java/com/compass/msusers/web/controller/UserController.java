package com.compass.msusers.web.controller;

import com.compass.msusers.web.dto.UserCreateDto;
import com.compass.msusers.entity.User;
import com.compass.msusers.entity.util.Address;
import com.compass.msusers.service.UserService;
import com.compass.msusers.web.dto.UserPasswordDto;
import com.compass.msusers.web.dto.UserResponseDto;
import com.compass.msusers.web.dto.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Endpoints")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create User", description = "Create User",
            tags = {"User"},
            responses = {
                    @ApiResponse(description = "Create", responseCode = "201",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class))
                                    )
                            }),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto dto) {
        User user = UserMapper.toUser(dto);
        Address add = userService.getAddressByZipCode(dto.getCep());
        user.setAddress(add);
        User newUser = userService.create(user);
        return new ResponseEntity<>(UserMapper.toDto(newUser), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Password", description = "Update Password",
            tags = {"User"},
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204"),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserPasswordDto dto) {
        userService.editPassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword());
        return ResponseEntity.noContent().build();
    }

}
