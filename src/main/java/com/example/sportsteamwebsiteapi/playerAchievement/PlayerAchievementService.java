package com.example.sportsteamwebsiteapi.playerAchievement;

import com.example.sportsteamwebsiteapi.player.Player;
import com.example.sportsteamwebsiteapi.player.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlayerAchievementService {
    private final PlayerAchievementRepository playerAchievementRepository;
    private final PlayerRepository playerRepository;
    
    public PlayerAchievementService(
        PlayerAchievementRepository playerAchievementRepository,
        PlayerRepository playerRepository
    ) {
        this.playerAchievementRepository = playerAchievementRepository;
        this.playerRepository = playerRepository;
    }
    
    public PlayerAchievement addNewPlayerAchievement(
        int playerId,
        PlayerAchievement playerAchievement
    ) {
        Player player = playerRepository.findById(playerId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Player with id " + playerId + " does not exist."
            )
        );
        playerAchievement.setPlayer(player);
        
        if (playerAchievement.getAchievementDate() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Achievement date is required"
            );
        }
        if (
            playerAchievement.getAchievement() == null
            || playerAchievement.getAchievement().isEmpty()
        ) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Achievement type is required"
            );
        }
        
        playerAchievementRepository.save(playerAchievement);
        player.getPlayerAchievements().add(playerAchievement);
        playerRepository.save(player);
        return playerAchievement;
    }
    
    public Iterable<PlayerAchievement> getAllPlayerAchievements() {
        return playerAchievementRepository.findAll();
    }
    
    public PlayerAchievement getPlayerAchievement(int playerAchievementId) {
        return playerAchievementRepository.findById(playerAchievementId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Player achievement with id " + playerAchievementId + " does not exist."
            )
        );
    }
    
    public void deletePlayerAchievement(int playerAchievementId) {
        PlayerAchievement playerAchievement =
            playerAchievementRepository.findById(playerAchievementId).orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Player achievement with id " + playerAchievementId + " does not exist."
                )
            );
        Player player = playerRepository
                            .findById(playerAchievement.getPlayer().getPlayerId())
                            .orElseThrow(
                                () -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Player with id " + playerAchievement.getPlayer().getPlayerId()
                                    + " does not exist."
                                )
                            );
        player.getPlayerAchievements().remove(playerAchievement);
        playerAchievementRepository.delete(playerAchievement);
    }
    
    
    public PlayerAchievement updatePlayerAchievement(
        int playerAchievementId,
        PlayerAchievement updatedPlayerAchievement
    ) {
        PlayerAchievement playerAchievement =
            playerAchievementRepository.findById(playerAchievementId).orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Player achievement with id " + playerAchievementId + " does not exist."
                )
            );
        if (
            updatedPlayerAchievement.getAchievementDate() != null
            && updatedPlayerAchievement.getAchievement() != null
            && !updatedPlayerAchievement.getAchievement().isEmpty()
        ) {
            playerAchievement.setAchievementDate(updatedPlayerAchievement.getAchievementDate());
            playerAchievement.setAchievement(updatedPlayerAchievement.getAchievement());
        }
        return playerAchievementRepository.save(playerAchievement);
    }
}
