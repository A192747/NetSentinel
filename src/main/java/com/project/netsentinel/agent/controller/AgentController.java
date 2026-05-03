package com.project.netsentinel.agent.controller;

import com.project.netsentinel.agent.service.AgentService;
import com.project.netsentinel.agent.service.enrichers.EnricherRegistry;
import com.project.netsentinel.agent.service.enrichers.Enrichers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;
    private final EnricherRegistry enricherRegistry;

    @PostMapping("/user")
    public String chat(@RequestParam UUID chatId, @RequestParam String query) {
        return agentService.ask(enricherRegistry.get(Enrichers.USER).enrich(query, chatId));
    }

    @PostMapping("/kuma")
    public String chat(@RequestParam String query) {
        return agentService.ask(enricherRegistry.get(Enrichers.KUMA).enrich(query, UUID.randomUUID()));
    }

    @PostMapping("/approve")
    public String approve(@RequestParam UUID chatId) {
        return agentService.approve(chatId);
    }
}