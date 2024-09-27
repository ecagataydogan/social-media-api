package com.eso.socialmediaserver.follower.repository;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.follower.entity.FollowRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRequestEntityRepository extends JpaRepository<FollowRequestEntity, Long> {

    FollowRequestEntity findByLeaderAndFollower(Client leader, Client follower);

    List<FollowRequestEntity> findByLeaderAndIsAcceptedFalse(Client leader);

    FollowRequestEntity findByLeaderAndFollowerAndIsAcceptedFalse(Client leader, Client follower);
}
