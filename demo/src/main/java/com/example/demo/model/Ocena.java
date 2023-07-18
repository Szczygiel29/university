package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ocena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private int ocena;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "IDStudenta")
    private Student student;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "IDWykladowcy")
    private Wykladowca wykladowca;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "IDPrzedmiotu")
    private Przedmiot przedmiot;

    public Ocena(int ocena, Student student, Wykladowca wykladowca, Przedmiot przedmiot) {
        this.ocena = ocena;
        this.student = student;
        this.wykladowca = wykladowca;
        this.przedmiot = przedmiot;
    }
}
