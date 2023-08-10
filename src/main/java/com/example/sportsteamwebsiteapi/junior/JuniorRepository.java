package com.example.sportsteamwebsiteapi.junior;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuniorRepository extends JpaRepository<Junior, Integer> {
    List<Junior> findByDivisionIgnoreCase(String division);
}
