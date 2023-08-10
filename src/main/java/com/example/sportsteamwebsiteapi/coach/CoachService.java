package com.example.sportsteamwebsiteapi.coach;

import com.example.sportsteamwebsiteapi.utils.DateValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CoachService {
    private final CoachRepository coachRepository;
    
    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }
    
    public List<Coach> getCoaches() {
        return coachRepository.findAll();
    }
    
    public Coach getCoach(int coachId) {
        return coachRepository.findById(coachId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Coach with id " + coachId + " does not exist"
            )
        );
    }
    
    public byte[] getCoachPhoto(int coachId) {
        Coach coach = coachRepository.findById(coachId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Coach with id " + coachId + " does not exist"
            )
        );
        return coach.getPhoto();
    }
    
    public Coach addNewCoach(Coach coach) {
        if (coach.getFirstName() == null || coach.getFirstName().length() == 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "First name is required"
            );
        }
        if (coach.getLastName() == null || coach.getLastName().length() == 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Last name is required"
            );
        }
        if (coach.getNationality() == null || coach.getNationality().length() == 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Nationality is required"
            );
        }
        if (
            coach.getBirthDate() == null
            || !DateValidator.isValid(coach.getBirthDate().toString())
        ) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Valid birth date is required"
            );
        }
        return coachRepository.save(coach);
    }
    
    public void deleteCoach(int coachId) {
        if (!coachRepository.existsById(coachId)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Coach with id " + coachId + " does not exist"
            );
        }
        coachRepository.deleteById(coachId);
    }
    
    @Transactional
    public Coach updateCoach(int coachId, Coach updatedCoach) {
        Coach coach = coachRepository.findById(coachId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Coach with id " + coachId + " does not exist"
            )
        );
        
        if (updatedCoach.getFirstName() != null && updatedCoach.getFirstName().length() != 0) {
            coach.setFirstName(updatedCoach.getFirstName());
        }
        if (updatedCoach.getLastName() != null && updatedCoach.getLastName().length() != 0) {
            coach.setLastName(updatedCoach.getLastName());
        }
        if (updatedCoach.getNationality() != null && updatedCoach.getNationality().length() != 0) {
            coach.setNationality(updatedCoach.getNationality());
        }
        if (
            updatedCoach.getBirthDate() != null
            && DateValidator.isValid(updatedCoach.getBirthDate().toString())
        ) {
            coach.setBirthDate(updatedCoach.getBirthDate());
        }
        if (updatedCoach.getPhoto() != null) {
            coach.setPhoto(updatedCoach.getPhoto());
        }
        if (updatedCoach.getDescription() != null && updatedCoach.getDescription().length() != 0) {
            coach.setDescription(updatedCoach.getDescription());
        }
        
        return coach;
    }
}
