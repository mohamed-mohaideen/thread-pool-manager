package com.msoft.threadmanager.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.msoft.threadmanager.util.ThreadPoolManager;

import jakarta.annotation.PostConstruct;

@Service
public class TaskSchedulerService {

    private ScheduledExecutorService scheduler;

    @PostConstruct
    public void init() {
        scheduler = Executors.newScheduledThreadPool(1);

        // Schedule task to run every 5 seconds
        scheduler.scheduleAtFixedRate(() -> {
            ThreadPoolManager.createPool("fixed-worker", 2, 4, 50)
                    .execute(() -> System.out.println("Running periodic task on " + Thread.currentThread().getName()));
        }, 0, 5, TimeUnit.SECONDS);
        
//        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
//            var pool = ThreadPoolManager.createPool("scheduled-pool", 2, 4, 100);
//            pool.execute(() -> System.out.println("Scheduled task by " + Thread.currentThread().getName()));
//        }, 0, 5, TimeUnit.SECONDS);
    }
}
