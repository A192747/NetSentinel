package com.project.netsentinel.agent.service;

import com.project.netsentinel.agent.model.DiagnosticTask;
import com.project.netsentinel.agent.service.orchestrator.AgentOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentService {

    @Value("${net-sentinel.agent.auto-approve:false}")
    private Boolean isAutoApprovable;

    private final AgentOrchestrator orchestrator;

    /**
     * Основная точка входа для новых задач (от пользователя или Kuma)
     */
    public String ask(DiagnosticTask task) {
        // Вызываем оркестратор.
        // Вторым параметром передаем false, так как нам нужно одобрение (Human-in-the-loop)
        return orchestrator.runDiagnostic(task, isAutoApprovable);
    }

    /**
     * Метод для подтверждения выполнения плана
     */
    public String approve(UUID chatId) {
        return orchestrator.approveAndExecute(chatId);
    }
}
