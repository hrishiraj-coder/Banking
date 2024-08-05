package com.finance.service;

import com.finance.exception.EntityNotFoundException;
import com.finance.model.dto.User;
import com.finance.model.entity.UserEntity;
import com.finance.model.mapper.UserMapper;
import com.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserMapper userMapper ;

    private final UserRepository userRepository;

    public User readUser(String identification) {
        UserEntity entity = userRepository.findByIdentificationNumber(identification).
                orElseThrow(EntityNotFoundException::new);
        return userMapper.convertToDto(entity);
    }

    public List<User> readAllUsers(Pageable pageable){
        return userMapper.convertToDtoList(userRepository.findAll(pageable).getContent());
    }


}
