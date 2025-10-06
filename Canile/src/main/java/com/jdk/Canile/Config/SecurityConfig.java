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
                .csrf().disable() //disabilitare csrf per test
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/index.html").hasRole("ADMIN")
                        .requestMatchers("/cane.html").hasRole("ADMIN")
                        .requestMatchers("/aggiungiPadrone.html").hasRole("ADMIN")
                        .requestMatchers("/indexPadroni.html").hasRole("ADMIN")
                        .requestMatchers("/padrone.html").hasRole("ADMIN")
                        .requestMatchers("/pratiche.html").hasRole("ADMIN")
                        .requestMatchers("/public.html").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/login.html", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html") // tua pagina personalizzata
                        .loginProcessingUrl("/login") // dove il form invia i dati
                        .defaultSuccessUrl("/default", true) // dove reindirizzare dopo login
                        .failureUrl("/login.html?error") // dove reindirizzare in caso di errore
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/logout.html")
                        .permitAll())

                .logout(
                        logout -> logout
                                .logoutSuccessUrl("/logout.html")
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
