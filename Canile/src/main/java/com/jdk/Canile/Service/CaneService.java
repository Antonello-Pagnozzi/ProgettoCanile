package com.jdk.Canile.Service;

import com.jdk.Canile.Entity.Cane;

import java.util.List;
import java.util.Optional;

public interface CaneService {

    Cane salvaCane(Cane cane);
    List<Cane> trovaTutti();
    //Cane trovaPerId(Long id);
    void eliminaCane(Long id);
    List<Cane> findByAdottato(boolean stato);
    Cane aggiornaCane(Cane cane);
    Optional<Cane> findCaneById(Long id);
    List<Cane> findByName(String nome);
    Cane findByIdConPadrone(Long id);
    List<Cane> findByAdottatoFalse();
}
