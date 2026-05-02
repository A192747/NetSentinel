package com.project.netsentinel.agent.controller;

import com.project.netsentinel.agent.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @GetMapping("/ask")
    public String chat(@RequestParam String chatId, @RequestParam String query) {
        return agentService.ask(chatId, query);
    }
}