package com.eso.socialmediaserver.post.service;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import com.eso.socialmediaserver.file.config.CdnConfig;
import com.eso.socialmediaserver.file.entity.File;
import com.eso.socialmediaserver.file.repository.FileRepository;
import com.eso.socialmediaserver.post.dto.request.PostRequest;
import com.eso.socialmediaserver.post.dto.response.PostResponse;
import com.eso.socialmediaserver.post.entity.Comment;
import com.eso.socialmediaserver.post.entity.Like;
import com.eso.socialmediaserver.post.entity.Post;
import com.eso.socialmediaserver.post.mapper.PostMapper;
import com.eso.socialmediaserver.post.repository.LikeRepository;
import com.eso.socialmediaserver.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FileRepository fileRepository;
    private final LikeRepository likeRepository;
    private CdnConfig cdnConfig;

    public PostResponse createPost(PostRequest postRequest, Client client) {
        File image = fileRepository.findByIdAndClient(postRequest.getImageId(), client)
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Image not found with id: " + postRequest.getImageId()));

        Post post = PostMapper.toEntity(postRequest, image, client);
        return PostMapper.toResponse(post, image, cdnConfig.getUploadPath(), cdnConfig.getHost());
    }

    public void handlePostLike(Long postId, Client client) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Comment not found with id: " + postId));
        likeRepository.findByPostAndClient(post, client)
                .ifPresentOrElse(
                        likeRepository::delete,
                        () -> {
                            Like like = new Like();
                            like.setPost(post);
                            like.setClient(client);
                            likeRepository.save(like);
                        }
                );
    }
}
