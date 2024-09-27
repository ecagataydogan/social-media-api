package com.eso.socialmediaserver.follower.controller;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.client.service.ClientService;
import com.eso.socialmediaserver.follower.dto.request.AcceptFollowRequest;
import com.eso.socialmediaserver.follower.dto.request.FollowRequest;
import com.eso.socialmediaserver.follower.dto.request.RejectFollowRequest;
import com.eso.socialmediaserver.follower.dto.request.UnfollowRequest;
import com.eso.socialmediaserver.follower.dto.response.FollowRequestEntityResponse;
import com.eso.socialmediaserver.follower.dto.response.FollowerResponse;
import com.eso.socialmediaserver.follower.service.FollowersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/followers")
@AllArgsConstructor
public class FollowersController {

    private final FollowersService followersService;
    private final ClientService clientService;

    @GetMapping
    public List<FollowerResponse> getFollowers(Authentication authentication) {
        Client client = clientService.getClient(authentication);
        return followersService.getFollowers(client);
    }


    @PostMapping("/follow-request")
    public void createFollowRequestEntity(@Valid @RequestBody FollowRequest followRequest, Authentication authentication) {
        Client client = clientService.getClient(authentication);
        followersService.createFollowRequest(followRequest, client);
    }

    @PostMapping("/cancel-request")
    public void cancelFollowRequestEntity(@Valid @RequestBody UnfollowRequest unfollowRequest, Authentication authentication) {
        Client client = clientService.getClient(authentication);
        followersService.cancelFollowRequestEntity(unfollowRequest, client);
    }

    @GetMapping("/follow-request")
    public List<FollowRequestEntityResponse> getFollowRequests(Authentication authentication) {
        Client client = clientService.getClient(authentication);
        return followersService.getFollowRequests(client);
    }

    @PostMapping("/follow-request/accept")
    public void acceptFollowRequest(@Valid @RequestBody AcceptFollowRequest acceptFollowRequest, Authentication authentication) {
        Client client = clientService.getClient(authentication);
        followersService.acceptFollowRequest(acceptFollowRequest, client);
    }

    @PostMapping("/follow-request/reject")
    public void rejectFollowRequest(@Valid @RequestBody RejectFollowRequest rejectFollowRequest, Authentication authentication) {
        Client client = clientService.getClient(authentication);
        followersService.rejectFollowRequest(rejectFollowRequest, client);
    }
}
