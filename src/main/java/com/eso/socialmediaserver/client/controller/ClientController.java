package com.eso.socialmediaserver.client.controller;

import com.eso.socialmediaserver.client.dto.response.ClientResponse;
import com.eso.socialmediaserver.client.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ClientResponse getClient(Authentication authentication) {
        return clientService.toClientResponse(authentication);
    }
}
