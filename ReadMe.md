# NetSentinel AI

[English](#english) | [Русский](#русский)



## English

### Project Overview
**NetSentinel AI** is an autonomous SRE (Site Reliability Engineering) agent designed for local infrastructure diagnostics and monitoring. Unlike traditional scripts, it uses a **Reasoning Loop** to analyze network issues, choose appropriate diagnostic tools, and provide human-readable incident reports.

### Current Progress (v0.1.0)
The foundation of the agentic core and local network toolset has been implemented:

#### 1. Agentic Foundation
* **Spring AI 2.0 Integration:** Leverages the latest `ChatClient` API for declarative agent orchestration.
* **Local LLM Inference:** Powered by **Ollama** (utilizing `qwen3.5` / `llama3.2`) to ensure data privacy within the local network.
* **Autonomous Tool Calling:** Implemented a non-deterministic loop where the AI decides when to trigger system tools based on the conversation context.

#### 2. Network Toolset (Capabilities)
The agent can now interact with the OS layer through:
* **ICMP Diagnostics:** Native Java and system-level ping verification.
* **DNS Resolution:** Identifying host IP addresses and verifying record availability.
* **Error Self-Healing:** The agent detects OS-level errors (e.g., missing binaries in Docker) and suggests alternative diagnostic paths.

### Core Architecture
* `AgentService`: Manages the `ChatClient` and handles the reasoning cycle.
* `NetworkTools`: A collection of Java-based tools annotated with `@Tool` for LLM discovery.
* `SimpleLoggerAdvisor`: Provides full observability into the agent's "thought process" in the logs.

---

## Русский

### Обзор проекта
**NetSentinel AI** — это автономный SRE-агент (Site Reliability Engineering) для диагностики и мониторинга локальной инфраструктуры. В отличие от обычных скриптов, он использует **цикл рассуждения (Reasoning Loop)** для анализа сетевых проблем, самостоятельного выбора инструментов диагностики и составления отчетов об инцидентах.

### Текущий прогресс (v0.1.0)
Реализован фундамент агентного ядра и базовый набор сетевых инструментов:

#### 1. Агентный фундамент
* **Интеграция Spring AI 2.0:** Использование актуального `ChatClient` API для декларативной оркестрации агента.
* **Локальный инференс LLM:** Работа через **Ollama** (модели `qwen3.5` / `llama3.2`) для обеспечения приватности данных внутри сети.
* **Автономный Tool Calling:** Реализован недетерминированный цикл, в котором ИИ сам решает, когда вызвать системный инструмент на основе контекста задачи.

#### 2. Сетевые инструменты (Возможности)
На данный момент агент умеет взаимодействовать с системным уровнем через:
* **ICMP Диагностика:** Проверка доступности узлов (Ping) через нативные средства Java и системные вызовы.
* **DNS Резолвинг:** Определение IP-адресов хостов и проверка доступности DNS-записей.
* **Self-Healing (Самодиагностика):** Агент распознает ошибки ОС (например, отсутствие утилит в Docker-контейнере) и предлагает альтернативные пути проверки.

### Основная архитектура
* `AgentService`: Управляет `ChatClient` и отвечает за цикл рассуждений агента.
* `NetworkTools`: Набор инструментов на Java, помеченных аннотацией `@Tool` для автоматического обнаружения моделью.
* `SimpleLoggerAdvisor`: Обеспечивает полную наблюдаемость (observability) «хода мыслей» агента в логах системы.

---

### Tech Stack / Стек технологий
* **Language:** Java 21
* **Framework:** Spring Boot 3.4+, Spring AI 2.0.0-M4
* **AI Runner:** Ollama
* **Observability:** Micrometer / SimpleLoggerAdvisor