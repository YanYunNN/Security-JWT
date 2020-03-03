package com.cloume.jwtsecurity.service.impl;

import com.cloume.jwtsecurity.model.User;
import com.cloume.jwtsecurity.repository.UserRepository;
import com.cloume.jwtsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findTopByUsernameIs(username);
    }

    @Override
    public List<User> list(String name, int page, int size) {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

}
