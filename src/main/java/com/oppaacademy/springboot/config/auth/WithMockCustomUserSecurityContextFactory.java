package com.oppaacademy.springboot.config.auth;

import com.oppaacademy.springboot.domain.user.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.*;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", annotation.userName());
        attributes.put("email", annotation.userEmail());

        Collection<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(Role.GUEST.getKey()));
        OAuth2User user = new DefaultOAuth2User(authorities, attributes, "id");
        securityContext.setAuthentication(new OAuth2AuthenticationToken(user, authorities, "client-registration-id"));

        return securityContext;
    }
}
