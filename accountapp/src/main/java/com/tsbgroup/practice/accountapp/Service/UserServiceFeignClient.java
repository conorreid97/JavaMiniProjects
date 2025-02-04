package com.tsbgroup.practice.accountapp.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserServiceFeignClient {
    @GetMapping("/api/user/{userId}")
    String getUserDetails(@PathVariable String userId);
    
}
