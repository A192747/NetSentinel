package com.project.netsentinel.agent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final ChatClient chatClient;

    public String ask(String chatId, String userQuery) {
        return chatClient.prompt()
                .user(userQuery)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .call()
                .content();
    }
}
