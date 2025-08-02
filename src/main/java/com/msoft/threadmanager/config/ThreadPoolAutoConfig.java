package com.msoft.threadmanager.config;

import org.springframework.context.annotation.Configuration;

import com.msoft.threadmanager.util.ThreadPoolManager;

import jakarta.annotation.PostConstruct;

/**
 * This class to create the thread while running the application.
 * Comment/use profile in the production to disable this bean.
 */
@Configuration
public class ThreadPoolAutoConfig {

    @PostConstruct
    public void init() {
        ThreadPoolManager.createFixedPool("fixed-worker", 5);
        ThreadPoolManager.createScheduledPool("scheduled-worker", 3);
        ThreadPoolManager.createCustomPool("custom-worker", 2, 10, 50);
    }
    
}
