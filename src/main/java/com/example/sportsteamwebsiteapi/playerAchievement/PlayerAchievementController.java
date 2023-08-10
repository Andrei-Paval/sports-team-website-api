package com.example.sportsteamwebsiteapi.playerAchievement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/playerAchievement")
@CrossOrigin
public class PlayerAchievementController {
    private final PlayerAchievementService playerAchievementService;
    
    public PlayerAchievementController(PlayerAchievementService playerAchievementService) {
        this.playerAchievementService = playerAchievementService;
    }
    
    @PostMapping()
    public ResponseEntity<?> addNewPlayerAchievement(
        @RequestParam int playerId,
        @RequestBody PlayerAchievement playerAchievement
    ) {
        PlayerAchievement newPlayerAchievement =
            playerAchievementService.addNewPlayerAchievement(playerId, playerAchievement);
        return ResponseEntity
                   .created(
                       URI.create(
                           "/api/playerAchievement/"
                           + newPlayerAchievement.getPlayerAchievementId()
                       )
                   )
                   .body(newPlayerAchievement);
    }
    
    @GetMapping()
    public ResponseEntity<?> getAllPlayerAchievements() {
        return ResponseEntity.ok().body(playerAchievementService.getAllPlayerAchievements());
    }
    
    @GetMapping(value = "{playerAchievementId}")
    public ResponseEntity<?> getPlayerAchievement(@PathVariable int playerAchievementId) {
        return ResponseEntity
                   .ok()
                   .body(playerAchievementService.getPlayerAchievement(playerAchievementId));
    }
    
    @DeleteMapping(value = "{playerAchievementId}")
    public ResponseEntity<?> deletePlayerAchievement(@PathVariable int playerAchievementId) {
        playerAchievementService.deletePlayerAchievement(playerAchievementId);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping(value = "/{playerAchievementId}")
    public ResponseEntity<?> updatePlayerAchievement(
        @PathVariable int playerAchievementId,
        @RequestBody PlayerAchievement playerAchievement
    ) {
        return ResponseEntity
                   .ok()
                   .body(
                       playerAchievementService.updatePlayerAchievement(
                           playerAchievementId,
                           playerAchievement
                       )
                   );
    }
}
