package com.msoft.threadmanager.util;

import java.util.concurrent.TimeUnit;

public class RetryUtil {

	public static void executeWithRetry(Runnable task, int maxRetries) {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                task.run();
                return; // Success
            } catch (Exception e) {
                attempt++;
                long backoff = (long) Math.pow(2, attempt) * 1000;
                System.out.println("Retry attempt " + attempt + " after " + backoff + "ms");
                try {
                    TimeUnit.MILLISECONDS.sleep(backoff);
                } catch (InterruptedException ignored) {}
            }
        }
        System.out.println("Task failed after " + maxRetries + " retries");
    }
	
}
