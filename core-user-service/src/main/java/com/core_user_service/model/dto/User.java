package com.core_user_service.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class User {
    private Long id;
    private String email;
    private String identification;
    private String password;
    private String authId;
    private Status status;
}
