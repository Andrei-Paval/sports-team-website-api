package com.example.sportsteamwebsiteapi.junior;

import com.example.sportsteamwebsiteapi.juniorAchievement.JuniorAchievement;
import com.example.sportsteamwebsiteapi.utils.DateValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JuniorService {
    private final JuniorRepository juniorRepository;
    
    public JuniorService(JuniorRepository juniorRepository) {
        this.juniorRepository = juniorRepository;
    }
    
    public List<Junior> getJuniors() {
        return juniorRepository.findAll();
    }
    
    public Junior getJunior(int juniorId) {
        return juniorRepository.findById(juniorId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Junior with id " + juniorId + " does not exist"
            )
        );
    }
    
    public Junior addNewJunior(Junior junior) {
        if (junior.getFirstName() == null || junior.getFirstName().length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        }
        if (junior.getLastName() == null || junior.getLastName().length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last name is required");
        }
        if (
            junior.getBirthDate() == null
            || !DateValidator.isValid(junior.getBirthDate().toString())
        ) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Valid birth date is required"
            );
        }
        if (junior.getDivision() == null || junior.getDivision().length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Division is required");
        }
        return juniorRepository.save(junior);
    }
    
    public void deleteJunior(int juniorId) {
        if (juniorRepository.existsById(juniorId)) {
            juniorRepository.deleteById(juniorId);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Junior with id " + juniorId + " does not exist"
            );
        }
    }
    
    @Transactional
    public Junior updateJunior(int juniorId, Junior updatedJunior) {
        Junior junior = juniorRepository.findById(juniorId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Junior with id " + juniorId + " does not exist"
            )
        );
        if (updatedJunior.getFirstName() != null && updatedJunior.getFirstName().length() != 0) {
            junior.setFirstName(updatedJunior.getFirstName());
        }
        if (updatedJunior.getLastName() != null && updatedJunior.getLastName().length() != 0) {
            junior.setLastName(updatedJunior.getLastName());
        }
        if (
            updatedJunior.getBirthDate() != null
            && DateValidator.isValid(updatedJunior.getBirthDate().toString())
        ) {
            junior.setBirthDate(updatedJunior.getBirthDate());
        }
        if (updatedJunior.getDivision() != null && updatedJunior.getDivision().length() != 0) {
            junior.setDivision(updatedJunior.getDivision());
        }
        
        return junior;
    }
    
    public List<Junior> getJuniorsByDivision(String division) {
        return juniorRepository.findByDivisionIgnoreCase(division);
    }
    
    public List<JuniorAchievement> getJuniorAchievements(int juniorId) {
        Junior junior = juniorRepository.findById(juniorId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Junior with id " + juniorId + " does not exist"
            )
        );
        return junior.getJuniorAchievements();
    }
}
