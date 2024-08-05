package com.finance.model.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FundTransferResponse {

    private String message;
    private String transactionId;

}
