package com.jdk.Canile.Controller;

import com.jdk.Canile.Entity.Cane;
import com.jdk.Canile.Service.CaneService;
import com.jdk.Canile.Service.PadroneDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cani")
public class CaneController {

    private final CaneService caneService;


    @Autowired
    public CaneController(CaneService caneService){
        this.caneService= caneService;
    }

    @GetMapping
    public List<Cane> trovaTutti() {
        return caneService.trovaTutti();
    }

    @GetMapping("/{id}")
    public Cane findCaneById(@PathVariable Long id){
        return caneService.findCaneById(id).get();
    }

    @GetMapping("/adottati")
    public List<Cane> trovaPerAdozione(@RequestParam boolean stato){
        return caneService.findByAdottato(stato);
    }

    @PostMapping("/salva")
    public ResponseEntity<Cane> salvaCane(@RequestBody Cane cane) {
        Cane nuovoCane = caneService.salvaCane(cane);
        return ResponseEntity.ok(nuovoCane);
    }

    @PutMapping("/aggiorna")
    public ResponseEntity<Cane> aggiornaCane(@RequestBody Cane cane){
        caneService.aggiornaCane(cane);
        Cane caneConPadrone = caneService.findByIdConPadrone(cane.getId());
        return ResponseEntity.ok(caneConPadrone);
    }

    @DeleteMapping("/cancella/{id}")
    public String cancellaCane(@PathVariable Long id){
        caneService.eliminaCane(id);
        return "Il cane con id " + id +" è stato cancellato correttamente!";
    }

    @GetMapping("/trova/{nome}")
    public ResponseEntity<List<Cane>> trovaPerNome(@PathVariable String nome){
        return ResponseEntity.ok(caneService.findByName(nome));
    }

}
