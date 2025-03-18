package com.application.user.entities;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import com.application.user.entities.Role;

@Data     //no need for constructor, getters and setters
@Entity
@Table(name = "\"user\"")
public class User {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)  // Assuming a user has one role, but a role can be assigned to multiple users
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    //@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private Set<Role> roles;
}