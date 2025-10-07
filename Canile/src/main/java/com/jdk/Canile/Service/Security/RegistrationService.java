package com.jdk.Canile.Service.Security;

import com.jdk.Canile.Entity.Security.Role;
import com.jdk.Canile.Entity.Security.User;
import com.jdk.Canile.Repository.Security.RoleRepository;
import com.jdk.Canile.Repository.Security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder BCryptPasswordEncoder;

    public User registerUser(String username, String rawPassword, String email) {
        //verifica se lo username e la mail esistono già
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username già esistente");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email già esistente");
        }
        //crea o prende ruolo se già esistente
        Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        //crea nuovo user e setta parametri
        User user = new User();
        user.setUsername(username);
        user.setPassword(BCryptPasswordEncoder.encode(rawPassword));
        user.setEmail(email);
        user.getRoles().add(userRole); // di default

        return userRepository.save(user);
    }
}
