package com.oppaacademy.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE user_email = ?1 AND user_openid = ?2")
    Optional<User> findByEmailOpenId(String user_email, String user_openid);
}
