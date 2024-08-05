package com.finance.model.mapper;

import com.finance.model.dto.BankAccount;
import com.finance.model.entity.BankAccountEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper extends BaseMapper<BankAccountEntity,BankAccount>  {

    @Override
    public BankAccountEntity convertToEntity(BankAccount bankAccountDto, Object... args) {
        BankAccountEntity entity = new BankAccountEntity();
        if (bankAccountDto != null) {
            BeanUtils.copyProperties(bankAccountDto, entity, "user");
        }

        return entity;
    }

    @Override
    public BankAccount convertToDto(BankAccountEntity entity,Object...args){
        BankAccount bankAccountDto=new BankAccount();
        if(entity!=null){
            BeanUtils.copyProperties(entity,bankAccountDto,"user");
        }
        return bankAccountDto;
    }
}
