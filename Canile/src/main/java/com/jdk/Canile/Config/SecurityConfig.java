package com.jdk.Canile.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable() // disabilitato per test

                // Configurazione autorizzazioni
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/index.html",
                                "/cane.html",
                                "/aggiungiPadrone.html",
                                "/indexPadroni.html",
                                "/padrone.html",
                                "/pratiche.html"
                        ).hasRole("ADMIN")
                        .requestMatchers("/public.html").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/login.html",
                                                    "/auth/register",
                                                    "/register.html",
                                                    "/css/**", "/js/**").permitAll()


                        .anyRequest().authenticated()
                )

                // Gestione accesso negato
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403.html")
                )

                // Login personalizzato
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/default", true)
                        .failureUrl("/login.html?error")
                        .permitAll()
                )

                // Logout con redirect al login con messaggio
                .logout(logout -> logout
                        .logoutUrl("/logout")               // endpoint POST
                        .logoutSuccessUrl("/login.html?logout") // redirect dopo logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
