package com.example.sportsteamwebsiteapi.juniorAchievement;

import com.example.sportsteamwebsiteapi.junior.Junior;
import com.example.sportsteamwebsiteapi.junior.JuniorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JuniorAchievementService {
    private final JuniorAchievementRepository juniorAchievementRepository;
    private final JuniorRepository juniorRepository;
    
    public JuniorAchievementService(
        JuniorAchievementRepository juniorAchievementRepository,
        JuniorRepository juniorRepository
    ) {
        this.juniorAchievementRepository = juniorAchievementRepository;
        this.juniorRepository = juniorRepository;
    }
    
    public JuniorAchievement addNewJuniorAchievement(
        int juniorId,
        JuniorAchievement juniorAchievement
    ) {
        Junior junior = juniorRepository.findById(juniorId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Junior with id " + juniorId + " does not exist."
            )
        );
        juniorAchievement.setJunior(junior);
        
        if (juniorAchievement.getAchievementDate() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Achievement date is required"
            );
        }
        if (
            juniorAchievement.getAchievement() == null
            || juniorAchievement.getAchievement().isEmpty()
        ) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Achievement type is required"
            );
        }
        
        return juniorAchievementRepository.save(juniorAchievement);
    }
    
    public Iterable<JuniorAchievement> getAllJuniorAchievements() {
        return juniorAchievementRepository.findAll();
    }
    
    public JuniorAchievement getJuniorAchievement(int juniorAchievementId) {
        return juniorAchievementRepository
                   .findById(juniorAchievementId)
                   .orElseThrow(
                       () -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                           "Junior achievement with id " + juniorAchievementId + " does not exist."
                       )
                   );
    }
    
    public void deleteJuniorAchievement(int juniorAchievementId) {
        juniorAchievementRepository.deleteById(juniorAchievementId);
    }
    
    
    public JuniorAchievement updateJuniorAchievement(
        int juniorAchievementId,
        JuniorAchievement updatedJuniorAchievement
    ) {
        JuniorAchievement juniorAchievement =
            juniorAchievementRepository
                .findById(juniorAchievementId)
                .orElseThrow(
                    () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Junior achievement with id " + juniorAchievementId + " does not exist."
                    )
                );
        
        if (
            updatedJuniorAchievement.getAchievementDate() != null
            && updatedJuniorAchievement.getAchievement() != null
            && !updatedJuniorAchievement.getAchievement().isEmpty()
        ) {
            juniorAchievement.setAchievementDate(updatedJuniorAchievement.getAchievementDate());
            juniorAchievement.setAchievement(updatedJuniorAchievement.getAchievement());
        }
        return juniorAchievementRepository.save(juniorAchievement);
    }
}
