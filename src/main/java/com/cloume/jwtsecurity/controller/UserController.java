package com.cloume.jwtsecurity.controller;


import com.cloume.commons.rest.response.RestResponse;
import com.cloume.jwtsecurity.model.User;
import com.cloume.jwtsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private IUserService userService;


    /**
     * 获取用户列表
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/list")
    public RestResponse<?> list(@RequestParam(value = "name", defaultValue = "") String name,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10000") int size) {
        List<User> list = userService.list(name, page, size);
        return RestResponse.good(list);
    }
}
