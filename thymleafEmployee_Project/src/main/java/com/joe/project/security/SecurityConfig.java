package com.joe.project.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(users.username("Ahmed").password("123").roles("ADMIN", "EMPLOYEE"))
                .withUser(users.username("Yusuf").password("456").roles("MANAGER", "EMPLOYEE"))
                .withUser(users.username("Ali").password("789").roles("EMPLOYEE"));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                .antMatchers("/employees/show*").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/employees/post*").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/employees/delete").hasRole("ADMIN")
                .antMatchers("/employees/**").hasRole("EMPLOYEE")
                .antMatchers("/resources/**").permitAll()

                .and()

                .formLogin()
                .loginPage("/loginAuthPage")
                .failureUrl("/loginAuthPage")
                .loginProcessingUrl("/authenticateTheUser").permitAll()


                .and()

                .logout().permitAll()

                .and()

                .exceptionHandling().accessDeniedPage("/accessDenied");


    }
}
