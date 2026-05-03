package com.project.netsentinel.agent.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class AgentState {
    private final UUID id;
    private final DiagnosticPlan plan;
    private final LocalDateTime createdAt;

    private int currentStepIndex = 0;
    private Status status;
    private List<String> results = new ArrayList<>(); // Инициализируем сразу

    public AgentState(UUID id, DiagnosticPlan plan) {
        this.id = id;
        this.plan = plan;
        this.createdAt = LocalDateTime.now();
        this.status = Status.PENDING;
    }

    public enum Status {
        PENDING, APPROVED, RUNNING, COMPLETED, FAILED
    }

    public boolean hasMoreSteps() {
        return plan != null && currentStepIndex < plan.steps().size();
    }

    public DiagnosticStep getCurrentStep() {
        return plan.steps().get(currentStepIndex);
    }

    public void addResult(String result) {
        this.results.add(result);
        this.currentStepIndex++;
    }
}
