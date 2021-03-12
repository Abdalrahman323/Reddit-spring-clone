package com.springredditclone.redditclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private  final  String []  PublicEndPoints ={
            "/api/auth/**"
    };

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        // we are disabling it because csrf attack can mainly occur
        // when there is sessions and when we are using cookies to authenticate the
        // session information
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(PublicEndPoints)
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
