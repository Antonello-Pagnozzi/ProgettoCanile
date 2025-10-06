package com.jdk.Canile.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Pratica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoPratica tipo;

    @NotNull
    @ManyToOne
    private Cane cane;

    @ManyToOne
    private Padrone padrone;

    private LocalDate data;

    public Pratica(){}

    public Pratica(Long id, TipoPratica tipo, Cane cane, Padrone padrone, LocalDate data) {
        this.id = id;
        this.tipo = tipo;
        this.cane = cane;
        this.padrone = padrone;
        this.data = data;
    }

    //Pratica senza padrone (es. ritrovamento)
    public Pratica(Long id, TipoPratica tipo, Cane cane,LocalDate data) {
        this.id = id;
        this.tipo = tipo;
        this.cane = cane;
        this.data = data;
    }

    //Getter e Setteer
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPratica getTipo() {
        return tipo;
    }

    public void setTipo(TipoPratica tipo) {
        this.tipo = tipo;
    }

    public Cane getCane() {
        return cane;
    }

    public void setCane(@NotBlank Cane cane) {
        this.cane = cane;
    }

    public Padrone getPadrone() {
        return padrone;
    }

    public void setPadrone(Padrone padrone) {
        this.padrone = padrone;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
