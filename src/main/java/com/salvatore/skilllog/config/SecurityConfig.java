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
                // Swagger e OpenAPI sempre pubblici
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // Utenti
                .requestMatchers("/api/utenti/**").hasRole("ADMIN")
                .requestMatchers("/api/utenti/username/**").hasAnyRole("ADMIN", "USER")

                // Skills
                .requestMatchers("/api/skills/search", "/api/skills/test").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/skills/export").hasRole("ADMIN")
                .requestMatchers("/api/skills/**").hasRole("ADMIN")

                // Progetti
                .requestMatchers("/api/progetti/stato/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/progetti/**").hasRole("ADMIN")

                // AI
                .requestMatchers("/api/ai/message").hasAnyRole("ADMIN", "USER")

                // Stats
                .requestMatchers("/api/stats").hasRole("ADMIN")

                // Tutto il resto richiede autenticazione
                .anyRequest().authenticated()
            )
            .userDetailsService(userDetailsService)
            .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
