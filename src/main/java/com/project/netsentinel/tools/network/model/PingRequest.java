package com.project.netsentinel.tools.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record PingRequest(
        @JsonProperty(required = true)
        @JsonPropertyDescription("IP адрес или хостнейм для проверки (например, 8.8.8.8)")
        String host
) {}
