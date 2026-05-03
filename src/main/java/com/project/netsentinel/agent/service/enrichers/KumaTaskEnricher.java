package com.project.netsentinel.agent.service.enrichers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KumaTaskEnricher implements TaskEnricher {

    @Value("${net-sentinel.agent.kuma.prompt:}")
    private String mainPrompt;

    @Override
    public Enrichers getType() {
        return Enrichers.KUMA;
    }
}
