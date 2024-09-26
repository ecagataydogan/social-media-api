package com.eso.socialmediaserver.post.dto.response;

import com.eso.socialmediaserver.client.dto.response.ClientResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private Long postId;
    private ClientResponse clientResponse;
    private List<LikeResponse> likes;
}
