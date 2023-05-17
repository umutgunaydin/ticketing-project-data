package com.company.entity;

import com.company.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
//@Where(clause = "is_deleted=false") //userRepository includes this condition automatically for all queries
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String userName;
    private String password;
    private boolean enabled;
    private String phone;
    @ManyToOne
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
