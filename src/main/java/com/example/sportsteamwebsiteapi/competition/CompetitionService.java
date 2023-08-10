package com.example.sportsteamwebsiteapi.competition;

import com.example.sportsteamwebsiteapi.match.Match;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@Service
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    
    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }
    
    @Transactional
    public List<Competition> getCompetitions(String name, String season) {
        if (name != null && season != null && !name.isEmpty() && !season.isEmpty()) {
            Competition competition =
                competitionRepository.findByCompetitionNameAndSeason(name, season);
            if (competition == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found");
            }
            return List.of(competition);
        }
        if (name != null && !name.isEmpty()) {
            return getCompetitionsByName(name);
        }
        if (season != null && !season.isEmpty()) {
            return getCompetitionsBySeason(season);
        }
        return competitionRepository.findAll();
    }
    
    public Competition getCompetition(int competitionId) {
        return competitionRepository.findById(competitionId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Competition with id " + competitionId + " does not exist"
            )
        );
    }
    
    public Competition addNewCompetition(Competition competition) {
        if (competition.getCompetitionName() == null || competition.getSeason() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Competition name and season are required"
            );
        }
        if (competition.getCompetitionName().length() == 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Competition name has to be at least 1 character long"
            );
        }
        if (competition.getSeason().length() == 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Season has to be at least 1 character long"
            );
        }
        try {
            return competitionRepository.save(competition);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                e.getMostSpecificCause().getMessage()
            );
        }
    }
    
    public void deleteCompetition(int competitionId) {
        if (competitionRepository.findById(competitionId).isPresent()) {
            competitionRepository.deleteById(competitionId);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Competition with id " + competitionId + " does not exist"
            );
        }
    }
    
    public List<Match> getCompetitionMatches(int competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Competition with id " + competitionId + " does not exist"
            )
        );
        return competition.getMatches();
    }
    
    public Competition updateCompetition(int competitionId, Competition updatedCompetition) {
        Competition competition = competitionRepository.findById(competitionId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Competition with id " + competitionId + " does not exist"
            )
        );
        
        if (
            updatedCompetition.getCompetitionName() != null
            && updatedCompetition.getSeason() != null
        ) {
            if (
                competitionRepository.findByCompetitionNameAndSeason(
                    updatedCompetition.getCompetitionName(),
                    updatedCompetition.getSeason()
                ) != null
            ) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Competition with name '" + updatedCompetition.getCompetitionName()
                    + "' and season '" + updatedCompetition.getSeason() + "' already exists"
                );
            }
        }
        
        if (
            updatedCompetition.getCompetitionName() != null
            && updatedCompetition.getCompetitionName().length() > 0
        ) {
            competition.setCompetitionName(updatedCompetition.getCompetitionName());
        }
        if (updatedCompetition.getSeason() != null &&
            updatedCompetition.getSeason().length() > 0) {
            competition.setSeason(updatedCompetition.getSeason());
        }
        
        return competitionRepository.save(competition);
    }
    
    public List<Competition> getCompetitionsByName(String competitionName) {
        return competitionRepository.findAllByCompetitionName(competitionName);
    }
    
    public List<Competition> getCompetitionsBySeason(String season) {
        return competitionRepository.findAllBySeason(String.valueOf(season));
    }
    
    public Match getLastMatch(int competitionId) {
        List<Match> matches = getCompletedMatches(competitionId);
        if (matches.size() == 0) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No matches have been played yet"
            );
        }
        return matches.get(matches.size() - 1);
    }
    
    public Match getNextMatch(int competitionId) {
        List<Match> matches = getUpcomingMatches(competitionId);
        if (matches.size() == 0) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No matches are scheduled"
            );
        }
        return matches.get(0);
    }
    
    public List<Match> getCompletedMatches(int competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Competition with id " + competitionId + " does not exist"
            )
        );
        List<Match> matches = competition.getMatches();
        matches.removeIf(
            match -> match.getMatchDate().isAfter(ChronoLocalDate.from(LocalDate.now()))
        );
        return matches;
    }
    
    public List<Match> getUpcomingMatches(int competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Competition with id " + competitionId + " does not exist"
            )
        );
        List<Match> matches = competition.getMatches();
        matches.removeIf(
            match -> match.getMatchDate().isBefore(ChronoLocalDate.from(LocalDate.now()))
        );
        return matches;
    }
}