package com.eso.socialmediaserver.client.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OnboardRequest {

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String bio;
    private Long avatarId;
}
