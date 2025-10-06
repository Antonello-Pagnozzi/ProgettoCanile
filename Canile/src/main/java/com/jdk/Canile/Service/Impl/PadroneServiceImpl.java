package com.jdk.Canile.Service.Impl;

import com.jdk.Canile.Entity.Cane;
import com.jdk.Canile.Entity.Padrone;
import com.jdk.Canile.Repository.CaneRepository;
import com.jdk.Canile.Repository.PadroneRepository;
import com.jdk.Canile.Service.PadroneService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PadroneServiceImpl implements PadroneService {

    @Autowired
    private PadroneRepository padroneRepository;

    @Autowired
    private CaneRepository caneRepository;

    public PadroneServiceImpl(PadroneRepository padroneRepository, CaneRepository caneRepository) {
        this.padroneRepository = padroneRepository;
        this.caneRepository = caneRepository;
    }

    @Override
    public Padrone salvaPadrone(Padrone padrone) {
        return padroneRepository.save(padrone);
    }

    @Override
    public Optional<Padrone> trovaPerId(Long id) {
        Optional<Padrone> trovato = padroneRepository.findById(id);
        if (trovato.isEmpty()){
            throw new EntityNotFoundException("Errore! Nessun padrone con id: "+ id + " trovato!");
        }
        return trovato;
    }

    @Override
    public List<Padrone> trovaTutti() {
        return padroneRepository.findAll();
    }

    @Override
    public void cancellaPadroneById(Long id){
        Padrone padrone = padroneRepository.findById(id).get();
        //sleghiamo i cani dall'entità padrone
        for (Cane cane: padrone.getCani()){
            cane.setPadrone(null);
            cane.setAdottato(false);
        }
        //cancelliamo il padrone
        padroneRepository.deleteById(id);

    }

    @Override
    public Padrone aggiungiCane(Long idPadrone, Long idCane) {
        Padrone padrone = padroneRepository.findById(idPadrone).get();
        Cane cane = caneRepository.findById(idCane).get();

        List<Cane> caniPadrone = padrone.getCani();
        caniPadrone.add(cane);
        cane.setAdottato(true);
        cane.setPadrone(padrone);
        padrone.setCani(caniPadrone);

        padroneRepository.save(padrone);
        caneRepository.save(cane);

        return padrone;
    }

    @Override
    public Padrone rimuoviCane(Long idPadrone, Long idCane) {
        Padrone padrone = padroneRepository.findById(idPadrone).get();
        Cane cane = caneRepository.findById(idCane).get();

        List<Cane> caniPadrone = padrone.getCani();
        caniPadrone.remove(cane);
        cane.setAdottato(false);
        cane.setPadrone(null);
        padrone.setCani(caniPadrone);

        padroneRepository.save(padrone);
        caneRepository.save(cane);

        return padrone;
    }

    public Padrone findPadroneById(Long id){
        Optional<Padrone> padrone = padroneRepository.findById(id);
        if (padrone.isEmpty()){
            throw new EntityNotFoundException("Errore! Nessun padrone con id: "+ id + " trovato!");
        }
        return padrone.get();
    }

}