package com.instagram.numble_instagram.config.security;

import com.instagram.numble_instagram.util.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException)
            throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now().toString());
        map.put("status", 403);
        map.put("message", "Access denied");
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        res.getWriter().write(JsonUtil.objectToJsonStr(map));
    }
}
