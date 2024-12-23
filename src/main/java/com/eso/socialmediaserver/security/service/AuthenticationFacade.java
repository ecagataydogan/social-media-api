package com.eso.socialmediaserver.security.service;

import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import com.eso.socialmediaserver.security.util.UserValidationUtil;
import com.eso.socialmediaserver.user.entity.User;
import com.eso.socialmediaserver.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationFacade {

    private final UserRepository userRepository;

    public User getUserThroughAuthentication(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();

        if (!UserValidationUtil.isValidEmail(email)) {
            throw new BusinessException(ErrorCode.validation, "Email is not valid: " + email);
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "User not found with email: " + email));
    }
}
