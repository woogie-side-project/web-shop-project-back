package com.project.webshopproject.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        // RequestFactory 설정
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000); // 5000ms = 5초
        requestFactory.setReadTimeout(5000);    // 5000ms = 5초

        // RestTemplateBuilder를 활용하여 RestTemplate 생성
        return restTemplateBuilder
                .requestFactory(() -> requestFactory) // RequestFactory 설정
                .build();
    }
}
