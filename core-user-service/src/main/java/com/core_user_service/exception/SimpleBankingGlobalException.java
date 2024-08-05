package com.core_user_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
