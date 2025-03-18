package com.application.user.dtos;


import com.application.user.entities.Role;
import com.application.user.entities.Role_enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto
{
    private Integer id;
    private Role_enum role;

    public RoleDto(Role role)
    {
        this.id = role.getId();
        this.role = role.getRole();
    }
}
