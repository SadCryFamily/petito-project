package com.petito.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import(InternationalizationConfiguration.class)
public class MvcConfiguration implements WebMvcConfigurer
{
    @Autowired
    private InternationalizationConfiguration configuration;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(configuration.localeChangeInterceptor());
    }
}
