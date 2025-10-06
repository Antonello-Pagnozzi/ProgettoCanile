package com.jdk.Canile.Service;

import com.jdk.Canile.Entity.Pratica;

import java.util.List;
import java.util.Optional;

public interface PraticaService {

    Pratica creaPratica(Pratica pratica);
    List<Pratica> trovaTutte();
    List<Pratica> trovaPerCaneId(Long caneId);
    List<Pratica> trovaPerPadroneId(Long padroneId);
    Pratica aggiornaPratica(Long id, Pratica praticaAggiornata);
    Optional<Pratica> getPraticaById(Long id);
}
