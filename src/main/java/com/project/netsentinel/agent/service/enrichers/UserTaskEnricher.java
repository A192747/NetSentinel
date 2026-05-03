package com.project.netsentinel.agent.service.enrichers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserTaskEnricher implements TaskEnricher {

    @Value("${net-sentinel.agent.user.prompt:}")
    private String mainPrompt;

    @Override
    public Enrichers getType() {
        return Enrichers.USER;
    }

    @Override
    public String getMainPrompt() {
        return mainPrompt;
    }
}
