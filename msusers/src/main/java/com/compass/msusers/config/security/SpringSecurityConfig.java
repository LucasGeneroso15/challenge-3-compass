package com.compass.msusers.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Locale;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPointHandler())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        Locale.setDefault(Locale.US);
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");

            String jsonException = String.format(
                    "{\"path\": \"%s\", \"method\": \"%s\", \"status\": %d, \"statusText\": \"%s\", \"message\": \"%s\"}",
                    request.getRequestURI(),
                    request.getMethod(),
                    HttpStatus.FORBIDDEN.value(),
                    HttpStatus.FORBIDDEN.getReasonPhrase(),
                    accessDeniedException.getMessage()
            );


            response.getWriter().write(jsonException);
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPointHandler() {
        Locale.setDefault(Locale.US);
        return (request, response, authenticationEntryPointException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            String jsonException = String.format(
                    "{\"path\": \"%s\", \"method\": \"%s\", \"status\": %d, \"statusText\": \"%s\", \"message\": \"%s\"}",
                    request.getRequestURI(),
                    request.getMethod(),
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    authenticationEntryPointException.getMessage()
            );


            response.getWriter().write(jsonException);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
