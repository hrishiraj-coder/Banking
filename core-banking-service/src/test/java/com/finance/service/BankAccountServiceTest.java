package com.finance.service;

import com.finance.exception.EntityNotFoundException;
import com.finance.model.dto.BankAccount;
import com.finance.model.dto.UtilityAccount;
import com.finance.model.entity.BankAccountEntity;
import com.finance.model.entity.UtilityAccountEntity;
import com.finance.model.mapper.BankAccountMapper;
import com.finance.model.mapper.UtilityAccountMapper;
import com.finance.repository.BankAccountRepository;
import com.finance.repository.UtilityAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {
    @InjectMocks
    private BankAccountService bankAccountService;
    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private UtilityAccountRepository utilityAccountRepository;
    @Mock
    private BankAccountMapper bankAccountMapper;
    @Mock
    private UtilityAccountMapper utilityAccountMapper;

    @Test
    void testReadBankAccountSuccess() {
        BankAccountEntity mockBankAccountEntity = new BankAccountEntity();
        mockBankAccountEntity.setNumber("ACC123456");
        Mockito.lenient().when(bankAccountRepository.findByNumber("ACC123456")).thenReturn(Optional.of(mockBankAccountEntity));

        BankAccount mockDto = new BankAccount();
        mockDto.setNumber("ACC123456");
        Mockito.lenient().when(bankAccountMapper.convertToDto(mockBankAccountEntity)).thenReturn(mockDto);

        BankAccount result = bankAccountService.readBankAccount("ACC123456");
        assertNotNull(result);
        assertEquals("ACC123456", result.getNumber());
        verify(bankAccountRepository, times(1)).findByNumber("ACC123456");
    }

    @Test
    void testReadBankAccountNotFound() {
        when(bankAccountRepository.findByNumber("ACC123456")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bankAccountService.readBankAccount("ACC123456"));

    }

    @Test
    void readUtilityAccount() {
        UtilityAccountEntity mockUtilityAccountEntity = new UtilityAccountEntity();
        mockUtilityAccountEntity.setId(1L);

        Mockito.lenient().when(utilityAccountRepository.findById(1L)).thenReturn(Optional.of(mockUtilityAccountEntity));

        UtilityAccount mockUtilityAccountDto = new UtilityAccount();
        mockUtilityAccountDto.setId(1L);
        Mockito.lenient().when(utilityAccountMapper.convertToDto(mockUtilityAccountEntity)).thenReturn(mockUtilityAccountDto);

        UtilityAccount result = bankAccountService.readUtilityAccount(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(utilityAccountRepository, times(1)).findById(1L);
    }

    @Test
    void readUtilityAccountNotFound() {
        when(utilityAccountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> bankAccountService.readUtilityAccount(1L));
     }

    @Test
    void testReadUtilityAccount() {
        UtilityAccountEntity entity=new UtilityAccountEntity();
        entity.setProviderName("provider");
       Mockito.lenient().when(utilityAccountRepository.findByProviderName("provider")).thenReturn(Optional.of(entity));

        UtilityAccount dto=new UtilityAccount();
        dto.setProviderName("provider");
        Mockito.lenient().when(utilityAccountMapper.convertToDto(entity)).thenReturn(dto);

        UtilityAccount result=bankAccountService.readUtilityAccount("provider");
        assertNotNull(result);
        assertEquals("provider",result.getProviderName());
        verify(utilityAccountRepository,times(1)).findByProviderName("provider");

    }

    @Test
    void readUtilityAccountByProviderNotFound() {
        when(utilityAccountRepository.findByProviderName("provider")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> bankAccountService.readUtilityAccount("provider"));
    }

}