package com.project.netsentinel.agent.service.reporter;

import com.project.netsentinel.tools.utils.ToolMetadataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporterService {

    // Тот же plannerClient, но с другим системным промптом
    private final ChatClient chatClient;

    public ReporterService(@Qualifier("answererClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String generateFinalReport(String originalGoal, List<String> executionResults) {
        String resultsBlock = String.join("\n---\n", executionResults);

        return chatClient.prompt()
                .user(u -> u.text("""
                    Цель была: {goal}
                    Результаты шагов:
                    {results}
                    
                    Дай структурированный вывод. Если цель не достигнута, спроси пользователя о дальнейших действиях.
                    """)
                        .param("goal", originalGoal)
                        .param("results", resultsBlock))
                .call()
                .content();
    }
}
