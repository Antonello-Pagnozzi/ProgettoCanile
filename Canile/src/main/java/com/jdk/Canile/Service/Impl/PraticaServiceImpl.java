package com.jdk.Canile.Service.Impl;

import com.jdk.Canile.Entity.Cane;
import com.jdk.Canile.Entity.Padrone;
import com.jdk.Canile.Entity.Pratica;
import com.jdk.Canile.Entity.TipoPratica;
import com.jdk.Canile.Repository.CaneRepository;
import com.jdk.Canile.Repository.PadroneRepository;
import com.jdk.Canile.Repository.PraticaRepository;
import com.jdk.Canile.Service.PraticaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PraticaServiceImpl implements PraticaService {

    private final PraticaRepository praticaRepository;
    private final CaneRepository caneRepository;
    private final PadroneRepository padroneRepository;

    @Autowired
    public PraticaServiceImpl(PraticaRepository praticaRepository, CaneRepository caneRepository, PadroneRepository padroneRepository) {
        this.praticaRepository = praticaRepository;
        this.caneRepository = caneRepository;
        this.padroneRepository = padroneRepository;
    }

    @Override
    public Pratica creaPratica(Pratica pratica) {
        // Controlli preliminari
        if (pratica.getCane() == null) {
            throw new EntityNotFoundException("ERRORE! Nessun cane collegato a questa pratica!");
        }

        Cane canePratica = pratica.getCane();

        // CASO: ADOZIONE
        if (pratica.getTipo() == TipoPratica.ADOZIONE) {
            // Recupero il cane dal DB
            Cane caneDb = caneRepository.findById(canePratica.getId())
                    .orElseThrow(() -> new EntityNotFoundException("ERRORE! Nessun cane con id " + canePratica.getId()));

            caneDb.setAdottato(true);

            Padrone padronePratica = pratica.getPadrone();
            if (padronePratica == null) {
                throw new EntityNotFoundException("ERRORE! Nessun padrone collegato alla pratica!");
            }

            // Se è un nuovo padrone, lo salvo prima
            if (padronePratica.getId() == null) {
                // Assicurati che i campi obbligatori siano valorizzati
                if (padronePratica.getNomeCognome() == null || padronePratica.getCodiceFiscale() == null) {
                    throw new IllegalArgumentException("ERRORE! I campi obbligatori del nuovo padrone non sono completi");
                }
                padroneRepository.save(padronePratica);
            } else {
                // Carico il padrone esistente dal DB
                padronePratica = padroneRepository.findById(padronePratica.getId())
                        .orElseThrow(() -> new EntityNotFoundException("ERRORE! Nessun padrone con id " + pratica.getPadrone().getId()));
            }

            // Associo il cane al padrone e aggiorno la lista dei cani
            caneDb.setPadrone(padronePratica);
            if (padronePratica.getCani() == null) {
                padronePratica.setCani(new ArrayList<>());
            }
            if (!padronePratica.getCani().contains(caneDb)) {
                padronePratica.getCani().add(caneDb);
            }

            // Salvataggi finali
            padroneRepository.save(padronePratica);
            caneRepository.save(caneDb);
            pratica.setCane(caneDb);
            pratica.setPadrone(padronePratica);

        }
        // CASO: RESTITUZIONE
        else if (pratica.getTipo() == TipoPratica.RESTITUZIONE) {
            Cane caneDb = caneRepository.findById(canePratica.getId())
                    .orElseThrow(() -> new EntityNotFoundException("ERRORE! Nessun cane con id " + canePratica.getId()));

            Padrone padroneDb = Optional.ofNullable(pratica.getPadrone())
                    .flatMap(p -> padroneRepository.findById(p.getId()))
                    .orElseThrow(() -> new EntityNotFoundException("ERRORE! Nessun padrone con id " + pratica.getPadrone().getId()));

            caneDb.setAdottato(false);
            caneDb.setPadrone(null);

            if (padroneDb.getCani() != null) {
                padroneDb.getCani().remove(caneDb);
            }

            caneRepository.save(caneDb);
            padroneRepository.save(padroneDb);
            pratica.setCane(caneDb);
            pratica.setPadrone(padroneDb);
        }
        // CASO: RITROVAMENTO
        else if (pratica.getTipo() == TipoPratica.RITROVAMENTO) {
            Cane nuovoCane = new Cane();
            nuovoCane.setNome(canePratica.getNome());
            nuovoCane.setRazza(canePratica.getRazza());
            nuovoCane.setTaglia(canePratica.getTaglia());
            nuovoCane.setSesso(canePratica.getSesso());
            nuovoCane.setAdottato(false);
            nuovoCane.setPadrone(null);

            caneRepository.save(nuovoCane);
            pratica.setCane(nuovoCane);
            pratica.setPadrone(null);
        }
        else {
            throw new IllegalArgumentException("ERRORE! Tipo Pratica non valido!");
        }

        // Imposta la data della pratica
        pratica.setData(LocalDate.now());

        // Salvo la pratica
        return praticaRepository.save(pratica);
    }


    @Override
    public List<Pratica> trovaTutte() {
        return praticaRepository.findAll();
    }

    @Override
    public List<Pratica> trovaPerCaneId(Long caneId) {
        Optional<Cane> cane = caneRepository.findCaneById(caneId);
        if (cane.isEmpty()){
            throw new EntityNotFoundException("ERRORE! Non esiste un cane con questo id!");
        }
        return praticaRepository.findByCaneId(caneId);
    }

    @Override
    public List<Pratica> trovaPerPadroneId(Long padroneId) {
        Optional<Padrone> padrone = padroneRepository.findById(padroneId);
        if (padrone.isEmpty()){
            throw new EntityNotFoundException("ERRORE! Non esiste un padrone con questo id!");
        }
        return praticaRepository.findByPadroneId(padroneId);
    }

    //Correggere domani
    @Override
    public Pratica aggiornaPratica(Long id, Pratica praticaAggiornata) {
        Optional<Pratica> optPratica = praticaRepository.findById(id);
        if (optPratica.isPresent()) {
            Pratica pratica = optPratica.get();

            Padrone padrone = padroneRepository.findById(praticaAggiornata.getPadrone().getId())
                    .orElseThrow(() -> new EntityNotFoundException("ERRORE! nessun padrone con questo ID!"));
            Cane cane = caneRepository.findById(praticaAggiornata.getCane().getId())
                    .orElseThrow(() -> new EntityNotFoundException("ERRORE! nessun cane con questo ID!"));

            pratica.setTipo(praticaAggiornata.getTipo());
            pratica.setPadrone(praticaAggiornata.getPadrone());
            pratica.setData(praticaAggiornata.getData());

            // Aggiorna lo stato del cane se serve
            if (pratica.getTipo() == TipoPratica.ADOZIONE) {
                cane.setAdottato(true);
                cane.setPadrone(pratica.getPadrone());
                List<Cane> caniPadrone = padrone.getCani();
                caniPadrone.add(cane);
                padrone.setCani(caniPadrone);
                caneRepository.save(cane);
                padroneRepository.save(padrone);
            } else if (pratica.getTipo() == TipoPratica.RESTITUZIONE) {
                cane.setAdottato(false);
                cane.setPadrone(null);
                List<Cane> caniPadrone = padrone.getCani();
                caniPadrone.remove(cane);
                padrone.setCani(caniPadrone);
                caneRepository.save(cane);
                padroneRepository.save(padrone);
            }
            pratica.setData(LocalDate.now());
            return praticaRepository.save(pratica);
        } else {
            throw new RuntimeException("Pratica non trovata con id " + id);
        }
    }

    @Override
    public Optional<Pratica> getPraticaById(Long id) {
        Optional<Pratica> pratica = praticaRepository.findById(id);
        if (pratica.isEmpty()){
            throw new EntityNotFoundException("ERRORE! Nessuna pratica presente con id "+ id);
        }
        return pratica;
    }

}
