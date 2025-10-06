package com.jdk.Canile;

import com.jdk.Canile.Entity.Cane;
import com.jdk.Canile.Entity.Padrone;
import com.jdk.Canile.Entity.Sesso;
import com.jdk.Canile.Entity.Taglia;
import com.jdk.Canile.Repository.CaneRepository;
import com.jdk.Canile.Repository.PadroneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CanilePersistenceTest {

    @Autowired
    private CaneRepository caneRepository;

    @Autowired
    private PadroneRepository padroneRepository;

//    @Test
//    void testCaneEPadrone() {
//        // Creiamo un nuovo padrone
//        Padrone padrone = new Padrone(null, "Mario Rossi", "1234567890", "RSSMRA80A01H501X");
//        padroneRepository.save(padrone);
//
//        //Padrone senza cani
//        Padrone padrone2 = new Padrone(null, "Giulia Pagnozzi", "3337122459", "PGNGL00605H501");
//        padroneRepository.save(padrone2);
//
//        // Creiamo un nuovo cane senza padrone
//        Cane cane1 = new Cane(null, "Elvis", Sesso.MASCHIO, "Labrador",Taglia.MEDIA);
//        caneRepository.save(cane1);
//
//        Cane cane2 = new Cane(null, "Principessa", Sesso.FEMMINA, "Dobermann",Taglia.GRANDE);
//        caneRepository.save(cane2);
//
//        // Creiamo un cane con padrone
//        Cane cane3 = new Cane(null, "Luna", Sesso.FEMMINA,"Meticcia",Taglia.PICCOLA, true, padrone);
//        cane3.setAdottato(true);
//        caneRepository.save(cane3);
//
//        // Verifica che i dati siano salvati
//        Optional<Padrone> p = padroneRepository.findById(padrone.getId());
//        assertThat(p).isPresent();
//        assertThat(p.get().getNomeCognome()).isEqualTo("Mario Rossi");
//
//        Optional<Cane> c = caneRepository.findById(cane3.getId());
//        assertThat(c).isPresent();
//        assertThat(c.get().getPadrone()).isNotNull();
//        assertThat(c.get().getPadrone().getNomeCognome()).isEqualTo("Mario Rossi");
//
//        Optional<Cane> c1 = caneRepository.findById(cane1.getId());
//        assertThat(c1).isPresent();
//        assertThat(c1.get().getPadrone()).isNull();
//    }
}
