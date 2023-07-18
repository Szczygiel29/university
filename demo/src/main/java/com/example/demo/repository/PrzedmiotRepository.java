package com.example.demo.repository;

import com.example.demo.model.Przedmiot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrzedmiotRepository extends JpaRepository<Przedmiot, Long> {
}
