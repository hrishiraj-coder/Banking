package com.finance.repository;

import com.finance.model.dto.User;
import com.finance.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdentificationNumber(String number);

}
