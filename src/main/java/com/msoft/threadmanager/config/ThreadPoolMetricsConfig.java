package com.msoft.threadmanager.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Configuration;

import com.msoft.threadmanager.util.ThreadPoolManager;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import jakarta.annotation.PostConstruct;

@Configuration
public class ThreadPoolMetricsConfig {

    private final MeterRegistry registry;

    public ThreadPoolMetricsConfig(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void bindMetrics() {
        ThreadPoolManager.getPools().forEach((name, executor) -> {
            registry.gauge("threadpool.active", Tags.of("name", name), executor, ThreadPoolExecutor::getActiveCount);
            registry.gauge("threadpool.queue.size", Tags.of("name", name), executor, e -> e.getQueue().size());
            registry.gauge("threadpool.pool.size", Tags.of("name", name), executor, ThreadPoolExecutor::getPoolSize);
        });
    }
    
}
