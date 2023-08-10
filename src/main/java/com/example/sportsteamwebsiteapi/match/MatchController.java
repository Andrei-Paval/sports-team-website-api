package com.example.sportsteamwebsiteapi.match;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/match")
@CrossOrigin
public class MatchController {
    private final MatchService matchService;
    
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }
    
    @GetMapping()
    public ResponseEntity<?> getAllMatches() {
        return ResponseEntity.ok().body(matchService.getAllMatches());
    }
    
    @GetMapping(value = "/{matchId}")
    public ResponseEntity<?> getMatchById(@PathVariable int matchId) {
        return ResponseEntity.ok().body(matchService.getMatchById(matchId));
    }
    
    @GetMapping(value = "/scheduled")
    public ResponseEntity<?> getUpcomingMatches() {
        return ResponseEntity.ok().body(matchService.getScheduledMatches());
    }
    
    @GetMapping(value = "/completed")
    public ResponseEntity<?> getCompletedMatches() {
        return ResponseEntity.ok().body(matchService.getCompletedMatches());
    }
    
    @GetMapping(value = "/next")
    public ResponseEntity<?> getNextMatches() {
        return ResponseEntity.ok().body(matchService.getNextMatch());
    }
    
    @GetMapping(value = "/last")
    public ResponseEntity<?> getLastMatch() {
        return ResponseEntity.ok().body(matchService.getLastMatch());
    }
    
    @PostMapping()
    public ResponseEntity<?> addNewMatch(@RequestBody Match match) {
        Match newMatch = matchService.addNewMatch(match);
        return ResponseEntity
                   .created(URI.create("api/match/" + newMatch.getMatchId()))
                   .body(newMatch);
    }
    
    @PutMapping(value = "/{matchId}")
    public ResponseEntity<?> updateMatch(@PathVariable int matchId, @RequestBody Match match) {
        return ResponseEntity.ok().body(matchService.updateMatch(matchId, match));
    }
    
    @DeleteMapping(value = "{matchId}")
    public ResponseEntity<?> deleteMatch(@PathVariable int matchId) {
        matchService.deleteMatch(matchId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping()
    public ResponseEntity<?> deleteAllMatches() {
        matchService.deleteAllMatches();
        return ResponseEntity.ok().build();
    }
}
