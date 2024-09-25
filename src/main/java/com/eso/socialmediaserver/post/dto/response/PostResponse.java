package com.eso.socialmediaserver.post.dto.response;

import com.eso.socialmediaserver.file.dto.response.FileResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostResponse {

    private Long id;
    private String content;
    private FileResponse image;
    private String location;
    private List<CommentResponse> comments;
    private List<LikeResponse> likes;
}
