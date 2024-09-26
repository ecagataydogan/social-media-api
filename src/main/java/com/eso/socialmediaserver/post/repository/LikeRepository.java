package com.eso.socialmediaserver.post.repository;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.post.entity.Comment;
import com.eso.socialmediaserver.post.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByCommentAndClient(Comment comment, Client client);

}
