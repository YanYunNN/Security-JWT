package com.cloume.jwtsecurity.exception;

import com.cloume.commons.rest.response.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by echisan on 2018/6/24
 * @description:没有携带token或者token无效
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        response.getWriter().write(new ObjectMapper().writeValueAsString(RestResponse.bad(HttpServletResponse.SC_FORBIDDEN, "当前用户没有携带token或者token无效")));
    }
}
