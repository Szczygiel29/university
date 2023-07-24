package com.example.demo.repository;

import com.example.demo.model.Ocena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcenaRepository extends JpaRepository<Ocena, Long> {
}
