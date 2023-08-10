package com.example.sportsteamwebsiteapi.match;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    List<Match> findAllByOrderByMatchDateAsc();
    
    void deleteAll();
}
