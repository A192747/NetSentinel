package com.project.netsentinel.tools.network;

import com.project.netsentinel.tools.network.model.DnsRequest;
import com.project.netsentinel.tools.network.model.DnsResponse;
import com.project.netsentinel.tools.network.model.PingRequest;
import com.project.netsentinel.tools.network.model.PingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

@Slf4j
@Component
public class NetworkTools {

    @Tool(description = "Проверить доступность узла в сети (ping)")
    public PingResponse executePing(PingRequest request) {
        // 1. Сначала пробуем нативный Java check
        try {
            if (InetAddress.getByName(request.host()).isReachable(2000)) {
                return new PingResponse("Reachable via native ICMP/TCP-7", true);
            }
        } catch (Exception ignored) {}

        int[] commonPorts = {53, 80, 443};
        for (int port : commonPorts) {
            try (java.net.Socket socket = new java.net.Socket()) {
                socket.connect(new java.net.InetSocketAddress(request.host(), port), 1000);
                return new PingResponse("Reachable via TCP port " + port, true);
            } catch (Exception ignored) {}
        }

        return new PingResponse("All checks failed for " + request.host(), false);
    }

    @Tool(description = "Узнать IP адрес хоста через DNS (nslookup)")
    public DnsResponse resolveDns(DnsRequest request) {
        log.info("Resolving DNS for: {}", request.host());
        try {
            String ip = InetAddress.getByName(request.host()).getHostAddress();
            return new DnsResponse(ip);
        } catch (Exception e) {
            return new DnsResponse("Unknown host");
        }
    }
}