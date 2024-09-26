package com.eso.socialmediaserver.post.service;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import com.eso.socialmediaserver.file.config.CdnConfig;
import com.eso.socialmediaserver.post.dto.request.CommentRequest;
import com.eso.socialmediaserver.post.dto.response.CommentResponse;
import com.eso.socialmediaserver.post.entity.Comment;
import com.eso.socialmediaserver.post.entity.Like;
import com.eso.socialmediaserver.post.entity.Post;
import com.eso.socialmediaserver.post.mapper.CommentMapper;
import com.eso.socialmediaserver.post.repository.CommentRepository;
import com.eso.socialmediaserver.post.repository.LikeRepository;
import com.eso.socialmediaserver.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final CdnConfig cdnConfig;

    public CommentResponse createComment(Long postId, CommentRequest commentRequest, Client client) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Post not found with id: " + postId));

        Comment comment = CommentMapper.toEntity(commentRequest, post, client);
        return CommentMapper.toResponse(commentRepository.save(comment), cdnConfig.getUploadPath(), cdnConfig.getHost());
    }

    public void handleCommentLike(Long commentId, Client client) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Comment not found with id: " + commentId));
        likeRepository.findByCommentAndClient(comment, client)
                .ifPresentOrElse(
                        likeRepository::delete,
                        () -> {
                            Like like = new Like();
                            like.setComment(comment);
                            like.setClient(client);
                            likeRepository.save(like);
                        }
                );
    }
}
