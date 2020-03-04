package com.cloume.jwtsecurity.controller;

import com.cloume.commons.rest.response.RestResponse;
import com.cloume.commons.verify.Verifier;
import com.cloume.jwtsecurity.model.User;
import com.cloume.jwtsecurity.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户认证/登录处理
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * @param body
     * @return
     */
    @PostMapping(value = "/loginUser")
    public RestResponse<?> login(@RequestBody Map<String, String> body) {
        if (!new Verifier()
                .rule("username")
                .rule("password")
                .result((r, s) -> {
                }).verify(body)) {
            return RestResponse.bad(-1, "invalid request body");
        }
        try {
            String username = body.get("username");
            String password = body.get("password");
            User user = userService.findByUsername(username);
            if (user == null) {
                return RestResponse.bad(-3, "该用户名不存在，请核实后再登录!");
            }
            injectPrincipalToSpringContext(user, password);
            return RestResponse.good(user);
        } catch (Exception e) {
            return RestResponse.bad(-2, "登录失败，请检查您的用户名或密码是否正确!");
        }
    }

    /**
     * 新建用户
     * @return
     */
    @PostMapping(value = "/register")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse<?> register(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String password = body.get("password");
            User user = new User();
            user.setUsername(username);
            user.setPassword(User.PASSWORD_ENCODER.encode(password));
            user.setRealname(body.get("name"));
            User res = userService.save(user);
            return RestResponse.good(res);
        } catch (Exception e) {
            return RestResponse.bad(-1, "注册失败!");
        }
    }

    @Autowired
    UserDetailsService userDetailsService;

    private void injectPrincipalToSpringContext(User user, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), password);
        token.setDetails(user);

        DaoAuthenticationProvider authenticator = new DaoAuthenticationProvider();
        authenticator.setUserDetailsService(userDetailsService);
        authenticator.setPasswordEncoder(User.PASSWORD_ENCODER);
        Authentication authentication = authenticator.authenticate(token);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }
}
