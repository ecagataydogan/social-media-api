package com.eso.socialmediaserver.post.service;

import com.eso.socialmediaserver.client.entity.Client;
import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import com.eso.socialmediaserver.file.config.CdnConfig;
import com.eso.socialmediaserver.file.entity.File;
import com.eso.socialmediaserver.file.repository.FileRepository;
import com.eso.socialmediaserver.post.dto.request.PostRequest;
import com.eso.socialmediaserver.post.dto.response.PostResponse;
import com.eso.socialmediaserver.post.entity.Post;
import com.eso.socialmediaserver.post.mapper.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PostService {

    private FileRepository fileRepository;
    private CdnConfig cdnConfig;

    public PostResponse createPost(PostRequest postRequest, Client client) {
        File image = fileRepository.findByIdAndClient(postRequest.getImageId(), client)
                .orElseThrow(() -> new BusinessException(ErrorCode.not_found, "Image not found with id: " + postRequest.getImageId()));

        Post post = PostMapper.toEntity(postRequest, image, client);
        return PostMapper.toResponse(post, image, cdnConfig.getUploadPath(), cdnConfig.getHost());
    }
}
