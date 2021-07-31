package com.oppaacademy.springboot.config.auth.dto;

import com.oppaacademy.springboot.domain.user.Role;
import com.oppaacademy.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String user_name;
    private String user_email;
    private String user_picture;
    private Role user_role;

    public SessionUser(User user) {
        this.user_name = user.getUser_name();
        this.user_email = user.getUser_email();
        this.user_picture = user.getUser_picture();
        this.user_role = user.getUser_role();
    }
}
