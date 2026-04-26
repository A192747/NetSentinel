package com.project.netsentinel.core.config.chatClients;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient ollamaAiChatClient(OllamaChatModel chatModel) {
        return ChatClient.create(chatModel);
    }

}
