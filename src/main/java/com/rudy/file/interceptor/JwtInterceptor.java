package com.rudy.file.interceptor;

import com.rudy.file.response.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${url.auth-server}")
    private String authServerUrl;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.replace("Bearer ", "");

            JwtResponse jwtResponse = restTemplate.getForObject(authServerUrl + "?token=" + authHeader, JwtResponse.class);
            assert jwtResponse != null;

            if (jwtResponse.getIsValidated()) {
                return true;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Invalid Token");
        return false;
    }
}
