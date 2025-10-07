package com.jdk.Canile.Init;

import com.jdk.Canile.Model.Role;
import com.jdk.Canile.Model.User;
import com.jdk.Canile.Repository.Security.RoleRepository;
import com.jdk.Canile.Repository.Security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        return args -> {
            // Crea ruoli se non esistono
            Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new Role("ROLE_ADMIN")));
            Role userRole = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(new Role("ROLE_USER")));

            // Crea utenti di test
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin"));
                admin.setEmail("admin@admin.it");
                admin.getRoles().add(adminRole);
                userRepo.save(admin);
            }

            if (userRepo.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(encoder.encode("user"));
                user.setEmail("user@user.it");
                user.getRoles().add(userRole);
                userRepo.save(user);
            }
            System.out.println("Utenti di test creati.");
        };
    }
}
