package com.project.netsentinel.tools.network;

import com.project.netsentinel.tools.network.model.DnsRequest;
import com.project.netsentinel.tools.network.model.DnsResponse;
import com.project.netsentinel.tools.network.model.PingRequest;
import com.project.netsentinel.tools.network.model.PingResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import java.util.function.Function;

@Configuration
public class NetworkToolsConfig {

    @Bean
    @Description("Проверить доступность узла в сети (ping)")
    public Function<PingRequest, PingResponse> networkPing(NetworkTools tools) {
        return tools::executePing;
    }

    @Bean
    @Description("Узнать IP адрес хоста через DNS (nslookup)")
    public Function<DnsRequest, DnsResponse> dnsLookup(NetworkTools tools) {
        return tools::resolveDns;
    }
}
