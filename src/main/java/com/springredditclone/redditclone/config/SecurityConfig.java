package com.springredditclone.redditclone.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private  final  String []  PublicEndPoints ={
            "/api/auth/**"
    };

    // whenever we autowired the authentication manager spring find this bean and inject it to our class
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean () throws Exception {
        return  super.authenticationManagerBean();
    }

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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
         authenticationManagerBuilder.userDetailsService(userDetailsService)
                 .passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
