package com.project.netsentinel.agent.controller;

import com.project.netsentinel.agent.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @GetMapping("/ask")
    public Flux<String> chat(@RequestParam String query) {
        return agentService.ask(query);
    }
}