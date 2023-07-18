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
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String imie;
    private String nazwisko;
    private String nrIndeksu;
    @OneToMany(mappedBy = "student")
    private List<Ocena> oceny;

    public Student(String imie, String nazwisko, String nrIndeksu, List<Ocena> oceny) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrIndeksu = nrIndeksu;
        this.oceny = oceny;
    }

}
