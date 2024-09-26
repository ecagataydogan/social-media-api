package com.eso.socialmediaserver.post.repository;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByClient(Client client);
}
