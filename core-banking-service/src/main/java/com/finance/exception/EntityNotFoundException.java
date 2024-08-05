package com.finance.exception;

public class EntityNotFoundException extends SimpleBankingGlobalException {

public EntityNotFoundException(){
    super("Entity Not Found in DB.",GlobalErrorCode.ERROR_ENTITY_NOT_FOUND);
}

public EntityNotFoundException(String message){
    super(message,GlobalErrorCode.ERROR_ENTITY_NOT_FOUND);
}

}
