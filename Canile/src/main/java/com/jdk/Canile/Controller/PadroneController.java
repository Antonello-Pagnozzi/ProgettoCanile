package com.jdk.Canile.Controller;

import com.jdk.Canile.DTO.PadroneDTO;
import com.jdk.Canile.Entity.Cane;
import com.jdk.Canile.Entity.Padrone;
import com.jdk.Canile.Service.PadroneDTOService;
import com.jdk.Canile.Service.PadroneService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/padroni")
public class PadroneController {

    private PadroneService padroneService;
    private PadroneDTOService padroneDtoService;

    @Autowired
    public PadroneController (PadroneService padroneService, PadroneDTOService padroneDtoService){
        this.padroneService = padroneService;
        this.padroneDtoService = padroneDtoService;
    }

    //metodi senza lista cani
    @GetMapping
    public ResponseEntity<List<Padrone>> getPadroni(){
        List<Padrone> padroni = padroneService.trovaTutti();
        return ResponseEntity.ok(padroni);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Padrone> getPadroneById(@PathVariable Long id){
        Padrone padrone = padroneService.trovaPerId(id).get();
        return ResponseEntity.ok(padrone);
    }

    //metodi con lista cani
    @GetMapping("/cani")
    public ResponseEntity<List<PadroneDTO>> getPadroniECani(){
        List<PadroneDTO> lista = padroneDtoService.trovaPadroniConCani();
        return ResponseEntity.ok(lista);
    }


    @GetMapping("/cani/{id}")
    public ResponseEntity<List<PadroneDTO>> getPadroneCaniConId(@PathVariable Long id){
        List<PadroneDTO> padroni = padroneDtoService.trovaPadroneConCaniPerID(id);
        return ResponseEntity.ok(padroni);
    }


    @PostMapping("/salva")
    public ResponseEntity<Padrone> salvaPadrone(@RequestBody Padrone padrone){
        padroneService.salvaPadrone(padrone);
        return ResponseEntity.ok(padrone);
    }

    @DeleteMapping("/cancella/{id}")
    public String cancellaPadrone(@PathVariable Long id){
        padroneService.cancellaPadroneById(id);
        return "Il padrone con id "+ id + " è stato cancellato correttamente!";
    }

    //aggiunta e rimozione cane
    @PutMapping("/aggiungi/{padroneId}/cane/{caneId}")
    public ResponseEntity<Padrone> aggiungiCane(@PathVariable Long padroneId,@PathVariable Long caneId ){
        padroneService.aggiungiCane(padroneId,caneId);
        Padrone padrone = padroneService.trovaPerId(padroneId).get();
        return ResponseEntity.ok(padrone);
    }

    @PutMapping("/rimuovi/{padroneId}/cane/{caneId}")
    public ResponseEntity<Padrone> rimuoviCane(@PathVariable Long padroneId, @PathVariable Long caneId){
        padroneService.rimuoviCane(padroneId,caneId);
        Padrone padrone = padroneService.trovaPerId(padroneId).get();
        return ResponseEntity.ok(padrone);
    }
}
