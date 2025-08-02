package com.msoft.threadmanager.dto;

public record ThreadPoolInfo(String name,
		int corePoolSize,
		int maxPoolSize,
		int activeCount, 
		int poolSize,
		int queueSize) {
}
