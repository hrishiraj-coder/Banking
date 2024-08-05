package com.core_user_service.exception;

public class InvalidBankingUserException extends SimpleBankingGlobalException {

    public InvalidBankingUserException(String message, String code) {
        super(message, code);
    }
}
