package com.eso.socialmediaserver.file.mapper;

import com.eso.socialmediaserver.file.dto.response.FileResponse;
import com.eso.socialmediaserver.file.entity.File;

public class FileMapper {

    private FileMapper() {

    }

    public static FileResponse toResponse(File file, String path, String url) {
        return FileResponse.builder()
                .id(file.getId())
                .createdDate(file.getCreatedDate())
                .modifiedDate(file.getModifiedDate())
                .path(file.getPath() != null ? path.concat(file.getPath()) : null)
                .name(file.getName())
                .contentType(file.getContentType())
                .url(file.getUrl() != null ? file.getUrl() : url.concat(file.getPath()))
                .build();
    }
}
