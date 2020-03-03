package com.cloume.jwtsecurity.repository;

import com.cloume.jwtsecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

    Role findByIdEquals(Integer id);
}
