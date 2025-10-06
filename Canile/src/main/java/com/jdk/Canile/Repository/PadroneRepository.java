package com.jdk.Canile.Repository;

import com.jdk.Canile.Entity.Padrone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PadroneRepository extends JpaRepository<Padrone, Long> {
    //Padrone findPadroneById(Long id);
}
