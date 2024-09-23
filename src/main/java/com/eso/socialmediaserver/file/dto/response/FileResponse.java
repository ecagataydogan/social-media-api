package com.eso.socialmediaserver.file.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class FileResponse {

    private Long id;
    private ZonedDateTime createdDate;
    private ZonedDateTime modifiedDate;
    private String name;
    private String path;
    private String contentType;
    private String url;
}
