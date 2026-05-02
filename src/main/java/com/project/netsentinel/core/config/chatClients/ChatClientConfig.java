package com.project.netsentinel.core.config.chatClients;

import com.project.netsentinel.tools.network.NetworkTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class ChatClientConfig {

    @Value("${net-sentinel.agent.prompt:}")
    private String mainPrompt;

    @Bean
    public ChatClient sREChatClient(ChatClient.Builder builder,
                                    NetworkTools networkTools,
                                    ChatMemory chatMemory,
                                    List<ToolCallback> filteredTools) {

        log.info("Initializing sREChatClient with following components:");
        log.info("System Prompt: {}", mainPrompt.substring(0, Math.min(mainPrompt.length(), 100)) + "...");
        log.info("Local NetworkTools found: {}", networkTools != null);
        log.info("ChatMemory implementation: {}", chatMemory.getClass().getSimpleName());
        log.info("Number of MCP/Filtered Tools: {}", filteredTools.size());
        filteredTools.forEach(tool ->
                log.info(" - Tool available: {} ({})", tool.getToolDefinition().name(), tool.getToolDefinition().description())
        );

        return builder
                .defaultSystem(mainPrompt)
                // Добавляем локальные инструменты
                .defaultTools(networkTools)
                // Добавляем инструменты из всех подключенных MCP серверов
                .defaultToolCallbacks(filteredTools)
                .defaultAdvisors(
                        // Настройка памяти
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        // Логирование в консоль (Thought/Action/Observation)
                        new SimpleLoggerAdvisor()
                )
                .build();
    }
}
