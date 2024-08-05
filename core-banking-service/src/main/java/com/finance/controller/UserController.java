package com.finance.controller;

import com.finance.model.dto.User;
import com.finance.service.BankAccountService;
import com.finance.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/readuser/{id}")
    public ResponseEntity<User> readUser(@PathVariable String id){
        log.info("Reading user with id {}",id);
         return ResponseEntity.ok(userService.readUser(id));
    }

    @GetMapping("/readAllUsers")
    public ResponseEntity<List<User>> readAllUsers(Pageable pageable){
        return ResponseEntity.ok(userService.readAllUsers(pageable));
    }


}
