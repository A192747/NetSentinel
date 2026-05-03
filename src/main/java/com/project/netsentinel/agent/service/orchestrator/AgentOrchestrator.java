package com.project.netsentinel.agent.service.orchestrator;

import com.project.netsentinel.agent.model.AgentState;
import com.project.netsentinel.agent.model.DiagnosticPlan;
import com.project.netsentinel.agent.model.DiagnosticStep;
import com.project.netsentinel.agent.model.DiagnosticTask;
import com.project.netsentinel.agent.service.executor.ExecutorService;
import com.project.netsentinel.agent.service.planner.PlannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentOrchestrator {
    private final PlannerService plannerService;
    private final ExecutorService executorService;

    // В идеале в будущем заменить на Redis или БД
    private final Map<UUID, AgentState> sessionCache = new ConcurrentHashMap<>();

    public String runDiagnostic(DiagnosticTask task, boolean autoApprove) {
        log.info("Starting diagnostic for chat: {}", task.chatId());

        // 1. Планирование (PlannerService использует plannerChatClient без тулзов)
        DiagnosticPlan plan = plannerService.createPlan(task);
        AgentState state = new AgentState(task.chatId(), plan);
        sessionCache.put(task.chatId(), state);

        if (!autoApprove) {
            log.info("Waiting for user approval for chat: {}", task.chatId());
            return "План создан. Пожалуйста, подтвердите выполнение следующих шагов:\n" +
                    formatSteps(plan);
        }

        return approveAndExecute(task.chatId());
    }

    /**
     * Метод для вызова из контроллера при получении одобрения от пользователя
     */
    public String approveAndExecute(UUID chatId) {
        AgentState state = sessionCache.get(chatId);
        if (state == null) {
            return "Ошибка: Сессия не найдена.";
        }

        if (state.getStatus() != AgentState.Status.PENDING) {
            return "Ошибка: План уже выполняется или завершен.";
        }

        state.setStatus(AgentState.Status.RUNNING);

        try {
            return executeRemainingSteps(state);
        } catch (Exception e) {
            state.setStatus(AgentState.Status.FAILED);
            log.error("Diagnostic failed for chat {}", chatId, e);
            return "Произошла ошибка при выполнении диагностики: " + e.getMessage();
        }
    }

    private String executeRemainingSteps(AgentState state) {
        while (state.hasMoreSteps()) {
            DiagnosticStep currentStep = state.getCurrentStep();
            log.info("Executing step {}/{} for chat {}",
                    state.getCurrentStepIndex() + 1, state.getPlan().steps().size(), state.getId());

            // 2. Выполнение (ExecutorService использует executorChatClient с тулзами)
            String result = executorService.executeStep(currentStep);
            state.addResult(result);
        }

        state.setStatus(AgentState.Status.COMPLETED);
        return formatFinalReport(state);
    }

    private String formatSteps(DiagnosticPlan plan) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plan.steps().size(); i++) {
            var s = plan.steps().get(i);
            sb.append(i + 1).append(". ").append(s.action())
                    .append(" (").append(s.reasoning()).append(")\n");
        }
        return sb.toString();
    }

    private String formatFinalReport(AgentState state) {
        return "--- Диагностика завершена ---\n" +
                String.join("\n", state.getResults());
    }
}
