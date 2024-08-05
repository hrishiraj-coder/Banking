package com.finance.model.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityPaymentResponse {
    private String message;
    private String transactionId;
}
