package com.example.sportsteamwebsiteapi.competition;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
    
    Competition findByCompetitionNameAndSeason(String competitionName, String season);
    
    List<Competition> findAllByCompetitionName(String competitionName);
    
    List<Competition> findAllBySeason(String season);
}
