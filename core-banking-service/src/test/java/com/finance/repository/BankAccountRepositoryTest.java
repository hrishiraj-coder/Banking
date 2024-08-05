package com.finance.repository;

import com.finance.exception.EntityNotFoundException;
import com.finance.model.AccountStatus;
import com.finance.model.AccountType;
import com.finance.model.entity.BankAccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@DataJpaTest
class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository repository;

    @Test
    void findByNumber() {
        BankAccountEntity accountEntity = BankAccountEntity.builder()
                .id(1L)
                .user(null)
                .actualBalance(new BigDecimal("500"))
                .availableBalance(new BigDecimal("500"))
                .type(AccountType.FIXED_DEPOSIT)
                .status(AccountStatus.ACTIVE)
                .number("123")
                .build();

        repository.save(accountEntity);

        Optional<BankAccountEntity> entity=repository.findByNumber("123");

        assertNotNull(entity);
        assertEquals("123",entity.get().getNumber());
        assertEquals(new BigDecimal("500"),entity.get().getActualBalance());

    }
    @Test
    void findByNumberNotFound(){

        assertThrows(EntityNotFoundException.class,()->repository.
                findByNumber("123").orElseThrow(EntityNotFoundException::new));
    }
}