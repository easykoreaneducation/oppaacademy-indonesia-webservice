package com.oppaacademy.springboot.web;

import com.oppaacademy.springboot.config.auth.LoginUser;
import com.oppaacademy.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping(value = "/")
    public String mainPage(HttpServletRequest request, Model model, @LoginUser SessionUser sessionUser) {

        if(sessionUser != null) {
            model.addAttribute("userName", sessionUser.getUser_name());
        }

        return "index";
    }

    @GetMapping(value = "/test")
    public String testPage(@LoginUser SessionUser sessionUser,
                           Model model) {

        if(sessionUser != null) {
            model.addAttribute("userName", sessionUser.getUser_name());
        }
        return "test";
    }

    /* 로그인 화면 */
    @GetMapping(value = "/oauth2/login")
    public String oauth2Login(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam(value = "return_to", required = false) String returnTo) {

        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("previousUrl");

        if(returnTo != null && !returnTo.isEmpty()) {
            httpSession.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
            httpSession.setAttribute("previousUrl", returnTo);
        }

        return "member/login";
    }

    /* 신규 회원 기입 */
    @GetMapping(value = "/oauth2/signup")
    public String oauth2SignUp() {

        return "member/signup";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/quiz/level-test")
    public String quizLevelTest() {

        return "quiz/leveltest";
    }

}
