package com.example.demo.repository;

import com.example.demo.model.Wykladowca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WykladowcaRepository extends JpaRepository<Wykladowca, Long> {
}
