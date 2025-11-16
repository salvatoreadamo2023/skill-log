package com.salvatore.skilllog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.salvatore.skilllog.service.CustomUserDetailsService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/api/utenti/**").hasRole("ADMIN")
                .requestMatchers("/api/utenti/username/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/skills/search", "/api/skills/test").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/skills/export").hasRole("ADMIN")
                .requestMatchers("/api/skills/**").hasRole("ADMIN")
                .requestMatchers("/api/progetti/stato/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/progetti/**").hasRole("ADMIN")
                .requestMatchers("/api/ai/message").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/stats").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .userDetailsService(userDetailsService)
            // login DB via Basic Auth
            .httpBasic()
            // login Google via OAuth2
            .and()
            .oauth2Login(oauth -> oauth
                .loginPage("/oauth2/authorization/google") // endpoint per avviare il flusso Google
                .defaultSuccessUrl("/api/auth/me", true)   // dopo login, ritorna al backend
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
