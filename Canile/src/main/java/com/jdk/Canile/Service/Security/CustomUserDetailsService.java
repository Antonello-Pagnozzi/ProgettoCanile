package com.jdk.Canile.Service.Security;

import com.jdk.Canile.Entity.Security.User;
import com.jdk.Canile.Repository.Security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Tentativo di login per: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("Utente non trovato: " + username);
                    return new UsernameNotFoundException("User not found");
                });

        System.out.println("Utente trovato: " + user.getUsername());
        System.out.println("Password criptata: " + user.getPassword());
        System.out.println("Ruoli associati: " + user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.joining(", ")));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}