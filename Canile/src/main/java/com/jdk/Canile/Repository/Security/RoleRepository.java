package com.jdk.Canile.Repository.Security;

import com.jdk.Canile.Model.Role;
import com.jdk.Canile.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Long> findByName(String name);
}
