package com.eso.socialmediaserver.post.controller;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.client.service.ClientService;
import com.eso.socialmediaserver.post.dto.request.CommentRequest;
import com.eso.socialmediaserver.post.dto.response.CommentResponse;
import com.eso.socialmediaserver.post.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
@AllArgsConstructor
public class CommentController {

    private final ClientService clientService;
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public CommentResponse createComment(@PathVariable Long postId, CommentRequest commentRequest, Authentication authentication) {
        Client client = clientService.getClient(authentication);
        return commentService.createComment(postId, commentRequest, client);
    }

    @PostMapping("/{commentId}/like")
    public void handleCommentLike(@PathVariable Long commentId, Authentication authentication) {
        Client client = clientService.getClient(authentication);
        commentService.handleCommentLike(commentId, client);
    }
}
