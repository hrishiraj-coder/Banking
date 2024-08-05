package com.finance.model.entity;

import com.finance.model.AccountStatus;
import com.finance.model.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banking_core_account")
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private BigDecimal availableBalance;

    private BigDecimal actualBalance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
