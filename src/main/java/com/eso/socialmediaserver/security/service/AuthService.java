package com.eso.socialmediaserver.security.service;

import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import com.eso.socialmediaserver.security.dto.UserDetailsImpl;
import com.eso.socialmediaserver.security.dto.request.LoginRequest;
import com.eso.socialmediaserver.security.dto.request.RefreshTokenRequest;
import com.eso.socialmediaserver.security.dto.request.RegisterRequest;
import com.eso.socialmediaserver.security.dto.response.LoginResponse;
import com.eso.socialmediaserver.security.dto.response.RefreshTokenResponse;
import com.eso.socialmediaserver.security.entity.RefreshToken;
import com.eso.socialmediaserver.user.entity.User;
import com.eso.socialmediaserver.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void register(RegisterRequest registerRequest) {
        boolean isPasswordMatch = isPasswordMatch(registerRequest.getPassword(), registerRequest.getConfirmPassword());
        if (!isPasswordMatch) {
            throw new BusinessException(ErrorCode.password_mismatch, "Passwords mismatch");
        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        // todo mail verification with otp
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        String accessToken = accessTokenService.generateAccessToken(authentication);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .id(userDetails.getId())
                .email(userDetails.getUsername())
                .build();
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String requestRefreshToken = refreshTokenRequest.getRefreshToken();
        String accessToken = refreshTokenService.refreshToken(requestRefreshToken);
        return RefreshTokenResponse.builder()
                .refreshToken(requestRefreshToken)
                .accessToken(accessToken)
                .build();
    }

    private boolean isPasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
