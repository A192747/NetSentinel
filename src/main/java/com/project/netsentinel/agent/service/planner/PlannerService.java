package com.project.netsentinel.agent.service.planner;

import com.project.netsentinel.agent.model.DiagnosticPlan;
import com.project.netsentinel.agent.model.DiagnosticTask;
import com.project.netsentinel.tools.utils.ToolMetadataProvider;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PlannerService {

    public PlannerService(@Qualifier("plannerClient") ChatClient chatClient, ToolMetadataProvider toolMetadataProvider) {
        this.chatClient = chatClient;
        this.toolMetadataProvider = toolMetadataProvider;
    }

    private final ChatClient chatClient;
    private final ToolMetadataProvider toolMetadataProvider;

    public DiagnosticPlan createPlan(DiagnosticTask task) {
        var outputConverter = new BeanOutputConverter<>(DiagnosticPlan.class);

        String jsonPlan = chatClient.prompt()
                .user(task.task() + "\n" + toolMetadataProvider.getAvailableToolsMenu() + "\n" + outputConverter.getFormat())
                .call()
                .content();

        return outputConverter.convert(Objects.requireNonNull(jsonPlan));
    }
}
