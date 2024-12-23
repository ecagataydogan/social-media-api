package com.eso.socialmediaserver.security.service;

import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import com.eso.socialmediaserver.security.config.JwtConfig;
import com.eso.socialmediaserver.security.entity.RefreshToken;
import com.eso.socialmediaserver.security.repository.RefreshTokenRepository;
import com.eso.socialmediaserver.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenService accessTokenService;
    private final JwtConfig jwtConfig;
    private final UserService userService;

    public String refreshToken(String token) {
        return findByToken(token)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> accessTokenService.generateToken(user.getEmail()))
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Refresh token does not exist"));
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plus(Duration.ofMillis(jwtConfig.getJwtRefreshExpirationMs())));
        refreshToken.setUser(userService.getUser(userId));
        return refreshTokenRepository.save(refreshToken);
    }

    private Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new BusinessException(ErrorCode.code_expired, "Refresh token expired");
        }
        return token;
    }
}