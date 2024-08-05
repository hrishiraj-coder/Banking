package com.finance.service;

import com.finance.exception.EntityNotFoundException;
import com.finance.exception.GlobalErrorCode;
import com.finance.exception.InsufficientFundsException;
import com.finance.model.TransactionType;
import com.finance.model.dto.BankAccount;
import com.finance.model.dto.UtilityAccount;
import com.finance.model.dto.request.FundTransferRequest;
import com.finance.model.dto.request.UtilityPaymentRequest;
import com.finance.model.dto.response.FundTransferResponse;
import com.finance.model.dto.response.UtilityPaymentResponse;
import com.finance.model.entity.BankAccountEntity;
import com.finance.model.entity.TransactionEntity;
import com.finance.repository.BankAccountRepository;
import com.finance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountService accountService;
    private final BankAccountRepository bankAccountRepository;

    public FundTransferResponse fundTransfer(FundTransferRequest fundTransferRequest) {
        BankAccount fromBankAccount = accountService.readBankAccount(fundTransferRequest.getFromAccount());
        BankAccount toBankAccount = accountService.readBankAccount(fundTransferRequest.getToAccount());

        validateBalance(fromBankAccount, fundTransferRequest.getAmount());

        String transactionId = internalFundsTransfer(fromBankAccount, toBankAccount, fundTransferRequest.getAmount());
        return FundTransferResponse.builder().message("Transaction completed successfully").transactionId(transactionId).build();


    }

    public UtilityPaymentResponse utilityPayment(UtilityPaymentRequest utilityPaymentRequest) {
        String transactionId = UUID.randomUUID().toString();

        BankAccount fromBankAccount = accountService.readBankAccount(utilityPaymentRequest.getAccount());

        validateBalance(fromBankAccount, utilityPaymentRequest.getAmount());

        UtilityAccount utilityAccount = accountService.readUtilityAccount(utilityPaymentRequest.getProviderId());

        BankAccountEntity fromAccount = bankAccountRepository.findByNumber(fromBankAccount.getNumber()).get();


        fromAccount.setActualBalance(fromAccount.getActualBalance().subtract(utilityPaymentRequest.getAmount()));
        fromAccount.setAvailableBalance(fromAccount.getActualBalance().subtract(utilityPaymentRequest.getAmount()));


        transactionRepository.save(TransactionEntity.builder().transactionType(TransactionType.UTILITY_PAYMENT)
                .transactionId(transactionId)
                .referenceNumber(utilityPaymentRequest.getReferenceNumber())
                .amount(utilityPaymentRequest.getAmount().negate())
                .account(fromAccount)
                .build());

        return UtilityPaymentResponse.builder().message("Utility payment transfer successfully completed.").transactionId(transactionId).build();

    }

    public void validateBalance(BankAccount bankAccount, BigDecimal amount) {
        if (bankAccount.getActualBalance().compareTo(BigDecimal.ZERO) < 0 || bankAccount.getActualBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in account " + bankAccount.getNumber(), GlobalErrorCode.INSUFFICIENT_FUNDS);
        }
    }


    public String internalFundsTransfer(BankAccount fromBankAccount, BankAccount toBankAccount, BigDecimal amount) {
        String transactionId = UUID.randomUUID().toString();

        BankAccountEntity fromBankAccountEntity = bankAccountRepository.findByNumber(fromBankAccount.getNumber()).orElseThrow(EntityNotFoundException::new);
        BankAccountEntity toBankAccountEntity = bankAccountRepository.findByNumber(toBankAccount.getNumber()).orElseThrow(EntityNotFoundException::new);

        fromBankAccountEntity.setActualBalance(fromBankAccountEntity.getActualBalance().subtract(amount));
        fromBankAccountEntity.setAvailableBalance(fromBankAccountEntity.getActualBalance().subtract(amount));
        bankAccountRepository.save(fromBankAccountEntity);

        transactionRepository.save(TransactionEntity.builder().transactionType(TransactionType.FUND_TRANSFER)
                .transactionId(transactionId)
                .referenceNumber(toBankAccount.getNumber())
                .account(fromBankAccountEntity).amount(amount.negate())
                .build());

        toBankAccountEntity.setActualBalance(toBankAccountEntity.getActualBalance().add(amount));
        toBankAccountEntity.setAvailableBalance(toBankAccountEntity.getActualBalance().add(amount));


        transactionRepository.save(TransactionEntity.builder().transactionId(transactionId)
                .account(toBankAccountEntity)
                .amount(amount.negate())
                .referenceNumber(toBankAccountEntity.getNumber())
                .transactionType(TransactionType.FUND_TRANSFER).build());

        return transactionId;

    }


}
