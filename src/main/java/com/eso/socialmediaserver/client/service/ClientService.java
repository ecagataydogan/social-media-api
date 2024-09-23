package com.eso.socialmediaserver.client.service;

import com.eso.socialmediaserver.client.dto.response.ClientResponse;
import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.client.mapper.ClientMapper;
import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import com.eso.socialmediaserver.file.config.CdnConfig;
import com.eso.socialmediaserver.security.service.AuthenticationFacade;
import com.eso.socialmediaserver.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class ClientService {

    private final AuthenticationFacade authenticationFacade;
    private final CdnConfig cdnConfig;

    public Client getClient(Authentication authentication) {
        User user = authenticationFacade.getUserThroughAuthentication(authentication);
        if (user.getClient() ==  null) {
            throw new BusinessException(ErrorCode.validation, "Client not found");
        }
        return user.getClient();
    }

    public ClientResponse toClientResponse(Authentication authentication) {
        Client client = getClient(authentication);
        return ClientMapper.toResponse(client, cdnConfig.getUploadPath(), cdnConfig.getHost());
    }
}
