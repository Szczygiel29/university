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
public class Przedmiot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String nazwa;
    @OneToMany(mappedBy = "przedmiot")
    private List<Ocena> oceny;

    public Przedmiot(String nazwa, List<Ocena> oceny) {
        this.nazwa = nazwa;
        this.oceny = oceny;
    }

}
