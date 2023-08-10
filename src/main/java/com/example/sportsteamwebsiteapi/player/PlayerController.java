package com.example.sportsteamwebsiteapi.player;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/player")
@CrossOrigin
public class PlayerController {
    private final PlayerService playerService;
    
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    
    @GetMapping()
    public ResponseEntity<?> getPlayers(@RequestParam(required = false) Integer numOfPlayers) {
        return ResponseEntity.ok().body(playerService.getPlayers(numOfPlayers));
    }
    
    @GetMapping(
        value = "image/{playerId}",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<?> getPlayerPhoto(@PathVariable int playerId) {
        return ResponseEntity.ok().body(playerService.getPlayerPhoto(playerId));
    }
    
    @GetMapping(value = "{playerId}")
    public ResponseEntity<?> getPlayer(@PathVariable int playerId) {
        return ResponseEntity.ok().body(playerService.getPlayer(playerId));
    }
    
    @GetMapping(value = "{playerId}/achievements")
    public ResponseEntity<?> getPlayerAchievements(@PathVariable int playerId) {
        return ResponseEntity.ok().body(playerService.getPlayerAchievements(playerId));
    }
    
    @PostMapping
    public ResponseEntity<?> addNewPlayer(@RequestBody Player player) {
        Player newPlayer = playerService.addNewPlayer(player);
        return ResponseEntity
                   .created(URI.create("api/player/" + newPlayer.getPlayerId()))
                   .body(newPlayer);
    }
    
    @DeleteMapping(value = "{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable int playerId) {
        playerService.deletePlayer(playerId);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping(value = "{playerId}")
    public ResponseEntity<?> updatePlayer(
        @PathVariable int playerId,
        @RequestBody Player player
    ) {
        return ResponseEntity.ok().body(playerService.updatePlayer(playerId, player));
    }
}
