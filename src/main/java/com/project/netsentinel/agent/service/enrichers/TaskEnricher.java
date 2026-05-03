package com.project.netsentinel.agent.service.enrichers;

import com.project.netsentinel.agent.model.DiagnosticTask;

import java.util.UUID;

public interface TaskEnricher {
    String mainPrompt = "";
    
    default DiagnosticTask enrich(String input, UUID chatId) {
        return new DiagnosticTask(mainPrompt, chatId, input);
    }
    Enrichers getType();
}
