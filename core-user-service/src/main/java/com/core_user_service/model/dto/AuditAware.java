package com.core_user_service.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditAware implements Serializable {
    @JsonIgnore
    @CreatedDate
    private Instant createdDate;
    @JsonIgnore
    @CreatedBy
    private String createdBy;
    @JsonIgnore
    @LastModifiedDate
    private Instant modifiedDate;
    @JsonIgnore
    @LastModifiedBy
    private Instant modifiedBy;
    @Version
    private Long Version;


}
