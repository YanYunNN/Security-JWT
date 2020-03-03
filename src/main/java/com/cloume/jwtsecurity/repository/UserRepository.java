package com.cloume.jwtsecurity.repository;

import com.cloume.jwtsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findTopByUsernameIs(String username);
}
