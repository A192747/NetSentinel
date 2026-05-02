package com.project.netsentinel.mcp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class McpWebClientConfig {

    @Value("${spring.ai.mcp.client.streamable-http.connections.docker-gateway.url:}")
    private String gatewayUrl;

    @Value("${spring.ai.mcp.client.headers.Authorization:}")
    private String authHeader;

    @Bean
    @Primary
    public WebClient.Builder mcpAuthorizedWebClientBuilder() {
        log.info("Configuring MCP WebClient: url={}, authHeader={}",
                gatewayUrl,
                authHeader != null && !authHeader.isEmpty() ? "***REDACTED***" : "NOT SET");

        return WebClient.builder()
                .baseUrl(gatewayUrl)
                .filter((request, next) -> {
                    // Если токен задан — создаём новый запрос с заголовком Authorization
                    if (authHeader != null && !authHeader.isEmpty()) {
                        ClientRequest authorizedRequest = ClientRequest.from(request)
                                .header("Authorization", authHeader)
                                .build();
                        log.debug("Added Authorization header to request to {}", request.url());
                        return next.exchange(authorizedRequest);
                    }
                    // Иначе передаём запрос как есть
                    return next.exchange(request);
                });
    }
}