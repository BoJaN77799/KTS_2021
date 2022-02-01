package com.app.RestaurantApp.config.web_security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
 
	@Override
    public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**").allowedOrigins("*");
    }

}
