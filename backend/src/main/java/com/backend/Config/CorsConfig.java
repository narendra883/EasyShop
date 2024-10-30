

package com.backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {


    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(new String[]{"https://narendraeasyshop.netlify.app"})
                        .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                        .allowedOrigins(new String[]{"*"});
            }
        };
    }
}

