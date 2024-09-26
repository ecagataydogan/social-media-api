package com.eso.socialmediaserver.post.dto.response;

import com.eso.socialmediaserver.client.dto.response.ClientResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeResponse {
    private Long id;
    private ClientResponse client;
}
