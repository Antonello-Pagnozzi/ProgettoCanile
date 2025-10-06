package com.jdk.Canile.DTO;

import java.util.List;

public record PadroneDTO(
        Long id, String nomeCognome, String codiceFiscale, String numeroTelefono, List<CaneDTO> cani
) {


}
