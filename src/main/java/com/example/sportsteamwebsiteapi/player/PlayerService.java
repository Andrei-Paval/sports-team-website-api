package com.example.sportsteamwebsiteapi.player;

import com.example.sportsteamwebsiteapi.playerAchievement.PlayerAchievement;
import com.example.sportsteamwebsiteapi.utils.DateValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    
    public List<Player> getPlayers(Integer numOfPlayers) {
        if (numOfPlayers != null) {
            if (numOfPlayers <= 0) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Number of players must be greater than 0"
                );
            }
            List<Player> players = playerRepository.findAll();
            if (players.size() < numOfPlayers) {
                return players;
            }
            return players.subList(0, numOfPlayers);
        }
        return playerRepository.findAll();
    }
    
    public byte[] getPlayerPhoto(int id) {
        Player player = playerRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Player with id " + id + " does not exist"
            )
        );
        return player.getPhoto();
    }
    
    public Player addNewPlayer(Player player) {
        if (player.getFirstName() == null || player.getFirstName().length() == 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid first name"
            );
        }
        if (player.getLastName() == null || player.getLastName().length() == 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid last name"
            );
        }
        if (player.getNationality() == null || player.getNationality().length() == 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid nationality"
            );
        }
        if (
            player.getBirthDate() == null
            || !DateValidator.isValid(player.getBirthDate().toString())
        ) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid birth date"
            );
        }
        if (player.getHeight() == null || player.getHeight() <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid height"
            );
        }
        if (player.getPlayerNumber() == 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid player number"
            );
        }
        
        try {
            return playerRepository.save(player);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                e.getMostSpecificCause().getMessage()
            );
        }
    }
    
    public void deletePlayer(int playerId) {
        if (playerRepository.existsById(playerId)) {
            playerRepository.deleteById(playerId);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Player with id " + playerId + " does not exist"
            );
        }
    }
    
    public Player updatePlayer(int playerId, Player updatedPlayer) {
        Player player = playerRepository.findById(playerId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Player with id " + playerId + " does not exist"
            )
        );
        
        if (updatedPlayer.getFirstName() != null && updatedPlayer.getFirstName().length() > 0) {
            player.setFirstName(updatedPlayer.getFirstName());
        }
        if (updatedPlayer.getLastName() != null && updatedPlayer.getLastName().length() > 0) {
            player.setLastName(updatedPlayer.getLastName());
        }
        if (updatedPlayer.getNationality() != null && updatedPlayer.getNationality().length() > 0) {
            player.setNationality(updatedPlayer.getNationality());
        }
        if (updatedPlayer.getPosition() != null && updatedPlayer.getPosition().length() > 0) {
            player.setPosition(updatedPlayer.getPosition());
        }
        if (
            updatedPlayer.getBirthDate() != null
            && DateValidator.isValid(updatedPlayer.getBirthDate().toString())
        ) {
            player.setBirthDate(updatedPlayer.getBirthDate());
        }
        if (updatedPlayer.getHeight() != null && updatedPlayer.getHeight() > 0) {
            player.setHeight(updatedPlayer.getHeight());
        }
        if (
            updatedPlayer.getPlayerNumber() > 0
            && !playerRepository.existsByPlayerNumber(updatedPlayer.getPlayerNumber())
        ) {
            player.setPlayerNumber(updatedPlayer.getPlayerNumber());
        }
        if (updatedPlayer.getPhoto() != null) {
            player.setPhoto(updatedPlayer.getPhoto());
        }
        
        return playerRepository.save(player);
    }
    
    public Player getPlayer(int playerId) {
        return playerRepository.findById(playerId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Player with id " + playerId + " does not exist"
            )
        );
    }
    
    public List<PlayerAchievement> getPlayerAchievements(int playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Player with id " + playerId + " does not exist"
            )
        );
        System.out.println(player.getPlayerAchievements());
        return player.getPlayerAchievements();
    }
}
