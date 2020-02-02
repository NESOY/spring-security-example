package com.nesoy.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .mvcMatchers("/", "info", "/account/**").permitAll()
               .mvcMatchers("/admin").hasRole("ADMIN")
               .anyRequest().authenticated();
       http.formLogin();
       http.httpBasic();
    }

    /**
     * In-Memory 저장
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        /**
//         * {암호화 Prefix}
//         */
//        auth.inMemoryAuthentication()
//                .withUser("nesoy").password("{noop}123").roles("USER").and()
//                .withUser("admin").password("{noop}!@#").roles("ADMIN");
//    }
}
