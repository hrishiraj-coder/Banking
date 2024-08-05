package com.finance.controller;

import com.finance.model.dto.BankAccount;
import com.finance.model.dto.UtilityAccount;
import com.finance.model.entity.BankAccountEntity;
import com.finance.model.entity.UtilityAccountEntity;
import com.finance.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BankAccountControllerTest {
    @Mock
    private BankAccountService bankAccountService;
    @InjectMocks
    private BankAccountController bankAccountController;

    private BankAccount bankAccount;
    private UtilityAccount utilityAccountDto;

    @BeforeEach
    void setUp() {

        bankAccount = new BankAccount();
        bankAccount.setNumber("123");
        bankAccount.setActualBalance(new BigDecimal("500.00"));
        bankAccount.setAvailableBalance(new BigDecimal("500.00"));

        utilityAccountDto=UtilityAccount.builder()
                .id(1L)
                .providerName("provider")
                .number("123")
                .build();
    }

    @Test
    void readBankAccount() {
        when(bankAccountService.readBankAccount("123")).thenReturn(bankAccount);

        ResponseEntity<BankAccount> responseEntity = bankAccountController.readBankAccount("123");

        assertNotNull(responseEntity);
        verify(bankAccountService,times(1)).readBankAccount("123");
        assertEquals(ResponseEntity.ok(bankAccount),responseEntity);


    }

    @Test
    void readUtilityAccountByProvider() {
        when(bankAccountService.readUtilityAccount("provider")).thenReturn(utilityAccountDto);
        ResponseEntity<UtilityAccount> responseEntity=bankAccountController.readUtilityAccountByProvider("provider");
        assertNotNull(responseEntity);
        assertEquals(ResponseEntity.ok(utilityAccountDto),responseEntity);
        verify(bankAccountService,times(1)).readUtilityAccount("provider");


    }

    @Test
    void readUtilityAccountById() {
        when(bankAccountService.readUtilityAccount(1L)).thenReturn(utilityAccountDto);
        ResponseEntity<UtilityAccount> responseEntity=bankAccountController.readUtilityAccountById(1L);
        assertNotNull(responseEntity);
        assertEquals(ResponseEntity.ok(utilityAccountDto),responseEntity);
        verify(bankAccountService,times(1)).readUtilityAccount(1L);
    }
}