package com.ala.report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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
                .requestMatchers("/css/**", "/js/**").permitAll()  // Izinkan akses ke resources
                .requestMatchers("/report").hasAnyRole("EMPLOYEE", "ADMIN") // Izinkan EMPLOYEE dan ADMIN
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login") // Halaman login kustom (opsional)
                .defaultSuccessUrl("/report") // Redirect setelah login berhasil
                .permitAll())
            .logout(logout -> logout
                .permitAll())
            .csrf(csrf -> csrf.disable()); // Nonaktifkan CSRF untuk testing
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails employee = User.withUsername("employee")
            .password("password")
            .roles("EMPLOYEE")
            .build();
        UserDetails tom = User.withUsername("Tom")
            .password("password")
            .roles("EMPLOYEE")
            .build();
        UserDetails admin = User.withUsername("admin")
            .password("admin")
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(employee, admin, tom);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}