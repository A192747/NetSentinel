package com.project.netsentinel.core.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args)  {
        System.out.println("--------------------------------------------------");
        System.out.println("Приложение успешно запущено! Информация при старте.");
        System.out.println("--------------------------------------------------");
    }
}
