package com.application.user.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role
{
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Role_enum role;
}