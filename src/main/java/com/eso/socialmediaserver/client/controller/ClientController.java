package com.eso.socialmediaserver.client.controller;

import com.eso.socialmediaserver.client.dto.request.OnboardRequest;
import com.eso.socialmediaserver.client.dto.response.ClientResponse;
import com.eso.socialmediaserver.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ClientResponse getClient(Authentication authentication) {
        return clientService.toClientResponse(authentication);
    }

    @PostMapping("/onboard")
    public ClientResponse onboard(@Valid @RequestBody OnboardRequest onboardRequest, Authentication authentication) {
        return clientService.onboard(onboardRequest,authentication);
    }
}
