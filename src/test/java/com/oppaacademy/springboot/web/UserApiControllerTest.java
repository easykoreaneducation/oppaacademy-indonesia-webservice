package com.oppaacademy.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oppaacademy.springboot.config.auth.WithMockCustomUser;
import com.oppaacademy.springboot.domain.user.Role;
import com.oppaacademy.springboot.domain.user.User;
import com.oppaacademy.springboot.domain.user.UserRepository;
import com.oppaacademy.springboot.web.dto.user.UserUpdateSignupRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {

    @LocalServerPort
    private int randomPort;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @WithMockCustomUser
    @Test
    public void user_signup() throws Exception {
        //given
        String user_whatsapp = "012345678901";
        String user_nickname = "nickname";
        String user_gender = "M";

        userRepository.save(User.builder()
                .user_openid("test")
                .user_email("test@test.com")
                .user_name("test")
                .user_picture("")
                .user_role(Role.GUEST)
                .build());

        UserUpdateSignupRequestDto requestDto = UserUpdateSignupRequestDto.builder()
                .user_whatsapp(user_whatsapp)
                .user_nickname(user_nickname)
                .user_gender(user_gender)
                .build();

        String url = "http://localhost:" + randomPort + "/api/v1/user/signup";

        //when
        mockMvc.perform(put(url)
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<User> userList =  userRepository.findAll();
        User user = userList.get(0);

        //
        assertEquals(user.getUser_whatsapp(), user_whatsapp);
        assertEquals(user.getUser_nickname(), user_nickname);
        assertEquals(user.getUser_gender(), user_gender);
        assertEquals(user.getUser_role(), Role.USER);

    }

}
