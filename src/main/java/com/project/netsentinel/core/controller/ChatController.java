package com.project.netsentinel.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;

    @GetMapping("api/chat")
    public String generation(String userInput) {
        return this.chatClient.prompt()
                .advisors(new SimpleLoggerAdvisor())
                .user(userInput)
                .call()
                .content();
    }

}
