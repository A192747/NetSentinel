package com.project.netsentinel.agent.service.enrichers;

import com.project.netsentinel.agent.model.DiagnosticTask;

import java.util.UUID;

public interface TaskEnricher {
    
    default DiagnosticTask enrich(String input, UUID chatId) {
        return new DiagnosticTask(getMainPrompt(), chatId, input);
    }
    Enrichers getType();
    String getMainPrompt();
}
