package com.msoft.threadmanager.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolManager {

	private static final Map<String, ThreadPoolExecutor> pools = new ConcurrentHashMap<>();

    private ThreadPoolManager() {}
    
    public static ThreadPoolExecutor createPool(String name, int core, int max, int queueCapacity) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                core,
                max,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueCapacity),
                new NamedThreadFactory(name),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        pools.put(name, executor);
        return executor;
    }

    public static ThreadPoolExecutor createFixedPool(String name, int threads) {
        return pools.computeIfAbsent(name, key -> new ThreadPoolExecutor(
                threads, threads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100),
                new NamedThreadFactory(name),
                new ThreadPoolExecutor.CallerRunsPolicy()
        ));
    }

    public static ScheduledThreadPoolExecutor createScheduledPool(String name, int threads) {
        return (ScheduledThreadPoolExecutor) pools.computeIfAbsent(name, key ->
                new ScheduledThreadPoolExecutor(threads, new NamedThreadFactory(name)));
    }

    public static ThreadPoolExecutor createCustomPool(String name, int core, int max, int queueCapacity) {
        return pools.computeIfAbsent(name, key -> new ThreadPoolExecutor(
                core, max,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueCapacity),
                new NamedThreadFactory(name),
                new ThreadPoolExecutor.AbortPolicy()
        ));
    }

    public static void resizePool(String name, int core, int max) {
        ThreadPoolExecutor executor = pools.get(name);
        if (executor != null) {
            executor.setCorePoolSize(core);
            executor.setMaximumPoolSize(max);
        }
    }

    public static void shutdownPool(String name) {
        ThreadPoolExecutor executor = pools.remove(name);
        if (executor != null) executor.shutdown();
    }

    public static void shutdownAll() {
        pools.values().forEach(ThreadPoolExecutor::shutdown);
        pools.clear();
    }

    public static Map<String, ThreadPoolExecutor> getPools() {
        return pools;
    }

    private static class NamedThreadFactory implements ThreadFactory {
        private final String prefix;
        private final AtomicInteger count = new AtomicInteger(1);

        NamedThreadFactory(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, prefix + "-thread-" + count.getAndIncrement());
        }
    }
    
}
