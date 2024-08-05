package com.core_user_service.exception;

import com.core_user_service.model.dto.User;

public class UserAlreadyRegisteredException extends SimpleBankingGlobalException {

    public UserAlreadyRegisteredException(String message,String code){
        super(message,code);
    }
}
