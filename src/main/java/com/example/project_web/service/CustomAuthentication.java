package com.example.project_web.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class CustomAuthentication implements AuthenticationProvider {


    UserDetailsService userDetailService;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        //
        try {
            // sử dụng userDetailService để lấy thông tin user
            UserDetails user = userDetailService.loadUserByUsername(a.getName());
            // logic xac thuc user
            UsernamePasswordAuthenticationToken result = null;
            if (user.getUsername().equals(a.getName()) && user.getPassword().equals(a.getCredentials().toString())) {
                result = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),user.getAuthorities());
            }
            return result;
        } catch (UsernameNotFoundException e) {
            throw e;
        }
    }

    public void setUserDetailService(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
}
