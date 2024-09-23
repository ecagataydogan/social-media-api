package com.eso.socialmediaserver.security.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Long id;
    private String email;
}
