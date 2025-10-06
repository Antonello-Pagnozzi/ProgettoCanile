package com.jdk.Canile.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Padrone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nomeCognome;

    @NotBlank
    private String codiceFiscale;

    private String numeroTelefono;

    @OneToMany(mappedBy = "padrone")
    @JsonIgnore
    private List<Cane> cani = new ArrayList<>();

    //Costruttore parametrizzato
    public Padrone(Long id, String nomeCognome, String numeroTelefono, String codiceFiscale) {
        this.id = id;
        this.nomeCognome = nomeCognome;
        this.numeroTelefono = numeroTelefono;
        this.codiceFiscale = codiceFiscale;
        this.cani = new ArrayList<>();;
    }

    //Costruttore vuoto
    public Padrone() {}

    //Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCognome() {
        return nomeCognome;
    }

    public void setNomeCognome(String nomeCognome) {
        this.nomeCognome = nomeCognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public List<Cane> getCani() {
        return cani;
    }

    public void setCani(List<Cane> cani) {
        this.cani = cani;
    }
}
