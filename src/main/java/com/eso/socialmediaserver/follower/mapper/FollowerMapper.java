package com.eso.socialmediaserver.follower.mapper;

import com.eso.socialmediaserver.client.mapper.ClientMapper;
import com.eso.socialmediaserver.follower.dto.response.FollowerResponse;
import com.eso.socialmediaserver.follower.entity.Follower;

public class FollowerMapper {

    private FollowerMapper() {

    }

    public static FollowerResponse toResponse(Follower follower, String filePath, String fileUrl) {
        return FollowerResponse.builder()
                .follower(ClientMapper.toResponse(follower.getFollower(), filePath, fileUrl))
                .build();
    }
}
