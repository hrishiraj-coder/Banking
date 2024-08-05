package com.finance.controller;

import com.finance.model.dto.BankAccount;
import com.finance.model.dto.UtilityAccount;
import com.finance.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping("/bankaccount/{accountNumber}")
    public ResponseEntity<BankAccount> readBankAccount(@PathVariable String accountNumber) {
        BankAccount bankAccount = bankAccountService.readBankAccount(accountNumber);
        return ResponseEntity.ok(bankAccount);
    }

    @GetMapping("/utilityaccount/provider/{provider}")
    public ResponseEntity<UtilityAccount> readUtilityAccountByProvider(@PathVariable String provider) {
        UtilityAccount utilityAccount = bankAccountService.readUtilityAccount(provider);
        return ResponseEntity.ok(utilityAccount);
    }

    @GetMapping("/utilityaccount/id/{id}")
    public ResponseEntity<UtilityAccount> readUtilityAccountById(@PathVariable Long id) {
        UtilityAccount utilityAccount = bankAccountService.readUtilityAccount(id);
        return ResponseEntity.ok(utilityAccount);
    }
}
