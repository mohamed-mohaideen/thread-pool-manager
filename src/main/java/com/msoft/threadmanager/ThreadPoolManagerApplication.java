package com.msoft.threadmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.msoft.threadmanager.util.ThreadPoolManager;

@SpringBootApplication
public class ThreadPoolManagerApplication {

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Gracefully shutting down all thread pools...");
            ThreadPoolManager.shutdownAll();
        }));
		
		SpringApplication.run(ThreadPoolManagerApplication.class, args);
	}

}
