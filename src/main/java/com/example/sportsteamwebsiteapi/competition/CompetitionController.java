package com.example.sportsteamwebsiteapi.competition;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/competition")
@CrossOrigin
public class CompetitionController {
    private final CompetitionService competitionService;
    
    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }
    
    @GetMapping
    public ResponseEntity<?> getCompetitions(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String season
    ) {
        return ResponseEntity.ok().body(competitionService.getCompetitions(name, season));
    }
    
    @GetMapping(value = "/{competitionId}")
    public ResponseEntity<?> getCompetition(@PathVariable int competitionId) {
        return ResponseEntity.ok().body(competitionService.getCompetition(competitionId));
    }
    
    @GetMapping(value = "/{competitionId}/matches")
    public ResponseEntity<?> getCompetitionMatches(@PathVariable int competitionId) {
        return ResponseEntity.ok().body(competitionService.getCompetitionMatches(competitionId));
    }
    
    @GetMapping(value = "/{competitionId}/lastMatch")
    public ResponseEntity<?> getLastMatch(@PathVariable int competitionId) {
        return ResponseEntity.ok().body(competitionService.getLastMatch(competitionId));
    }
    
    @GetMapping(value = "/{competitionId}/nextMatch")
    public ResponseEntity<?> getNextMatch(@PathVariable int competitionId) {
        return ResponseEntity.ok().body(competitionService.getNextMatch(competitionId));
    }
    
    @GetMapping(value = "/{competitionId}/completedMatches")
    public ResponseEntity<?> getCompletedMatches(@PathVariable int competitionId) {
        return ResponseEntity.ok().body(competitionService.getCompletedMatches(competitionId));
    }
    
    @GetMapping(value = "/{competitionId}/scheduledMatches")
    public ResponseEntity<?> getUpcomingMatches(@PathVariable int competitionId) {
        return ResponseEntity.ok().body(competitionService.getUpcomingMatches(competitionId));
    }
    
    @PostMapping
    public ResponseEntity<?> addCompetition(@RequestBody Competition competition) {
        Competition newCompetition = competitionService.addNewCompetition(competition);
        return ResponseEntity
                   .created(URI.create("api/competition/" + newCompetition.getCompetitionId()))
                   .body(newCompetition);
    }
    
    @DeleteMapping(value = "{competitionId}")
    public ResponseEntity<?> deleteCompetition(@PathVariable int competitionId) {
        competitionService.deleteCompetition(competitionId);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping(value = "{competitionId}")
    public ResponseEntity<?> updateCompetition(
        @PathVariable int competitionId,
        @RequestBody Competition updatedCompetition
    ) {
        return ResponseEntity
                   .ok()
                   .body(competitionService.updateCompetition(competitionId, updatedCompetition));
    }
}