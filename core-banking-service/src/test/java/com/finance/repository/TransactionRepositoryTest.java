package com.finance.repository;

import com.finance.model.entity.TransactionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;
    private TransactionEntity entity;

    @BeforeEach
    void setUp() {
        entity = TransactionEntity.builder()
                .id(1L)
                .transactionId("123")
                .referenceNumber("ref122")
                .amount(new BigDecimal("5000"))
                .account(null)
                .build();
    }

    @Test
    void testFindById() {
        repository.save(entity);

        Optional<TransactionEntity> optionalTransactionEntity = repository.findById(1L);

        assertNotNull(entity);
        assertEquals("123",optionalTransactionEntity.get().getTransactionId());
    }


}