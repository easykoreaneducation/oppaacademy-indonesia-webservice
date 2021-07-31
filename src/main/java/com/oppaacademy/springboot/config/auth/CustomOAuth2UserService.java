package com.oppaacademy.springboot.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oppaacademy.springboot.config.auth.dto.OAuthAttributes;
import com.oppaacademy.springboot.config.auth.dto.SessionUser;
import com.oppaacademy.springboot.domain.user.User;
import com.oppaacademy.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

//        log.info(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userRequest));
//        log.info(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(oAuth2User));

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getUserRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmailOpenId(attributes.getAttributeEmail(), attributes.getAttributeOpenid())
                .map(entity -> entity.update(attributes.getAttributeName(), attributes.getAttributePicture()))
                //.orElseThrow(() -> new UsernameNotFoundException(attributes.getAttributeName()));
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }



}
