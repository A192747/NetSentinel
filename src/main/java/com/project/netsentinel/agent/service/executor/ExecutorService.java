package com.project.netsentinel.agent.service.executor;

import com.project.netsentinel.agent.model.DiagnosticStep;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExecutorService {

    public ExecutorService(@Qualifier("executorClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    private final ChatClient chatClient;

    public String executeStep(DiagnosticStep step) {
        return chatClient.prompt()
                .user(String.format("Задача: %s над %s. Обоснование: %s",
                        step.action(), step.target(), step.reasoning()))
                .call()
                .content();
    }
}
