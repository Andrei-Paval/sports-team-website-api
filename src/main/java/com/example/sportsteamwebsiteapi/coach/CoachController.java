package com.example.sportsteamwebsiteapi.coach;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/coach")
@CrossOrigin
public class CoachController {
    private final CoachService coachService;
    
    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }
    
    @GetMapping
    public ResponseEntity<?> getCoaches() {
        return ResponseEntity.ok().body(coachService.getCoaches());
    }
    
    @GetMapping(value = "{coachId}")
    public ResponseEntity<?> getCoach(@PathVariable int coachId) {
        return ResponseEntity.ok().body(coachService.getCoach(coachId));
    }
    
    @GetMapping(
        value = "image/{coachId}",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<?> getCoachPhoto(@PathVariable int coachId) {
        return ResponseEntity.ok().body(coachService.getCoachPhoto(coachId));
    }
    
    @PostMapping
    public ResponseEntity<?> addNewCoach(@RequestBody Coach coach) {
        Coach newCoach = coachService.addNewCoach(coach);
        return ResponseEntity
                   .created(URI.create("api/coach/" + newCoach.getCoachId()))
                   .body(newCoach);
    }
    
    @DeleteMapping(value = "{coachId}")
    public ResponseEntity<?> deleteCoach(@PathVariable int coachId) {
        coachService.deleteCoach(coachId);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping(value = "{coachId}")
    public ResponseEntity<?> updateCoach(
        @PathVariable int coachId,
        @RequestBody Coach coach
    ) {
        return ResponseEntity.ok().body(coachService.updateCoach(coachId, coach));
    }
}
