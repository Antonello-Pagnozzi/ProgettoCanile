package com.jdk.Canile.Repository;

import com.jdk.Canile.Entity.Cane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CaneRepository extends JpaRepository<Cane, Long> {

    List<Cane> findByAdottato(boolean stato);
    Optional<Cane> findCaneById(Long id);
    // Eager fetch della relazione padrone
    @Query("SELECT c FROM Cane c LEFT JOIN FETCH c.padrone WHERE c.id = :id")
    Optional<Cane> findByIdConPadrone(@Param("id") Long id);

}
