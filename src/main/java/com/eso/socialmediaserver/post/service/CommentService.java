package com.eso.socialmediaserver.post.service;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import com.eso.socialmediaserver.file.config.CdnConfig;
import com.eso.socialmediaserver.post.dto.request.CommentRequest;
import com.eso.socialmediaserver.post.dto.response.CommentResponse;
import com.eso.socialmediaserver.post.entity.Comment;
import com.eso.socialmediaserver.post.entity.Post;
import com.eso.socialmediaserver.post.mapper.CommentMapper;
import com.eso.socialmediaserver.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CdnConfig cdnConfig;

    public CommentResponse createComment(Long postId, CommentRequest commentRequest, Client client) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Post not found with id: " + postId));

        Comment comment = CommentMapper.toEntity(commentRequest, post, client);
        return CommentMapper.toResponse(comment, cdnConfig.getUploadPath(), cdnConfig.getHost());
    }
}
