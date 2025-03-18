package com.application.user.repositories;

import com.application.user.entities.Role;
import com.application.user.entities.Role_enum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>
{
    Optional<Role> findByRole(Role_enum role);
}
