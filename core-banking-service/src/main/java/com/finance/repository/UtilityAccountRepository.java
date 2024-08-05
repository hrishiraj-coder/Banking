package com.finance.repository;

import com.finance.model.dto.UtilityAccount;
import com.finance.model.entity.UtilityAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilityAccountRepository extends JpaRepository<UtilityAccountEntity, Long> {

   Optional<UtilityAccountEntity> findByProviderName(String provider);
}
