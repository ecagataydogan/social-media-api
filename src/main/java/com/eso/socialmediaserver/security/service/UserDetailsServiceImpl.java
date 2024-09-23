package com.eso.socialmediaserver.security.service;

import com.eso.socialmediaserver.security.dto.UserDetailsImpl;
import com.eso.socialmediaserver.security.util.UserValidationUtil;
import com.eso.socialmediaserver.user.entity.User;
import com.eso.socialmediaserver.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;
        if (!UserValidationUtil.isValidEmail(email)) {
            throw new RuntimeException("Invalid email "); // todo custom exception
        }
        user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new UserDetailsImpl(user);
    }
}
