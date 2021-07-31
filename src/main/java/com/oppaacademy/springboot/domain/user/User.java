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

    @Column
    private String user_phone;

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

    public String getUserRoleKey() {
        return this.user_role.getKey();
    }
}
