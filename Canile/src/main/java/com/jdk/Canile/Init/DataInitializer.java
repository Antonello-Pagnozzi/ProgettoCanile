package com.jdk.Canile.Init;

import com.jdk.Canile.Model.Role;
import com.jdk.Canile.Model.User;
import com.jdk.Canile.Repository.Security.RoleRepository;
import com.jdk.Canile.Repository.Security.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        return args -> {
            if (roleRepo.findByName("ROLE_ADMIN").isEmpty()) {
                Role adminRole = roleRepo.save(new Role("ROLE_ADMIN"));
                Role userRole = roleRepo.save(new Role("ROLE_USER"));

                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("adminpass"));
                admin.getRoles().add(adminRole);
                userRepo.save(admin);

                User user = new User();
                user.setUsername("user");
                user.setPassword(encoder.encode("userpass"));
                user.getRoles().add(userRole);
                userRepo.save(user);

                System.out.println("Utenti e ruoli iniziali creati.");
            }
        };
    }
}
