package com.eso.socialmediaserver.client.controller;

import com.eso.socialmediaserver.security.service.AuthenticationFacade;
import com.eso.socialmediaserver.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public void getClient(Authentication authentication) {
        User user = authenticationFacade.getUserThroughAuthentication(authentication);
        System.out.println(user);
    }
}
