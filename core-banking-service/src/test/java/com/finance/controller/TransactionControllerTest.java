package com.finance.controller;

import com.finance.model.dto.request.FundTransferRequest;
import com.finance.model.dto.request.UtilityPaymentRequest;
import com.finance.model.dto.response.FundTransferResponse;
import com.finance.model.dto.response.UtilityPaymentResponse;
import com.finance.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private TransactionController transactionController;

    private FundTransferRequest request;
    private FundTransferResponse response;
    private UtilityPaymentRequest utilityPaymentRequest;
    private UtilityPaymentResponse utilityPaymentResponse;

    @BeforeEach
    void setUp() {
        request = new FundTransferRequest();
        request.setFromAccount("123");
        request.setToAccount("456");
        request.setAmount(new BigDecimal("500"));

        response = FundTransferResponse.builder()
                .transactionId("45678")
                .message("done")
                .build();

        utilityPaymentRequest = new UtilityPaymentRequest();
        utilityPaymentRequest.setProviderId(1L);
        utilityPaymentRequest.setAmount(new BigDecimal(500));
        utilityPaymentRequest.setAccount("123");
        utilityPaymentRequest.setReferenceNumber("ref789");

        utilityPaymentResponse = UtilityPaymentResponse.builder()
                .transactionId("1234")
                .message("done")
                .build();
    }

    @Test
    void fundTransfer() {
        when(transactionService.fundTransfer(request)).thenReturn(response);
        ResponseEntity<FundTransferResponse> responseEntity = transactionController.fundTransfer(request);
        assertNotNull(responseEntity);
        assertEquals(ResponseEntity.ok(response), responseEntity);
        verify(transactionService, times(1)).fundTransfer(request);
    }

    @Test
    void utilityPayment() {
        when(transactionService.utilityPayment(utilityPaymentRequest)).thenReturn(utilityPaymentResponse);
        ResponseEntity<UtilityPaymentResponse> responseEntity = transactionController.utilityPayment(utilityPaymentRequest);
        assertNotNull(responseEntity);
        assertEquals(ResponseEntity.ok(utilityPaymentResponse), responseEntity);
        verify(transactionService, times(1)).utilityPayment(utilityPaymentRequest);
    }
}