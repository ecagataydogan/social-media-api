package com.eso.socialmediaserver.security.controller;

import com.eso.socialmediaserver.security.dto.request.LoginRequest;
import com.eso.socialmediaserver.security.dto.request.RefreshTokenRequest;
import com.eso.socialmediaserver.security.dto.request.RegisterRequest;
import com.eso.socialmediaserver.security.dto.response.LoginResponse;
import com.eso.socialmediaserver.security.dto.response.RefreshTokenResponse;
import com.eso.socialmediaserver.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

}
