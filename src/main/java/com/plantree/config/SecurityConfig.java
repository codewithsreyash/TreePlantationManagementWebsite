package com.plantree.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // In-memory authentication requested in Gap 1
        auth.inMemoryAuthentication()
            .passwordEncoder(NoOpPasswordEncoder.getInstance())
            .withUser("admin")
            .password("admin123")
            .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for JSF compatibility
            .authorizeRequests()
                .antMatchers("/admin.xhtml", "/admin/*", "/drives/*").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
            .and()
            .formLogin()
                .loginPage("/login.xhtml")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin.xhtml", true)
                .failureUrl("/login.xhtml?error=true")
                .permitAll()
            .and()
            .logout()
                .logoutSuccessUrl("/index.xhtml")
                .permitAll()
            .and()
            .headers()
                .frameOptions().sameOrigin();
    }
}
