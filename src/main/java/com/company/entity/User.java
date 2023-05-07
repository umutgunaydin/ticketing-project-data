package com.company.entity;

import com.company.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean enabled;
    private String phone;
    @ManyToOne
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
