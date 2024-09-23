package com.eso.socialmediaserver.client.dto.response;

import com.eso.socialmediaserver.file.dto.response.FileResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientResponse {

    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private FileResponse avatar;
}
