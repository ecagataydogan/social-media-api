package com.eso.socialmediaserver.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/*
 * Bu konfigurasyon normalde yoktu, dosyayı browser'da açmak için ekledim. Bu sayede gelen url doğrudan browser'da görülebiliyor
 * */
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // todo test purposes, it will change when server deploy
        registry.addResourceHandler("/cdn/**")
                .addResourceLocations("file:/Users/ecagataydogan/Desktop/cdn/");
    }
}
