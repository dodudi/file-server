package com.rudy.file.response;

import lombok.Getter;

import java.util.Set;

@Getter
public class JwtResponse {
    private final Boolean isValidated;
    private final Integer userId;
    private final String username;
    private final Set<String> userRoles;

    public JwtResponse(Boolean isValidated, Integer userId, String username, Set<String> userRoles) {
        this.isValidated = isValidated;
        this.userId = userId;
        this.username = username;
        this.userRoles = userRoles;
    }
}
