package com.jdk.Canile.Service.Impl;

import com.jdk.Canile.Entity.Cane;
import com.jdk.Canile.Repository.CaneRepository;
import com.jdk.Canile.Service.CaneService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CaneServiceImpl implements CaneService {
    
    private final CaneRepository caneRepository;

    //Iniezione tramite costruttore
    public CaneServiceImpl(CaneRepository caneRepository) {
        this.caneRepository = caneRepository;
    }

    @Override
    public Cane salvaCane(Cane cane) {
        return caneRepository.save(cane);
    }

    @Override
    public List<Cane> trovaTutti() {
        return caneRepository.findAll();
    }

//    @Override
//    public Cane trovaPerId(Long id) {
//        Optional<Cane> trovato = Optional.ofNullable(caneRepository.findById(id).orElseThrow(
//                () -> new EntityNotFoundException("Cane non trovato con id " + id)));;
//        return trovato.get();
//    }

    @Override
    public void eliminaCane(Long id) {
        Optional<Cane> caneDaCancellare = caneRepository.findById(id);
        if (caneDaCancellare.isEmpty()){
            throw new EntityNotFoundException("Cane non trovato con id " + id + "! impossibile cancellare!");
        }
        caneRepository.deleteById(id);
    }

    @Override
    public List<Cane> findByAdottato(boolean stato) {
        return caneRepository.findByAdottato(stato);
    }

    @Override
    public Cane aggiornaCane(Cane cane) {
        if (cane.getId() == null || !caneRepository.existsById(cane.getId())) {
            throw new EntityNotFoundException("Cane non trovato con id " + cane.getId()+"! Impossibile aggiornare!");
        }
        return caneRepository.save(cane);
    }

    public Optional<Cane> findCaneById(Long id){
        Optional<Cane> cane = caneRepository.findCaneById(id);
        if (cane.isEmpty()){
            throw new EntityNotFoundException("ERRORE! Nessun cane con id "+ id);
        }
        return cane;
    }

    @Override
    public List<Cane> findByName(String nome) {
       List<Cane> tutti = caneRepository.findAll();
       List<Cane> risultato = new ArrayList<>();

       for (Cane cane : tutti){
           if (cane.getNome().toLowerCase().contains(nome.toLowerCase())){
               risultato.add(cane);
           }
       }
       return risultato;
    }

    @Override
    public Cane findByIdConPadrone(Long id) {
        return caneRepository.findByIdConPadrone(id)
                .orElseThrow(() -> new RuntimeException("Cane non trovato"));
    }

    @Override
    public List<Cane> findByAdottatoFalse() {
        return caneRepository.findByAdottato(false);
    }

}
