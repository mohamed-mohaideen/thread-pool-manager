package com.msoft.threadmanager.controller;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msoft.threadmanager.dto.ThreadPoolInfo;
import com.msoft.threadmanager.util.ThreadPoolManager;

@RestController
@RequestMapping("/thread-pools")
public class ThreadPoolController {

	 @PostMapping("/create")
	    public String create(@RequestParam String name,
	                         @RequestParam int core,
	                         @RequestParam int max,
	                         @RequestParam int queue) {
	        ThreadPoolManager.createPool(name, core, max, queue);
	        return "Created pool: " + name;
	    }
	
    @GetMapping
    public List<ThreadPoolInfo> listPools() {
        return ThreadPoolManager.getPools().entrySet().stream()
        		.map(entry -> {
        			ThreadPoolExecutor exec = entry.getValue();
                    return new ThreadPoolInfo(
                            entry.getKey(),
                            exec.getCorePoolSize(),
                            exec.getMaximumPoolSize(),
                            exec.getActiveCount(),
                            exec.getPoolSize(),
                            exec.getQueue().size()
                    ); 
        		}).toList();
    }

    @PostMapping("/{name}/resize")
    public String resize(@PathVariable String name, @RequestParam int core, @RequestParam int max) {
        ThreadPoolManager.resizePool(name, core, max);
        return "Resized " + name + " to core=" + core + ", max=" + max;
    }

    @DeleteMapping("/{name}")
    public String shutdown(@PathVariable String name) {
        ThreadPoolManager.shutdownPool(name);
        return "Shutdown pool: " + name;
    }
    
}
