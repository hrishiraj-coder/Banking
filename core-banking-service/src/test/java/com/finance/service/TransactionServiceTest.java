package com.finance.service;

import com.finance.exception.EntityNotFoundException;
import com.finance.exception.InsufficientFundsException;
import com.finance.model.dto.BankAccount;
import com.finance.model.dto.UtilityAccount;
import com.finance.model.dto.request.FundTransferRequest;
import com.finance.model.dto.request.UtilityPaymentRequest;
import com.finance.model.dto.response.FundTransferResponse;
import com.finance.model.dto.response.UtilityPaymentResponse;
import com.finance.model.entity.BankAccountEntity;
import com.finance.model.entity.TransactionEntity;
import com.finance.model.mapper.BankAccountMapper;
import com.finance.model.mapper.UtilityAccountMapper;
import com.finance.repository.BankAccountRepository;
import com.finance.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private BankAccountService accountService;
    @InjectMocks
    private TransactionService transactionService;

    BankAccount fromAccount;
    BankAccount toAccount;
    BankAccountEntity fromAccountEntity;
    BankAccountEntity toAccountEntity;

    @BeforeEach
    void setUp() {



        fromAccount = new BankAccount();
        fromAccount.setNumber("123");
        fromAccount.setActualBalance(new BigDecimal("1000.00"));
        fromAccount.setAvailableBalance(new BigDecimal("1000.00"));

        toAccount = new BankAccount();
        toAccount.setNumber("456");
        toAccount.setActualBalance(new BigDecimal("500.00"));
        toAccount.setAvailableBalance(new BigDecimal("500.00"));

        fromAccountEntity = new BankAccountEntity();
        fromAccountEntity.setNumber("123");
        fromAccountEntity.setActualBalance(new BigDecimal("1000.00"));
        fromAccountEntity.setAvailableBalance(new BigDecimal("1000.00"));

        toAccountEntity = new BankAccountEntity();
        toAccountEntity.setNumber("456");
        toAccountEntity.setActualBalance(new BigDecimal("500.00"));
        toAccountEntity.setAvailableBalance(new BigDecimal("500.00"));
    }

    @Test
    void fundTransferTest() {

        when(accountService.readBankAccount("123")).thenReturn(fromAccount);
        when(accountService.readBankAccount("456")).thenReturn(toAccount);

        when(bankAccountRepository.findByNumber("123")).thenReturn(Optional.of(fromAccountEntity));
        when(bankAccountRepository.findByNumber("456")).thenReturn(Optional.of(toAccountEntity));

        FundTransferRequest request = new FundTransferRequest();
        request.setAmount(new BigDecimal("500.00"));
        request.setFromAccount("123");
        request.setToAccount("456");

        FundTransferResponse response = transactionService.fundTransfer(request);

        assertEquals("Transaction completed successfully", response.getMessage());
        assertNotNull(response.getTransactionId());
        verify(transactionRepository, times(2)).save(any(TransactionEntity.class));

    }

    @Test
    void utilityPayment() {
        when(accountService.readBankAccount("123")).thenReturn(fromAccount);
        when(bankAccountRepository.findByNumber("123")).thenReturn(Optional.of(fromAccountEntity));

        UtilityAccount utilityAccount = new UtilityAccount();
        utilityAccount.setId(1L);
        when(accountService.readUtilityAccount(1L)).thenReturn(utilityAccount);

        UtilityPaymentRequest request = new UtilityPaymentRequest();
        request.setAccount("123");
        request.setAmount(new BigDecimal("150.00"));
        request.setProviderId(1L);
        request.setReferenceNumber("REF123");

        UtilityPaymentResponse response = transactionService.utilityPayment(request);

        assertEquals("Utility payment transfer successfully completed.", response.getMessage());
        assertNotNull(response.getTransactionId());
        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));

    }

    @Test
    void testInsufficientFunds() {
        fromAccount.setActualBalance(new BigDecimal("100.00"));
        when(accountService.readBankAccount("123")).thenReturn(fromAccount);

        FundTransferRequest request = new FundTransferRequest();
        request.setFromAccount("123");
        request.setToAccount("456");
        request.setAmount(new BigDecimal("200.00"));

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
                () -> transactionService.fundTransfer(request),
                "Expected fundTransfer to throw, but it didn't");

        assertTrue(exception.getMessage().contains("BANKING-CORE-SERVICE-1001"));
    }

    @Test
    void testEntityNotFoundException() {
        when(accountService.readBankAccount("123")).thenReturn(fromAccount);
        when(bankAccountRepository.findByNumber("123")).thenReturn(Optional.empty());

        FundTransferRequest request = new FundTransferRequest();
        request.setFromAccount("123");
        request.setToAccount("456");
        request.setAmount(new BigDecimal("200.00"));

        EntityNotFoundException exception=assertThrows(EntityNotFoundException.class,
                ()->transactionService.fundTransfer(request),"Failed to return exception");
        assertTrue(exception.getMessage().contains("BANKING-CORE-SERVICE-1000"));
    }



}
