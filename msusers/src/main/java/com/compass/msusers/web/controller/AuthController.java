package com.compass.msusers.web.controller;

import com.compass.msusers.entity.User;
import com.compass.msusers.entity.util.JwtUserDetails;
import com.compass.msusers.repository.UserRepository;
import com.compass.msusers.service.TokenService;
import com.compass.msusers.service.UserService;
import com.compass.msusers.web.dto.AuthenticationDTO;
import com.compass.msusers.web.dto.signinDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody @Valid AuthenticationDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((JwtUserDetails) auth.getPrincipal());

        return ResponseEntity.ok(new signinDto(token));
    }
}
