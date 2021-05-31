package com.example.project_web.config;

import com.example.project_web.security.AuthenticationHandlerImplement;
import com.example.project_web.security.MyAuthEntryPoint;
import com.example.project_web.service.CustomAuthentication;
import com.example.project_web.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder( ) {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Autowired
    private MyAuthEntryPoint myAuthEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// Từ spring security 5 csrf mặc định là "enable()"
                .authorizeRequests()
                .antMatchers("/login","/","/resources/static/**").anonymous()
                .antMatchers( "/resources/admin**").hasAuthority("ADMIN")
                .antMatchers("/index","/admin**").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(new AuthenticationHandlerImplement())// Đăng nhập thành công
                .failureUrl("/login?failed")// Đăng nhập không thành công
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(myAuthEntryPoint) // Chưa đăng nhập
                .accessDeniedPage("/404")// Không có quyền truy cập
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .rememberMe()// Nhớ tài khoản
                .key("secret");
    }


    @Autowired
    @Qualifier("userDetailService")
    private UserDetailService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        CustomAuthentication provider = new CustomAuthentication();
        provider.setUserDetailService(userDetailsService);
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/static/**"); // #3
    }
}
