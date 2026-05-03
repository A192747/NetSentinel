package com.project.netsentinel.agent.model;

import java.util.UUID;

public record DiagnosticTask (
        String prompt,
        UUID chatId,
        String task
) {}
