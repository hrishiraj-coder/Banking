package com.core_user_service.service.rest;

import com.core_user_service.rest.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "core-banking-service")
public interface BankingCoreRestClient {

    @GetMapping("/api/v1/user/readuser/{id}")
    UserResponse readUser(@PathVariable("identification") String identification);

}
