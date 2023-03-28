package com.example.config;

import com.example.handler.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationProvider authProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationFailureHandler authenticationFailureHandler) throws Exception {
        return http.authorizeRequests(authorizeRequest ->
                        authorizeRequest
                                .antMatchers(HttpMethod.GET, "/about", "/login*", "/blocked", "/css/**").permitAll()
                                .antMatchers(HttpMethod.GET, "/info").hasAuthority("VIEW_INFO")
                                .antMatchers(HttpMethod.GET, "/admin").hasAuthority("VIEW_ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .failureHandler(authenticationFailureHandler)
                        .permitAll())
                .logout(formLogout -> formLogout
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/logoutSuccess")
                        .permitAll()
                )
                .build();
    }

    @Bean
    protected DelegatingPasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
}
