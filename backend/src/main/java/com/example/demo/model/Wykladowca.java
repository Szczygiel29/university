package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Wykladowca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String imie;
    private String nazwisko;
    private String stopien;

    @OneToMany(mappedBy = "wykladowca")
    private List<Ocena> oceny;

    public Wykladowca(String imie, String nazwisko, String stopien, List<Ocena> oceny) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.stopien = stopien;
        this.oceny = oceny;
    }
}
