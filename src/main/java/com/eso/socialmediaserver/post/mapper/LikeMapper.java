package com.eso.socialmediaserver.post.mapper;

import com.eso.socialmediaserver.client.mapper.ClientMapper;
import com.eso.socialmediaserver.post.dto.response.LikeResponse;
import com.eso.socialmediaserver.post.entity.Like;

public class LikeMapper {

    private LikeMapper() {

    }

    public static LikeResponse toResponse(Like like, String filePath, String fileUrl) {
        return LikeResponse.builder()
                .id(like.getId())
                .client(ClientMapper.toResponse(like.getClient(), filePath, fileUrl))
                .build();
    }
}
