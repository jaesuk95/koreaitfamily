package com.koreait.cs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry){
        Path profileImgUpload = Paths.get("./profile-image");
        Path boardImgUpload = Paths.get("./board-image");
        Path responseImgUpload = Paths.get("./response-image");
        String profileImgPath = profileImgUpload.toFile().getAbsolutePath();
        String boardImgPath = boardImgUpload.toFile().getAbsolutePath();
        String responseImgPath = responseImgUpload.toFile().getAbsolutePath();

        registry.addResourceHandler("/profile-image/**").addResourceLocations("file:/" + profileImgPath + "/");
        registry.addResourceHandler("/board-image/**").addResourceLocations("file:/" + boardImgPath + "/");
        registry.addResourceHandler("/response-image/**").addResourceLocations("file:/" + responseImgPath + "/");
    }
}
