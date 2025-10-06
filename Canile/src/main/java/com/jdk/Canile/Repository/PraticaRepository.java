package com.jdk.Canile.Repository;

import com.jdk.Canile.Entity.Pratica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PraticaRepository extends JpaRepository<Pratica, Long> {
    List<Pratica> findByCaneId(Long caneId);
    List<Pratica> findByPadroneId(Long padroneId);
}
