package com.msoft.threadmanager.config;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.jdbc.core.JdbcTemplate;

import com.msoft.threadmanager.util.ThreadPoolManager;

public class DBPartitionFetcher {

    private final JdbcTemplate jdbcTemplate;

    public DBPartitionFetcher(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void fetchWithPartition(int totalPartitions) {
        var pool = ThreadPoolManager.createPool("db-pool", totalPartitions, totalPartitions, 50);
        IntStream.range(0, totalPartitions).forEach(partition -> pool.execute(() -> {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(
                "SELECT * FROM my_table WHERE MOD(id, ?) = ?", totalPartitions, partition
            );
            System.out.println("Partition " + partition + ": " + results.size() + " rows");
        }));
    }
    
}
