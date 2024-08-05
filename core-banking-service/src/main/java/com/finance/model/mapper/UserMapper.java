package com.finance.model.mapper;

import com.finance.model.dto.User;
import com.finance.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper extends BaseMapper<UserEntity, User> {
    private final BankAccountMapper bankAccountMapper;

    @Override
    public UserEntity convertToEntity(User dto, Object... args) {
        UserEntity entity = new UserEntity();

        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "accounts");
            entity.setAccounts(bankAccountMapper.convertToEntityList(dto.getBankAccounts()));
        }
        return entity;
    }

    @Override
    public User convertToDto(UserEntity entity, Object... args) {
        User dto = new User();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "accounts");
            dto.setBankAccounts(bankAccountMapper.convertToDtoList(entity.getAccounts()));
        }
        return dto;
    }
}
