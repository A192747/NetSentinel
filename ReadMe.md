# NetSentinel AI

[English](#english) | [Русский](#русский)


---

### Project Overview
**NetSentinel AI** is an autonomous SRE (Site Reliability Engineering) agent designed for local infrastructure diagnostics and monitoring. Unlike traditional scripts, it uses a **Reasoning Loop** to analyze network issues, choose appropriate diagnostic tools, and provide human-readable incident reports.


#### 1. Agentic Foundation
* **Spring AI 2.0 Integration:** Leverages the latest `ChatClient` API for declarative agent orchestration.
* **Local LLM Inference:** Powered by **Ollama** (utilizing `qwen3.5` / `llama3.2`) to ensure data privacy within the local network.
* **Autonomous Tool Calling:** Implemented a non-deterministic loop where the AI decides when to trigger system tools based on the conversation context.

#### 2. Network Toolset (Local Capabilities)
The agent interacts with the OS layer through:
* **ICMP Diagnostics:** Native Java and system-level ping verification.
* **DNS Resolution:** Identifying host IP addresses and verifying record availability.
* **Error Self-Healing:** The agent detects OS-level errors and suggests alternative diagnostic paths.

#### 3. MCP Integration & Extended Capabilities
* **Model Context Protocol (MCP) Support:** Integrated with **Docker MCP Gateway** to provide the agent with professional-grade external tools (e.g., DuckDuckGo Search).
* **Remote Tool Execution:** Tools are hosted in isolated Docker containers and accessed via SSE (Streaming) transport.
* **Tool Filtering & Security:** Implemented a whitelist mechanism in `application.yml` to strictly control which external tools are exposed to the LLM.

### Quick Start: Launching MCP Gateway
To provide the agent with external capabilities, start the gateway using:
```bash
docker mcp gateway run --profile dev_tools --port 8811 --transport streaming
```
---



---

**NetSentinel AI** — это автономный SRE-агент (Site Reliability Engineering) для диагностики и мониторинга локальной инфраструктуры. В отличие от обычных скриптов, он использует **цикл рассуждения (Reasoning Loop)** для анализа сетевых проблем, самостоятельного выбора инструментов и составления отчетов об инцидентах.


#### 1. Агентный фундамент
* **Интеграция Spring AI 2.0:** Использование актуального `ChatClient` API для декларативной оркестрации агента.
* **Локальный инференс LLM:** Работа через **Ollama** (модели `qwen3.5` / `llama3.2`) для обеспечения приватности данных.
* **Автономный Tool Calling:** ИИ сам решает, когда вызвать системный инструмент на основе контекста задачи.

#### 2. Сетевые инструменты (Локальные возможности)
* **ICMP Диагностика:** Проверка доступности узлов (Ping) через нативные средства Java и системные вызовы.
* **DNS Резолвинг:** Определение IP-адресов хостов и проверка доступности записей.
* **Self-Healing (Самодиагностика):** Агент распознает ошибки окружения и предлагает альтернативные пути проверки.

#### 3. Интеграция с MCP и расширенные возможности
* **Поддержка Model Context Protocol (MCP):** Интеграция с **Docker MCP Gateway** для использования внешних инструментов (например, поиск DuckDuckGo).
* **Удаленное выполнение инструментов:** Инструменты запускаются в изолированных Docker-контейнерах через SSE (Streaming) транспорт.
* **Фильтрация и безопасность:** Механизм «белого списка» в `application.yml` для ограничения доступа модели к внешним инструментам.

### Быстрый старт: Запуск MCP Gateway
Для обеспечения агента внешними инструментами, запустите шлюз командой:
```bash
docker mcp gateway run --profile dev_tools --port 8811 --transport streaming
```
---

### Tech Stack / Стек технологий
* **Language:** Java 21
* **Framework:** Spring Boot 3.4+, Spring AI 1.1.5
* **AI Runner:** Ollama
* **Observability:** Micrometer / SimpleLoggerAdvisor