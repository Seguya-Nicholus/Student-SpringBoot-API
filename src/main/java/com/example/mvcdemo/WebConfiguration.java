package com.example.mvcdemo;

import com.example.mvcdemo.interceptors.GeneralPurposeInterceptor;
import com.example.mvcdemo.interceptors.SinglePurposeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GeneralPurposeInterceptor());
        registry.addInterceptor(new SinglePurposeInterceptor()).addPathPatterns("/api/v1/student/**");
    }
}
