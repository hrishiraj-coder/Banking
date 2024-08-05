package com.finance.exception;

public class InsufficientFundsException extends SimpleBankingGlobalException {

    public InsufficientFundsException() {
        super("InsufficientFunds.", GlobalErrorCode.INSUFFICIENT_FUNDS);
    }

    public InsufficientFundsException(String message,String code) {
        super(message, code);
    }

}
