package com.finance.controller;

import com.finance.model.dto.request.FundTransferRequest;
import com.finance.model.dto.request.UtilityPaymentRequest;
import com.finance.model.dto.response.FundTransferResponse;
import com.finance.model.dto.response.UtilityPaymentResponse;
import com.finance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/funds-transfer")
    public ResponseEntity<FundTransferResponse> fundTransfer(@RequestBody FundTransferRequest request) {

        log.info("Transferring funds with {}", request.toString());
        return ResponseEntity.ok(transactionService.fundTransfer(request));
    }

    @PostMapping("/utility-transfer")
    public ResponseEntity<UtilityPaymentResponse> utilityPayment(@RequestBody UtilityPaymentRequest request) {
        log.info("Utility transfer initiated with {}", request.toString());
        return ResponseEntity.ok(transactionService.utilityPayment(request));
    }

}
