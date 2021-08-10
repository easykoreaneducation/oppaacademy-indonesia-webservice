package com.oppaacademy.springboot.web;

import com.oppaacademy.springboot.service.UserService;
import com.oppaacademy.springboot.web.dto.user.UserUpdateSignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PutMapping("/api/v1/user/signup")
    public void signup(@RequestBody UserUpdateSignupRequestDto requestDto) {
        userService.updateSignup(requestDto);
    }

}
