package com.project.netsentinel.mcp.config;

import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class McpFilteringConfig {

    @Value("${net-sentinel.mcp.allowed-tools:*}")
    private List<String> allowedTools;

    @Bean
    public List<ToolCallback> filteredMcpTools(SyncMcpToolCallbackProvider mcpToolProvider) {
        // Если в конфиге стоит '*', разрешаем всё
        if (allowedTools.contains("*")) return List.of(mcpToolProvider.getToolCallbacks());

        return  Arrays.stream(mcpToolProvider.getToolCallbacks())
                .filter(callback -> allowedTools.contains(callback.getToolDefinition().name()))
                .toList();
    }
}
