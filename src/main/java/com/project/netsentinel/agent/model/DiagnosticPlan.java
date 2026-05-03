package com.project.netsentinel.agent.model;

import java.util.List;

public record DiagnosticPlan(
        List<DiagnosticStep> steps,
        String goal
) {}