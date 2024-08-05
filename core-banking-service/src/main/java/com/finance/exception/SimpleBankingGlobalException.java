package com.finance.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleBankingGlobalException extends RuntimeException {

    private String code;
    private String message;

    public SimpleBankingGlobalException(String message) {
        super(message);
    }

}
