package com.eso.socialmediaserver.post.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {

    @NotNull
    private String content;
}
