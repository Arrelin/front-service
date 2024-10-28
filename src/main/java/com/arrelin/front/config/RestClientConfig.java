package com.arrelin.front.config;

import com.arrelin.front.client.ProductClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${product.url}")
    private String productServiceUrl;

    @Bean
    public ProductClient productClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(productServiceUrl)
                .build();
        var webClientAdapter = WebClientAdapter.create(webClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return httpServiceProxyFactory.createClient(ProductClient.class);
    }
}