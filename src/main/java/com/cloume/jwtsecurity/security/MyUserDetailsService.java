package com.cloume.jwtsecurity.security;

import com.cloume.jwtsecurity.model.Role;
import com.cloume.jwtsecurity.model.User;
import com.cloume.jwtsecurity.model.UserRole;
import com.cloume.jwtsecurity.repository.RoleRepository;
import com.cloume.jwtsecurity.repository.UserRoleRepository;
import com.cloume.jwtsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username: %s", username));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        //根据角色权限关联表
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        for (UserRole userRole : userRoles) {
            Role role = roleRepository.findByIdEquals(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}

