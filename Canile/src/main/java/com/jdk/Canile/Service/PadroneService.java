package com.jdk.Canile.Service;

import com.jdk.Canile.Entity.Padrone;

import java.util.List;
import java.util.Optional;

public interface PadroneService {

    Padrone salvaPadrone(Padrone padrone);
    Optional<Padrone> trovaPerId(Long id);
    List<Padrone> trovaTutti();
    void cancellaPadroneById(Long id);
    Padrone aggiungiCane(Long idPadrone, Long idCane);
    Padrone rimuoviCane(Long idPadrone, Long idCane);
    //Padrone findPadroneById(Long id);

}
