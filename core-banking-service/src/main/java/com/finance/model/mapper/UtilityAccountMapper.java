package com.finance.model.mapper;

import com.finance.model.dto.UtilityAccount;
import com.finance.model.entity.UtilityAccountEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UtilityAccountMapper extends BaseMapper<UtilityAccountEntity, UtilityAccount> {


    @Override
    public UtilityAccountEntity convertToEntity(UtilityAccount dto, Object... args) {
        UtilityAccountEntity entity = new UtilityAccountEntity();

        if (dto != null) {
            BeanUtils.copyProperties(dto, entity);
        }

        return entity;
    }

    @Override
    public UtilityAccount convertToDto(UtilityAccountEntity entity, Object... args) {
        UtilityAccount dto = new UtilityAccount();

        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }

        return dto;
    }
}
