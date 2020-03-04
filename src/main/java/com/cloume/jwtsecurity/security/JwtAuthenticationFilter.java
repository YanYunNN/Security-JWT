package com.cloume.jwtsecurity.security;

import com.cloume.commons.rest.response.RestResponse;
import com.cloume.jwtsecurity.service.impl.UserServiceImpl;
import com.cloume.jwtsecurity.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//处理登入操作
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * 登录鉴权
     * POST/auth/login
     * @param authenticationManager
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setFilterProcessesUrl("/auth/login");
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从请求的 POST 中拿取 username 和 password 两个字段进行登入
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        // 交给 AuthenticationManager 进行鉴权
        return getAuthenticationManager().authenticate(token);
    }

    /*
    鉴权成功进行的操作，我们这里设置返回加密后的 token
    */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        handleResponse(request, response, authResult, null);
    }

    /*
    鉴权失败进行的操作，我们这里就返回 用户名或密码错误 的信息
    */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        handleResponse(request, response, null, failed);
        logger.info(request.getParameter("username") + "登陆失败");
    }

    private void handleResponse(HttpServletRequest request, HttpServletResponse response, Authentication authResult, AuthenticationException failed) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        if (authResult != null) {
            // 处理登入成功请求
            //TODO User获取不到
            User user = (User) authResult.getPrincipal();
            String token = JwtUtil.sign(user.getUsername(), user.getPassword());
            logger.info(user.getUsername() + "登陆成功");
            Map<String, Object> res = new HashMap<>();
            res.put("token", token);
            res.put("user", user);
            response.getWriter().write(mapper.writeValueAsString(RestResponse.good(res)));
        } else {
            // 处理登入失败请求
            response.getWriter().write(mapper.writeValueAsString(RestResponse.bad(-1, "用户名或密码错误")));
        }
    }
}
