package com.example.bankapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration.
 */
@Configuration
@EnableWebSecurity
public final class SecurityConfig {

    /**
     * Configures the security filter chain.
     *
     * @param http the HTTP security configuration
     * @return configured security filter chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            final HttpSecurity http)
            throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/register",
                    "/login",
                    "/css/**",
                    "/js/**",
                    "/actuator/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl(
                    "/dashboard",
                    true
                )
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    /**
     * Creates the password encoder bean.
     *
     * @return password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
