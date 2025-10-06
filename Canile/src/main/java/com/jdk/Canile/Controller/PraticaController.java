package com.jdk.Canile.Controller;

import com.jdk.Canile.Entity.Pratica;
import com.jdk.Canile.Service.CaneService;
import com.jdk.Canile.Service.PadroneService;
import com.jdk.Canile.Service.PraticaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pratiche")
public class PraticaController {

    private PraticaService praticaService;
    private CaneService caneService;
    private PadroneService padroneService;

    @Autowired
    public PraticaController(PraticaService praticaService, CaneService caneService, PadroneService padroneService){
        this.praticaService = praticaService;
        this.caneService = caneService;
        this.padroneService = padroneService;
    }

    @GetMapping
    public ResponseEntity<List<Pratica>> mostraPratiche(){
        List<Pratica> pratiche = praticaService.trovaTutte();
        return ResponseEntity.ok(pratiche);
    }

    @GetMapping("/cane/{id}")
    public ResponseEntity<List<Pratica>> pratichePerCane(@PathVariable Long id){
        List<Pratica> pratiche = praticaService.trovaPerCaneId(id);
        return ResponseEntity.ok(pratiche);
    }

    @GetMapping("/padrone/{id}")
    public ResponseEntity<List<Pratica>> pratichePerPadrone(@PathVariable Long id){
        List<Pratica> pratiche = praticaService.trovaPerPadroneId(id);
        return ResponseEntity.ok(pratiche);
    }

    @PostMapping("/salva")
    public ResponseEntity<Pratica> salvaPratica(@RequestBody Pratica pratica){
        Pratica salva = praticaService.creaPratica(pratica);
        return ResponseEntity.ok(salva);
    }

    @PutMapping("/aggiorna/{id}")
    public ResponseEntity<Pratica> aggiornaPratica(@PathVariable Long id, @RequestBody Pratica pratica){
        Pratica aggiornata = praticaService.aggiornaPratica(id, pratica);
        return ResponseEntity.ok(aggiornata);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pratica> trovaPraticaPerId(@PathVariable Long id){
        Pratica pratica = praticaService.getPraticaById(id).get();
        return ResponseEntity.ok(pratica);
    }

}
