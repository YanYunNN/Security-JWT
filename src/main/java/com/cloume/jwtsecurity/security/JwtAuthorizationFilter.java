package com.cloume.jwtsecurity.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cloume.jwtsecurity.util.JwtUtil;
import com.cloume.jwtsecurity.util.SpringContextUtil;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//处理每个请求鉴权
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    final MyUserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);

        final Map<String, MyUserDetailsService> userDetailsServices = SpringContextUtil.getApplicationContext().getBeansOfType(MyUserDetailsService.class);
        userDetailsService = (MyUserDetailsService) userDetailsServices.values().toArray()[0];
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 判断是否有 token，并且进行认证
        Authentication token = getAuthentication(request);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        // 认证成功
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 如果请求头中没有Authorization信息则直接拦截
        String header = request.getHeader(JwtUtil.TOKEN_HEADER);
        if (header == null || !header.startsWith(JwtUtil.TOKEN_PREFIX)) {
            return null;
        }

        // 如果请求头中有token，且用户名存在，则进行解析，并且设置认证信息
        String token = header.split(" ")[1];
        String username = JwtUtil.getUsername(token);
        UserDetails user = null;
        try {
            user = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return null;
        }
        try {
            boolean verify = JwtUtil.verify(token, username, user.getPassword());
            if (verify == false) {
                return null;
            }
        } catch (TokenExpiredException e) {
            logger.error("token过期！");
            return null;
        }
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
