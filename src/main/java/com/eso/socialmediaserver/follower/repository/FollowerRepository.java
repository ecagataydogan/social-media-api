package com.eso.socialmediaserver.follower.repository;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.follower.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follower,Long> {

    List<Follower> findByLeader(Client leader);
}
