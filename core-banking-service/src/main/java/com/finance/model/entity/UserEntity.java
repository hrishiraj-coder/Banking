package com.finance.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "banking_core_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String identificationNumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BankAccountEntity> accounts;

}
