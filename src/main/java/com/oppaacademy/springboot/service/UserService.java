package com.oppaacademy.springboot.service;

import com.oppaacademy.springboot.config.auth.IAuthenticationFacade;
import com.oppaacademy.springboot.config.auth.dto.AuthenticatedUser;
import com.oppaacademy.springboot.domain.user.Role;
import com.oppaacademy.springboot.domain.user.User;
import com.oppaacademy.springboot.domain.user.UserRepository;
import com.oppaacademy.springboot.web.dto.user.UserUpdateSignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final IAuthenticationFacade iAuthenticationFacade;
    private final UserRepository userRepository;

    @Transactional
    public void updateSignup(UserUpdateSignupRequestDto requestDto) {
        AuthenticatedUser authenticatedUser = iAuthenticationFacade.getPrincipal();

        User user = userRepository.findByEmailOpenId(authenticatedUser.getEmail(), authenticatedUser.getOpenId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid access"));

        user.updateSignup(requestDto.getUser_whatsapp(), requestDto.getUser_nickname(), requestDto.getUser_gender(), Role.USER);
        iAuthenticationFacade.reloadAuthorities();
    }

}
