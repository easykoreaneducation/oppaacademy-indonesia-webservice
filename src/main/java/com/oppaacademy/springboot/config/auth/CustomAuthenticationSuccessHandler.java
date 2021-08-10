package com.oppaacademy.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final HttpSession httpSession;
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String previousUrl = (String) httpSession.getAttribute("previousUrl");
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        boolean isGuest = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority() == "ROLE_GUEST") ? true : false;

        if(previousUrl != null && !previousUrl.isEmpty()) httpSession.removeAttribute("previousUrl");

        if(isGuest) {
            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString(), guestTargetUrl;

            StringBuilder targetStringBuilder = new StringBuilder();
            targetStringBuilder.append(baseUrl);
            targetStringBuilder.append("/oauth2/signup?return_to=");

            if(savedRequest == null) {
                if(previousUrl == null || previousUrl.isEmpty()) {
                    targetStringBuilder.append(URLEncoder.encode(baseUrl, StandardCharsets.UTF_8.toString()));
                } else {
                    targetStringBuilder.append(URLEncoder.encode(previousUrl, StandardCharsets.UTF_8.toString()));
                }
            } else {
                targetStringBuilder.append(URLEncoder.encode(savedRequest.getRedirectUrl(), StandardCharsets.UTF_8.toString()));
                httpSession.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
            }

            clearAuthenticationAttributes(request);
            getRedirectStrategy().sendRedirect(request, response, targetStringBuilder.toString());

            return;
        }


        if(savedRequest == null) {
            if(previousUrl == null || previousUrl.isEmpty()) {
                super.onAuthenticationSuccess(request, response, authentication);
            } else {
                clearAuthenticationAttributes(request);
                getRedirectStrategy().sendRedirect(request, response, previousUrl);

            }

            return;
        }

        clearAuthenticationAttributes(request);
        String targetUrl = savedRequest.getRedirectUrl();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }
}
