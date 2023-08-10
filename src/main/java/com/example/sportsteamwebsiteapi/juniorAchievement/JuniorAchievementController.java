package com.example.sportsteamwebsiteapi.juniorAchievement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/juniorAchievement")
@CrossOrigin
public class JuniorAchievementController {
    private final JuniorAchievementService juniorAchievementService;
    
    public JuniorAchievementController(JuniorAchievementService juniorAchievementService) {
        this.juniorAchievementService = juniorAchievementService;
    }
    
    @PostMapping()
    public ResponseEntity<?> addNewJuniorAchievement(
        @RequestParam int juniorId,
        @RequestBody JuniorAchievement juniorAchievement
    ) {
        JuniorAchievement newJuniorAchievement = juniorAchievementService.addNewJuniorAchievement(
            juniorId,
            juniorAchievement
        );
        return ResponseEntity
                   .created(
                       URI.create(
                           "api/juniorAchievement"
                           + newJuniorAchievement.getJuniorAchievementId()
                       )
                   )
                   .body(newJuniorAchievement);
    }
    
    @GetMapping()
    public ResponseEntity<?> getAllJuniorAchievements() {
        return ResponseEntity.ok().body(juniorAchievementService.getAllJuniorAchievements());
    }
    
    @GetMapping(value = "{juniorAchievementId}")
    public ResponseEntity<?> getJuniorAchievement(@PathVariable int juniorAchievementId) {
        return ResponseEntity
                   .ok()
                   .body(juniorAchievementService.getJuniorAchievement(juniorAchievementId));
    }
    
    @DeleteMapping(value = "{juniorAchievementId}")
    public ResponseEntity<?> deleteJuniorAchievement(@PathVariable int juniorAchievementId) {
        juniorAchievementService.deleteJuniorAchievement(juniorAchievementId);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping(value = "/{juniorAchievementId}")
    public ResponseEntity<?> updateJuniorAchievement(
        @PathVariable int juniorAchievementId,
        @RequestBody JuniorAchievement juniorAchievement
    ) {
        return ResponseEntity
                   .ok()
                   .body(
                       juniorAchievementService.updateJuniorAchievement(
                           juniorAchievementId,
                           juniorAchievement
                       )
                   );
    }
}
