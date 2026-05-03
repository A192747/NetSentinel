package com.project.netsentinel.tools.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ToolMetadataProvider {

    private final List<ToolCallback> filteredTools;

    public String getAvailableToolsMenu() {
        return "Available tools:\n" + filteredTools.stream()
                .map(tool -> String.format("- %s: %s",
                        tool.getToolDefinition().name(),
                        getFirstLineSafe(tool.getToolDefinition().description()))
                )
                .collect(Collectors.joining("\n"));
    }

    private String getFirstLineSafe(String text) {
        if (text == null || text.isEmpty()) return "";
        int idx = text.indexOf('\n');
        return (idx >= 0 ? text.substring(0, idx) : text).trim();
    }
}
