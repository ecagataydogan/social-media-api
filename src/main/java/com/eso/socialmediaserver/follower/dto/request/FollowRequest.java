package com.eso.socialmediaserver.follower.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FollowRequest {

    @NotNull
    private String username;
}
