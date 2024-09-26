package com.eso.socialmediaserver.post.controller;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.client.service.ClientService;
import com.eso.socialmediaserver.post.dto.request.PostRequest;
import com.eso.socialmediaserver.post.dto.response.PostResponse;
import com.eso.socialmediaserver.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final ClientService clientService;

    @PostMapping
    public PostResponse createPost(@RequestBody PostRequest postRequest, Authentication authentication) {
        Client client = clientService.getClient(authentication);
        return postService.createPost(postRequest, client);
    }

    @PostMapping("/{postId}/like")
    public void handlePostLike(@PathVariable Long postId, Authentication authentication) {
        Client client = clientService.getClient(authentication);
        postService.handlePostLike(postId, client);
    }
}
