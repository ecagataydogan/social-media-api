package com.eso.socialmediaserver.follower.dto.response;

import com.eso.socialmediaserver.client.dto.response.ClientResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowerResponse {

    private ClientResponse follower;
}
