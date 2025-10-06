package com.jdk.Canile.Service;

import com.jdk.Canile.DTO.PadroneDTO;

import java.util.List;

public interface PadroneDTOService {
    List<PadroneDTO> trovaPadroniConCani();
    List<PadroneDTO> trovaPadroneConCaniPerID(Long id);
}
