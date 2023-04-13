package com.ssh1y.paperrec.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenweihong
 */
@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {

    /**
     * 跨域配置
     *
     * @param registry registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTION")
                .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
