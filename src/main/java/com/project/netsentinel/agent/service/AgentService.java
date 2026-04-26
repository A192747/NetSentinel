package com.project.netsentinel.agent.service;

import com.project.netsentinel.tools.network.NetworkTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AgentService {

    private final ChatClient chatClient;

    public AgentService(ChatClient.Builder builder, NetworkTools networkTools) {
        this.chatClient = builder
                .defaultSystem("Ты — Senior SRE инженер в системе NetSentinel. " +
                        "Твоя задача — диагностировать сеть. Если тебе нужно проверить связь, " +
                        "используй предоставленные инструменты. Отвечай кратко и технически грамотно.")
                // Подключаем функции по именам бинов
                .defaultTools(networkTools)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    public Flux<String> ask(String userQuery) {
        return chatClient.prompt()
                .user(userQuery)
                .stream()
                .content();
    }
}
