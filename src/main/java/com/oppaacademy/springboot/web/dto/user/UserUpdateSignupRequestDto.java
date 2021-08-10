package com.oppaacademy.springboot.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class UserUpdateSignupRequestDto {
    private String user_whatsapp;
    private String user_nickname;
    private String user_gender;

    @Builder
    public UserUpdateSignupRequestDto(String user_whatsapp, String user_nickname, String user_gender) {
        this.user_whatsapp = user_whatsapp;
        this.user_nickname = user_nickname;
        this.user_gender = user_gender;
    }
}
