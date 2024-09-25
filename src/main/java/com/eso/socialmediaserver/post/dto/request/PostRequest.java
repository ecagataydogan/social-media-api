package com.eso.socialmediaserver.post.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequest {

    @NotNull
    private Long imageId;

    private String content;
    private String location;
}
