package com.compass.msusers.web.controller;

import com.compass.msusers.entity.User;
import com.compass.msusers.entity.util.JwtUserDetails;
import com.compass.msusers.exceptions.UsernameNotFoundException;
import com.compass.msusers.repository.UserRepository;
import com.compass.msusers.service.TokenService;
import com.compass.msusers.service.UserService;
import com.compass.msusers.web.dto.AuthenticationDTO;
import com.compass.msusers.web.dto.UserResponseDto;
import com.compass.msusers.web.dto.signinDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization Users Endpoint")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    @Operation(summary = "Authorization User", description = "Authorization User",
            tags = {"Auth"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = signinDto.class))
                                    )
                            }),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody @Valid AuthenticationDTO dto){

        Optional<User> optional = userService.findUserByUsername(dto.username());
        User optionalUser = optional.get();
        if (!optionalUser.getUsername().equals(dto.username())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", "Unauthorized",
                    "message", "Invalid username or password"
            ));
        }


        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((JwtUserDetails) auth.getPrincipal());

        return ResponseEntity.ok(new signinDto(token));
    }
}
