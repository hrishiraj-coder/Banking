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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UtilityAccountRepository utilityAccountRepository;
    private final BankAccountMapper bankAccountMapper=new BankAccountMapper();
    private final UtilityAccountMapper utilityAccountMapper=new UtilityAccountMapper();

    public BankAccount readBankAccount(String accountNumber) {
        BankAccountEntity entity = bankAccountRepository.findByNumber(accountNumber).orElseThrow(EntityNotFoundException::new);
        return bankAccountMapper.convertToDto(entity);
    }

    public UtilityAccount readUtilityAccount(String provider) {
        UtilityAccountEntity entity = utilityAccountRepository.findByProviderName(provider)
                .orElseThrow(EntityNotFoundException::new);

        return utilityAccountMapper.convertToDto(entity);
    }

    public UtilityAccount readUtilityAccount(Long id) {
        UtilityAccountEntity entity = utilityAccountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return utilityAccountMapper.convertToDto(entity);
    }


}
