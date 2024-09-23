package com.eso.socialmediaserver.file.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@Getter
@Setter
public class CdnConfig {

    @Value("${cdn.upload-path}")
    private String uploadPath;

    @Value("${cdn.host}")
    private String host;
}
