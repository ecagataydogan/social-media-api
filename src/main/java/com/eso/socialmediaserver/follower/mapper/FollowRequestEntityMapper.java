package com.eso.socialmediaserver.follower.mapper;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.client.mapper.ClientMapper;
import com.eso.socialmediaserver.follower.dto.response.FollowRequestEntityResponse;
import com.eso.socialmediaserver.follower.entity.FollowRequestEntity;

public class FollowRequestEntityMapper {

    private FollowRequestEntityMapper() {

    }

    public static FollowRequestEntity toEntity(Client leader, Client follower) {
        FollowRequestEntity followRequestEntity = new FollowRequestEntity();
        followRequestEntity.setFollower(follower);
        followRequestEntity.setLeader(leader);
        followRequestEntity.setAccepted(false);
        return followRequestEntity;
    }

    public static FollowRequestEntityResponse toResponse(FollowRequestEntity followRequestEntity, String filePath, String fileUrl) {
        return FollowRequestEntityResponse.builder()
                .id(followRequestEntity.getId())
                .follower(ClientMapper.toResponse(followRequestEntity.getFollower(), filePath, fileUrl))
                .build();
    }
}
