package com.ala.report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**").permitAll()  
                .requestMatchers("/report").hasAnyRole("EMPLOYEE", "ADMIN") 
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/report") 
                .permitAll())
            .logout(logout -> logout.permitAll())
            .csrf(csrf -> csrf.disable()); 
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
            createUser("employee", "password", "EMPLOYEE"),
            createUser("Tom", "password", "EMPLOYEE"),
            createUser("admin", "admin", "ADMIN")
        );
    }

    private UserDetails createUser(String username, String rawPassword, String role) {
        return User.withUsername(username)
            .password(passwordEncoder().encode(rawPassword)) // Enkripsi password
            .roles(role)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}