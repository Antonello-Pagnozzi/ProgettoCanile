package com.jdk.Canile.DTO;

public record CaneDTO(
        Long id,
        String nome,
        com.jdk.Canile.Entity.Sesso sesso,
        com.jdk.Canile.Entity.Taglia taglia,
        String razza) {}
