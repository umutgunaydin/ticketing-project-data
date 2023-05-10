package com.company.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isDeleted = false;
    @Column(nullable = false,updatable = false)

    private LocalDateTime insertDateTime;
    @Column(nullable = false,updatable = false)

    private Long insertUserId;
    @Column(nullable = false)

    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)

    private Long lastUpdateUserId;

    @PrePersist
    private void onPrePersist(){
        this.insertDateTime=LocalDateTime.now();
        this.lastUpdateDateTime=LocalDateTime.now();
        this.insertUserId=1L;
        this.lastUpdateUserId=1L;
    }
    @PreUpdate
    private void onPreUpdate(){
        this.lastUpdateDateTime=LocalDateTime.now();
        this.lastUpdateUserId=1L;
    }


}
