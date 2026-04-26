package com.project.netsentinel.tools.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DnsRequest(
        @JsonProperty(required = true)
        String host
) {}
