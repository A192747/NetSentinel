package com.project.netsentinel.agent.model;

public record DiagnosticStep(
        String id,
        String action,
        String target,
        String reasoning // Почему это важно
) {}
