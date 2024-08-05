package com.finance.controller;

import com.finance.model.AccountStatus;
import com.finance.model.AccountType;
import com.finance.model.dto.User;
import com.finance.model.entity.UserEntity;
import com.finance.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService service;
    @InjectMocks
    private UserController controller;

    private User dto;


    @BeforeEach
    void setUp() {
        dto = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .identificationNumber("123")
                .build();
    }

    @Test
    void readUser() {
        Mockito.when(service.readUser("123")).thenReturn(dto);
        ResponseEntity<User> responseEntity=controller.readUser("123");
        assertNotNull(responseEntity);
        assertEquals(ResponseEntity.ok(dto),responseEntity);
        Mockito.verify(service,Mockito.times(1)).readUser("123");
    }

    @Test
    void readAllUsers() {
        Page<User> page=new PageImpl<>(List.of(dto));
        Pageable  pageable= PageRequest.of(0,10);

        Mockito.when(service.readAllUsers(pageable)).thenReturn(List.of(dto));
        ResponseEntity<List<User>> responseEntity=controller.readAllUsers(pageable);

        assertNotNull(responseEntity);
        assertEquals(ResponseEntity.ok(List.of(dto)),responseEntity);

        Mockito.verify(service,Mockito.times(1)).readAllUsers(pageable);
    }
}