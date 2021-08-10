package com.oppaacademy.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .ignoringAntMatchers("/h2-console/**")
            .and()
                .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/oauth2/signup").hasRole("GUEST")
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/scss/**", "/bootstrap/**", "/oauth2/login/**", "/h2-console/**", "/profile", "/test").permitAll()
                .anyRequest().authenticated()
            .and()
                .logout()
                    .logoutUrl("/oauth2/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                    .loginPage("/oauth2/login/")
                    .defaultSuccessUrl("/")
                    .successHandler(customAuthenticationSuccessHandler)
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService);
    }
}
