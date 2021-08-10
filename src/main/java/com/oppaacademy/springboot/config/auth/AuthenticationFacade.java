package com.oppaacademy.springboot.config.auth;

import com.oppaacademy.springboot.config.auth.dto.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public AuthenticatedUser getPrincipal() {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return AuthenticatedUser.builder()
                .email(oAuth2User.getAttribute("email"))
                .openId(oAuth2User.getName())
                .build();
    }

    @Override
    public void reloadAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        OAuth2User principal = ((OAuth2AuthenticationToken)authentication).getPrincipal();

        Authentication newAuthentication = new OAuth2AuthenticationToken(principal, authorities, (((OAuth2AuthenticationToken)authentication).getAuthorizedClientRegistrationId()));
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }
}
