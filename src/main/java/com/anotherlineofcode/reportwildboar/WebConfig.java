package com.anotherlineofcode.reportwildboar;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/css/**",
                "/js/**",
                "/libraries/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars",
                        "classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/libraries/");
    }
}
