package com.project.netsentinel.agent.service.enrichers;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class EnricherRegistry {

    private final Map<Enrichers, TaskEnricher> map = new EnumMap<>(Enrichers.class);

    public EnricherRegistry(List<TaskEnricher> enrichers) {
        for (TaskEnricher enricher : enrichers) {
            map.put(enricher.getType(), enricher);
        }
    }

    public TaskEnricher get(Enrichers type) {
        return map.get(type);
    }
}
