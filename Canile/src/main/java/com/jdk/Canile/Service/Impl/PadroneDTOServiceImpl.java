package com.jdk.Canile.Service.Impl;

import com.jdk.Canile.DTO.CaneDTO;
import com.jdk.Canile.DTO.PadroneDTO;
import com.jdk.Canile.Entity.Padrone;
import com.jdk.Canile.Repository.PadroneRepository;
import com.jdk.Canile.Service.PadroneDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PadroneDTOServiceImpl implements PadroneDTOService {

    PadroneRepository padroneRepository;

    //costruttore
    @Autowired
    public PadroneDTOServiceImpl (PadroneRepository padroneRepository){
        this.padroneRepository = padroneRepository;
    }

    @Override
    public List<PadroneDTO> trovaPadroniConCani() {
        List<Padrone> padroni = padroneRepository.findAll();
        return padroni.stream().map(p -> new PadroneDTO(
                p.getId(),
                p.getNomeCognome(),
                p.getCodiceFiscale(),
                p.getNumeroTelefono(),
                p.getCani().stream()
                        .map(c -> new CaneDTO(c.getId(), c.getNome(),c.getSesso(), c.getTaglia(), c.getRazza()))
                        .toList()
        )).toList();
    }

    @Override
    public List<PadroneDTO> trovaPadroneConCaniPerID(Long id) {
        List<Padrone> padroni = new ArrayList<>();
        Padrone padrone = padroneRepository.findById(id).get();
        padroni.add(padrone);
        return padroni.stream().map(p -> new PadroneDTO(
                p.getId(),
                p.getNomeCognome(),
                p.getCodiceFiscale(),
                p.getNumeroTelefono(),
                p.getCani().stream()
                        .map(c -> new CaneDTO(c.getId(), c.getNome(), c.getSesso(), c.getTaglia(), c.getRazza()))
                        .toList()
        )).toList();
    }


}
