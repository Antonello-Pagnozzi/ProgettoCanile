package com.jdk.Canile.Config;

import com.jdk.Canile.Service.Security.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collection;

@Configuration
public class SecurityConfig {

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

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
                //parte per OAuth2
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // se hai già definito questo bean
                        )
                        .successHandler(oauth2SuccessHandler())
                )
                // Logout con redirect al login con messaggio
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html?logout=true")
                        .invalidateHttpSession(true)       // distrugge la sessione lato server
                        .deleteCookies("JSESSIONID")       // rimuove il cookie di sessione dal browser
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Handler per login OAuth2

    @Bean
    public AuthenticationSuccessHandler oauth2SuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/index.html");
            } else {
                response.sendRedirect("/public.html");
            }
        };
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
