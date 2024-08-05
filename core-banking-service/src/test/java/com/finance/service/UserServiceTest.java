package com.finance.service;

import com.finance.model.AccountStatus;
import com.finance.model.AccountType;
import com.finance.model.dto.User;
import com.finance.model.entity.BankAccountEntity;
import com.finance.model.entity.UserEntity;
import com.finance.model.mapper.UserMapper;
import com.finance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;
    private User userDto;

    @BeforeEach
    void setUp() {

        userEntity = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .identificationNumber("123")
                .build();

        BankAccountEntity accountEntity = BankAccountEntity.builder()
                .id(1L)
                .user(userEntity)
                .actualBalance(new BigDecimal("500"))
                .availableBalance(new BigDecimal("500"))
                .type(AccountType.FIXED_DEPOSIT)
                .status(AccountStatus.ACTIVE)
                .number("123")
                .build();

        userEntity.setAccounts(List.of(accountEntity));

        userDto = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .bankAccounts(List.of(
                        com.finance.model.dto.BankAccount.builder()
                                .id(1L)
                                .number("1234567890")
                                .type(AccountType.SAVINGS_ACCOUNT)
                                .status(AccountStatus.ACTIVE)
                                .availableBalance(new BigDecimal("1000.00"))
                                .actualBalance(new BigDecimal("1200.00"))
                                .build()
                ))
                .identificationNumber("123")
                .build();
    }

    @Test
    void testReadUser() {
        when(userRepository.findByIdentificationNumber("123")).thenReturn(Optional.of(userEntity));
        when(userMapper.convertToDto(userEntity)).thenReturn(userDto);

        User result = userService.readUser("123");

        assertNotNull(result);
        assertEquals("123", result.getIdentificationNumber());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());

        verify(userRepository, times(1)).findByIdentificationNumber("123");
        verify(userMapper, times(1)).convertToDto(userEntity);
    }

    @Test
    void readAllUsers() {
        List<UserEntity> entities = Arrays.asList(userEntity);
        Page<UserEntity> page = new PageImpl<>(entities);
        List<User> users = Arrays.asList(userDto);
        Pageable pageable = PageRequest.of(0, 10);


        when(userRepository.findAll(pageable)).thenReturn(page);

        when(userMapper.convertToDtoList(entities)).thenReturn(users);


        List<User> result = userService.readAllUsers(pageable);
        System.out.println(result);

        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getIdentificationNumber());

        verify(userRepository, times(1)).findAll(pageable);
        verify(userMapper, times(1)).convertToDtoList(entities);
    }
}
