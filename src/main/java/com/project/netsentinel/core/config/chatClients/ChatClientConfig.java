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

    @Value("${net-sentinel.agent.planner.prompt}")
    private String plannerPrompt;

    @Value("${net-sentinel.agent.executor.prompt}")
    private String executorPrompt;

    @Value("${net-sentinel.agent.answerer.prompt}")
    private String answererPrompt;

    @Bean("plannerClient")
    public ChatClient plannerChatClient(ChatClient.Builder builder, NetworkTools networkTools) {
        return builder
                .defaultSystem(plannerPrompt)
                .defaultTools(networkTools)
                .build();
    }

    @Bean("executorClient")
    public ChatClient executorChatClient(ChatClient.Builder builder,
                                         NetworkTools networkTools,
                                         ChatMemory chatMemory,
                                         List<ToolCallback> filteredTools) {

        log.info("Initializing Executor with {} tools", filteredTools.size());

        return builder
                .defaultSystem(executorPrompt)
                .defaultTools(networkTools)
                .defaultToolCallbacks(filteredTools)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();
    }

    @Bean("answererClient")
    public ChatClient answererChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem(answererPrompt)
                .build();
    }
}
