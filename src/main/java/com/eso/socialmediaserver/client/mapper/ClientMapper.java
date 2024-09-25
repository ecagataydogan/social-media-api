package com.eso.socialmediaserver.client.mapper;

import com.eso.socialmediaserver.client.dto.request.OnboardRequest;
import com.eso.socialmediaserver.client.dto.response.ClientResponse;
import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.file.entity.File;
import com.eso.socialmediaserver.file.mapper.FileMapper;
import com.eso.socialmediaserver.user.entity.User;
import jakarta.validation.constraints.NotNull;

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

    public static Client toEntity(OnboardRequest onboardRequest, File avatar, User user) {
        Client client = new Client();
        client.setUser(user);
        client.setUsername(onboardRequest.getUsername());
        client.setFirstName(onboardRequest.getFirstName());
        client.setLastName(onboardRequest.getLastName());
        client.setBio(onboardRequest.getBio() != null ? onboardRequest.getBio() : "");
        client.setAvatar(avatar);
        return client;
    }
}
