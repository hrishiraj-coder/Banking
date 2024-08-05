package com.core_user_service.exception;

public class EntityNotFoundException extends SimpleBankingGlobalException {

    public EntityNotFoundException() {
        super("Requested entity not present.", GlobalErrorCode.ERROR_ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String message) {
        super(message,GlobalErrorCode.ERROR_ENTITY_NOT_FOUND);
    }
}
