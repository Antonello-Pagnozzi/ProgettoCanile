package com.jdk.Canile.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Cane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Sesso sesso;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Taglia taglia;

    @NotNull
    private String razza;

    private boolean adottato;

    @ManyToOne(optional = true) //il cane può non avere padrone
    @JoinColumn(name = "padrone_id", nullable = true)
    private Padrone padrone;

    private String fotoUrl;

    //Costruttore
    public Cane(Long id, String nome, Sesso sesso, String razza, Taglia taglia, boolean adottato,Padrone padrone) {
        this.id = id;
        this.nome = nome;
        this.sesso = sesso;
        this.razza = razza;
        this.taglia = taglia;
        this.adottato = adottato;
        this.padrone = padrone;
        this.fotoUrl = null;
    }

    //Costruttore per trovatelli
    public Cane(Long id, String nome, Sesso sesso, String razza, Taglia taglia){
        this.id = id;
        this.nome = nome;
        this.sesso = sesso;
        this.razza = razza;
        this.taglia = taglia;
        this.adottato = false;
        this.fotoUrl = null;
    }

    //Costruttore vuoto
    public Cane(){}

    //Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Taglia getTaglia() {
        return taglia;
    }

    public void setTaglia(Taglia taglia) {
        this.taglia = taglia;
    }

    public boolean isAdottato() {
        return adottato;
    }

    public void setAdottato(boolean adottato) {
        this.adottato = adottato;
    }

    public Padrone getPadrone() {
        return padrone;
    }

    public void setPadrone(Padrone padrone) {
        this.padrone = padrone;
    }

    public String getRazza() {
        return razza;
    }

    public void setRazza(String razza) {
        this.razza = razza;
    }


    public void setSesso(Sesso sesso) {
        this.sesso = sesso;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
