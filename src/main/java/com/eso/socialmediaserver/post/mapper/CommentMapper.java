package com.eso.socialmediaserver.post.mapper;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.client.mapper.ClientMapper;
import com.eso.socialmediaserver.post.dto.request.CommentRequest;
import com.eso.socialmediaserver.post.dto.response.CommentResponse;
import com.eso.socialmediaserver.post.entity.Comment;
import com.eso.socialmediaserver.post.entity.Post;


public class CommentMapper {

    private CommentMapper() {

    }

    public static Comment toEntity(CommentRequest commentRequest, Post post, Client client) {
        Comment comment = new Comment();
        comment.setClient(client);
        comment.setPost(post);
        comment.setContent(commentRequest.getContent());
        return comment;
    }

    public static CommentResponse toResponse(Comment comment, String filePath, String fileUrl) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .clientResponse(ClientMapper.toResponse(comment.getClient(), filePath, fileUrl))
                .likes(comment.getLikes() != null ? comment.getLikes().stream()
                        .map(like -> LikeMapper.toResponse(like, filePath, fileUrl)).toList() : null)
                .build();
    }
}
