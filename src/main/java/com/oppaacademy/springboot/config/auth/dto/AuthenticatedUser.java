package com.oppaacademy.springboot.config.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AuthenticatedUser {
    private String openId;
    private String email;

    @Builder
    public AuthenticatedUser(String openId, String email) {
        this.openId = openId;
        this.email = email;
    }
}
