package com.project.netsentinel.agent.service.planner;

import com.project.netsentinel.agent.model.DiagnosticPlan;
import com.project.netsentinel.agent.model.DiagnosticTask;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PlannerService {

    public PlannerService(@Qualifier("plannerClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    private final ChatClient chatClient;

    public DiagnosticPlan createPlan(DiagnosticTask task) {
        var outputConverter = new BeanOutputConverter<>(DiagnosticPlan.class);

        String jsonPlan = chatClient.prompt()
                .user(task.task() + "\n" + outputConverter.getFormat())
                .call()
                .content();

        return outputConverter.convert(Objects.requireNonNull(jsonPlan));
    }
}
