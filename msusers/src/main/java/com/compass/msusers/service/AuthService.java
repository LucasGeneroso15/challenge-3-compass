package com.compass.msusers.service;

import com.compass.msusers.entity.User;
import com.compass.msusers.entity.util.JwtUserDetails;
import com.compass.msusers.exceptions.UsernameNotFoundException;
import com.compass.msusers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u1 = userRepository.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
        return new JwtUserDetails(u1);
    }
}
