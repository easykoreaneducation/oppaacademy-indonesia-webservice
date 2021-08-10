package com.oppaacademy.springboot.domain.user;

import com.oppaacademy.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false)
    private String user_name;

    @Column(nullable = false)
    private String user_email;

    @Column(nullable = false)
    private String user_openid;

    @Column
    private String user_picture;

    @Column(length = 13)
    private String user_whatsapp;

    @Column(length = 10)
    private String user_nickname;

    @Column(length = 1)
    private String user_gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role user_role;

    @Builder
    public User(String user_name, String user_email, String user_picture, String user_openid, Role user_role) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_openid = user_openid;
        this.user_picture = user_picture;
        this.user_role = user_role;
    }

    public User update(String user_name, String user_picture) {
        this.user_name = user_name;
        this.user_picture = user_picture;

        return this;
    }

    public void updateSignup(String user_whatsapp, String user_nickname, String user_gender, Role user_role) {
        this.user_whatsapp = user_whatsapp;
        this.user_nickname = user_nickname;
        this.user_gender = user_gender;
        this.user_role = user_role;
    }

    public String getUserRoleKey() {
        return this.user_role.getKey();
    }
}
