package com.eso.socialmediaserver.post.mapper;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.file.entity.File;
import com.eso.socialmediaserver.file.mapper.FileMapper;
import com.eso.socialmediaserver.post.dto.request.PostRequest;
import com.eso.socialmediaserver.post.dto.response.PostResponse;
import com.eso.socialmediaserver.post.entity.Post;

import java.util.ArrayList;

public class PostMapper {

    private PostMapper() {

    }

    public static Post toEntity(PostRequest postRequest, File image, Client client) {
        Post post = new Post();
        post.setClient(client);
        post.setImage(image);
        post.setContent(postRequest.getContent() != null ? postRequest.getContent() : "");
        post.setLocation(postRequest.getLocation());
        return post;
    }

    public static PostResponse toResponse(Post post, String filePath, String fileUrl) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .image(FileMapper.toResponse(post.getImage(), filePath, fileUrl))
                .location(post.getLocation())
                .comments(post.getComments() != null ? post.getComments().stream()
                        .map(comment -> CommentMapper.toResponse(comment, filePath, fileUrl))
                        .toList() : new ArrayList<>())
                .likes(post.getLikes() != null ? post.getLikes().stream()
                        .map(like -> LikeMapper.toResponse(like, filePath, fileUrl))
                        .toList() : null)
                .build();
    }
}
