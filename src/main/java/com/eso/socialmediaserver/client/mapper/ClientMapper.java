package com.eso.socialmediaserver.client.mapper;

import com.eso.socialmediaserver.client.dto.response.ClientResponse;
import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.file.mapper.FileMapper;

public class ClientMapper {

    private ClientMapper() {
    }

    public static ClientResponse toResponse(Client client, String filePath, String fileUrl) {
        return ClientResponse.builder()
                .username(client.getUsername())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .bio(client.getBio() != null ? client.getBio() : "")
                .avatar(client.getAvatar() != null ? FileMapper.toResponse(client.getAvatar(), filePath, fileUrl) : null)
                .build();
    }
}
