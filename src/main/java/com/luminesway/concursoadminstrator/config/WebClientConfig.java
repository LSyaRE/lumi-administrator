package com.luminesway.concursoadminstrator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    /**
     * Creates and configures a new instance of {@link WebClient}. This method sets up the
     * WebClient with its default builder, allowing for further customization if needed.
     *
     * @return a new instance of {@link WebClient} configured using the default settings.
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
