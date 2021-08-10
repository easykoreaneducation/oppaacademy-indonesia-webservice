package com.oppaacademy.springboot.config.auth;

import com.oppaacademy.springboot.config.auth.dto.AuthenticatedUser;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    AuthenticatedUser getPrincipal();
    void reloadAuthorities();
}
