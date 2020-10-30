package com.luv2code.springsecurity.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(users.username("user1").password("1111").roles("EMPLOYEE"))
                .withUser(users.username("user2").password("1111").roles("EMPLOYEE","MANAGER"))
                .withUser(users.username("user3").password("1111").roles("EMPLOYEE","MANAGER","DIRECTOR"))
                .withUser(users.username("user4").password("1111").roles("EMPLOYEE","MANAGER","DIRECTOR","ADMIN"));
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/showLoginPage").permitAll()
                .antMatchers("/systems/**").hasRole("ADMIN")
                .antMatchers("/leaders/**").hasRole("MANAGER")
                .antMatchers("/directors/**").hasRole("DIRECTOR")
                .and()
                .formLogin()
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");

    }

}
