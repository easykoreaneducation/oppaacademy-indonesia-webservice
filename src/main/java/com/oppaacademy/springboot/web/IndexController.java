package com.oppaacademy.springboot.web;

import com.oppaacademy.springboot.config.auth.LoginUser;
import com.oppaacademy.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String mainPage(Model model, @LoginUser SessionUser sessionUser) {

        if(sessionUser != null) {
            model.addAttribute("userName", sessionUser.getUser_name());
        }

        return "index";
    }

}
