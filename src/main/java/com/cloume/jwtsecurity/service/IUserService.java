package com.cloume.jwtsecurity.service;

import com.cloume.jwtsecurity.model.User;

import java.util.List;

public interface IUserService {

    User findByUsername(String username);

    List<User> list(String name, int page, int size);

    User save(User user);
}
