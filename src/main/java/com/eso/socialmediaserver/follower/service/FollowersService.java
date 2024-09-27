package com.eso.socialmediaserver.follower.service;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.client.repository.ClientRepository;
import com.eso.socialmediaserver.client.service.ClientService;
import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import com.eso.socialmediaserver.file.config.CdnConfig;
import com.eso.socialmediaserver.follower.dto.request.AcceptFollowRequest;
import com.eso.socialmediaserver.follower.dto.request.FollowRequest;
import com.eso.socialmediaserver.follower.dto.request.RejectFollowRequest;
import com.eso.socialmediaserver.follower.dto.request.UnfollowRequest;
import com.eso.socialmediaserver.follower.dto.response.FollowRequestEntityResponse;
import com.eso.socialmediaserver.follower.dto.response.FollowerResponse;
import com.eso.socialmediaserver.follower.entity.FollowRequestEntity;
import com.eso.socialmediaserver.follower.entity.Follower;
import com.eso.socialmediaserver.follower.mapper.FollowRequestEntityMapper;
import com.eso.socialmediaserver.follower.mapper.FollowerMapper;
import com.eso.socialmediaserver.follower.repository.FollowRequestEntityRepository;
import com.eso.socialmediaserver.follower.repository.FollowerRepository;
import jakarta.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class FollowersService {

    private final FollowRequestEntityRepository followRequestEntityRepository;
    private final ClientRepository clientRepository;
    private final FollowerRepository followerRepository;
    private final CdnConfig cdnConfig;
    private final ClientService clientService;

    public void createFollowRequest(FollowRequest followRequest, Client client) {
        Client leader = clientRepository.findByUsername(followRequest.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Client not found"));

        if (leader.getId() == client.getId()) {
            throw new BusinessException(ErrorCode.conflict, "You cannot follow yourself");
        }

        FollowRequestEntity existFollowRequestEntity = followRequestEntityRepository.findByLeaderAndFollower(leader, client);
        if (existFollowRequestEntity != null) {
            return;
        }

        followRequestEntityRepository.save(FollowRequestEntityMapper.toEntity(leader, client));
    }

    public void cancelFollowRequestEntity(UnfollowRequest unfollowRequest, Client client) {
        Client leader = clientRepository.findByUsername(unfollowRequest.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Client not found"));

        FollowRequestEntity followRequestEntity = followRequestEntityRepository.findByLeaderAndFollower(leader, client);
        if (followRequestEntity != null) {
            followRequestEntityRepository.delete(followRequestEntity);
        }
    }

    public List<FollowRequestEntityResponse> getFollowRequests(Client client) {
        List<FollowRequestEntity> followRequests = followRequestEntityRepository.findByLeaderAndIsAcceptedFalse(client);
        return followRequests.stream().
                map(followRequestEntity -> FollowRequestEntityMapper.toResponse(followRequestEntity, cdnConfig.getUploadPath(), cdnConfig.getHost()))
                .toList();
    }

    public void acceptFollowRequest(AcceptFollowRequest acceptFollowRequest, Client client) {
        Client follower = clientRepository.findByUsername(acceptFollowRequest.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Client not found"));

        FollowRequestEntity followRequestEntity = followRequestEntityRepository.findByLeaderAndFollowerAndIsAcceptedFalse(client, follower);
        if (followRequestEntity != null) {
            followRequestEntity.setAccepted(true);
            followRequestEntityRepository.save(followRequestEntity);
        }
    }

    public void rejectFollowRequest(RejectFollowRequest rejectFollowRequest, Client client) {
        Client follower = clientRepository.findByUsername(rejectFollowRequest.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Client not found"));
        FollowRequestEntity followRequestEntity = followRequestEntityRepository.findByLeaderAndFollowerAndIsAcceptedFalse(client, follower);
        if (followRequestEntity != null) {
            followRequestEntityRepository.delete(followRequestEntity);
        }
    }

    public List<FollowerResponse> getFollowers(Client client) {
        List<Follower> followers = followerRepository.findByLeader(client);
        return followers.stream().
                map(follower -> FollowerMapper.toResponse(follower, cdnConfig.getUploadPath(), cdnConfig.getHost())).toList();

    }
}
