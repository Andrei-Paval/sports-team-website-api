package com.example.sportsteamwebsiteapi.match;

import com.example.sportsteamwebsiteapi.competition.Competition;
import com.example.sportsteamwebsiteapi.competition.CompetitionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final CompetitionRepository competitionRepository;
    
    public MatchService(
        MatchRepository matchRepository,
        CompetitionRepository competitionRepository
    ) {
        this.matchRepository = matchRepository;
        this.competitionRepository = competitionRepository;
    }
    
    public Match addNewMatch(Match match) {
        if (match.getHomeTeam() == null || match.getHomeTeam().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Home team is required"
            );
        }
        if (match.getAwayTeam() == null || match.getAwayTeam().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Away team is required"
            );
        }
        if (Objects.equals(match.getHomeTeam(), match.getAwayTeam())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Home team and away team cannot be the same"
            );
        }
        if (match.getMatchDate() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Match must have a valid date"
            );
        }
        if (match.getMatchLocation() == null || match.getMatchLocation().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Match must have a location"
            );
        }
        if (match.getCompetition() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Match must have a competition"
            );
        }
        
        Competition competition =
            competitionRepository.findById(match.getCompetition().getCompetitionId()).orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Competition with id " + match.getCompetition().getCompetitionId()
                    + " does not exist"
                )
            );
        match.setCompetition(competition);
        return matchRepository.save(match);
    }
    
    public Match updateMatch(int matchId, Match updatedMatch) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Match with id " + matchId + " does not exist"
            );
        }
        
        if (updatedMatch.getCompetition() != null) {
            int updatedCompetitionId = updatedMatch.getCompetition().getCompetitionId();
            Competition competition =
                competitionRepository.findById(updatedCompetitionId).orElseThrow(
                    () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Competition with id " + updatedCompetitionId + " does not exist"
                    )
                );
            match.setCompetition(competition);
        }
        if (updatedMatch.getHomeTeam() != null || updatedMatch.getAwayTeam() != null) {
            if (updatedMatch.getHomeTeam() != null && updatedMatch.getAwayTeam() != null) {
                if (updatedMatch.getHomeTeam().isEmpty() || updatedMatch.getAwayTeam().isEmpty()) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Home team and away team cannot be empty"
                    );
                }
                if (Objects.equals(updatedMatch.getHomeTeam(), updatedMatch.getAwayTeam())) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Home team and away team cannot be the same"
                    );
                }
                match.setHomeTeam(updatedMatch.getHomeTeam());
                match.setAwayTeam(updatedMatch.getAwayTeam());
            } else if (updatedMatch.getHomeTeam() != null) {
                if (updatedMatch.getHomeTeam().isEmpty()) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Home team cannot be empty"
                    );
                }
                if (Objects.equals(updatedMatch.getHomeTeam(), match.getAwayTeam())) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Home team and away team cannot be the same"
                    );
                }
                match.setHomeTeam(updatedMatch.getHomeTeam());
            } else {
                if (updatedMatch.getAwayTeam().isEmpty()) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Away team cannot be empty"
                    );
                }
                if (Objects.equals(match.getHomeTeam(), updatedMatch.getAwayTeam())) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Home team and away team cannot be the same"
                    );
                }
                match.setAwayTeam(updatedMatch.getAwayTeam());
            }
        }
        
        if (updatedMatch.getHomeTeamLogo() != null) {
            match.setHomeTeamLogo(updatedMatch.getHomeTeamLogo());
        }
        if (updatedMatch.getAwayTeamLogo() != null) {
            match.setAwayTeamLogo(updatedMatch.getAwayTeamLogo());
        }
        if (updatedMatch.getMatchDate() != null) {
            match.setMatchDate(updatedMatch.getMatchDate());
        }
        if (updatedMatch.getMatchLocation() != null && !updatedMatch.getMatchLocation().isEmpty()) {
            match.setMatchLocation(updatedMatch.getMatchLocation());
        }
        if (updatedMatch.getMatchScore() != null) {
            match.setMatchScore(updatedMatch.getMatchScore());
        }
        if (updatedMatch.getSet1Score() != null) {
            match.setSet1Score(updatedMatch.getSet1Score());
        }
        if (updatedMatch.getSet2Score() != null) {
            match.setSet2Score(updatedMatch.getSet2Score());
        }
        if (updatedMatch.getSet3Score() != null) {
            match.setSet3Score(updatedMatch.getSet3Score());
        }
        if (updatedMatch.getSet4Score() != null) {
            match.setSet4Score(updatedMatch.getSet4Score());
        }
        if (updatedMatch.getSet5Score() != null) {
            match.setSet5Score(updatedMatch.getSet5Score());
        }
        if (updatedMatch.getLivestreamUrl() != null) {
            match.setLivestreamUrl(updatedMatch.getLivestreamUrl());
        }
        
        return matchRepository.save(match);
    }
    
    public void deleteMatch(int matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Match with id " + matchId + " does not exist"
            )
        );
        Competition competition = match.getCompetition();
        competition.getMatches().remove(match);
        matchRepository.deleteById(matchId);
    }
    
    public void deleteAllMatches() {
        List<Match> matches = matchRepository.findAll();
        for (Match match : matches) {
            Competition competition = match.getCompetition();
            competition.getMatches().remove(match);
            matchRepository.deleteById(match.getMatchId());
        }
    }
    
    public Match getMatchById(int matchId) {
        return matchRepository.findById(matchId).orElse(null);
    }
    
    @Transactional
    public List<Match> getAllMatches() {
        return matchRepository.findAllByOrderByMatchDateAsc();
    }
    
    @Transactional
    public List<Match> getScheduledMatches() {
        List<Match> matches = matchRepository.findAllByOrderByMatchDateAsc();
        matches.removeIf(
            match -> match.getMatchDate().isBefore(ChronoLocalDate.from(LocalDate.now()))
        );
        return matches;
    }
    
    @Transactional
    public List<Match> getCompletedMatches() {
        List<Match> matches = matchRepository.findAllByOrderByMatchDateAsc();
        matches.removeIf(
            match -> match.getMatchDate().isAfter(ChronoLocalDate.from(LocalDate.now()))
        );
        return matches;
    }
    
    @Transactional
    public Match getNextMatch() {
        List<Match> nextMatches = getScheduledMatches();
        if (nextMatches.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No scheduled matches found"
            );
        }
        return nextMatches.get(0);
    }
    
    @Transactional
    public Match getLastMatch() {
        List<Match> lastMatches = getCompletedMatches();
        if (lastMatches.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No completed matches found"
            );
        }
        return lastMatches.get(lastMatches.size() - 1);
    }
}
