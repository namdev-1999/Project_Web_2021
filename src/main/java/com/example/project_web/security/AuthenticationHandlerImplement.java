package com.example.project_web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationHandlerImplement implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if (httpServletResponse.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        List<String> roles = new ArrayList<>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities)
            roles.add(grantedAuthority.getAuthority());

        if (roles.contains("ADMIN")) {
            return "/index";
        } else if (roles.contains("USER")) {
            return "/";
        }
        return "/";
    }
}
