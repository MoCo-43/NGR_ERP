package com.yedam.erp.util;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SignResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name").toLowerCase();
        String basePath = os.contains("win") ? "C:/upload" : "/upload";

        System.out.println("🖋 [SignResourceConfig] basePath = " + basePath);

        registry.addResourceHandler("/sign/**")
                .addResourceLocations("file:" + basePath + "/sign/");
    }
}
