package com.eso.socialmediaserver.file.dto.response;


import lombok.Data;

@Data
public class UploadResponse {

    private String name;
    private String path;
    private String relativePath;
}
