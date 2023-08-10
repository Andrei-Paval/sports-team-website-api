package com.example.sportsteamwebsiteapi.junior;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/junior")
@CrossOrigin
public class JuniorController {
    private final JuniorService juniorService;
    
    public JuniorController(JuniorService juniorService) {
        this.juniorService = juniorService;
    }
    
    @GetMapping()
    public ResponseEntity<?> getJuniors() {
        return ResponseEntity.ok().body(juniorService.getJuniors());
    }
    
    @GetMapping(value = "{juniorId}")
    public ResponseEntity<?> getJunior(@PathVariable int juniorId) {
        return ResponseEntity.ok().body(juniorService.getJunior(juniorId));
    }
    
    @GetMapping(value = "division/{division}")
    public ResponseEntity<?> getJuniorsByDivision(@PathVariable String division) {
        return ResponseEntity.ok().body(juniorService.getJuniorsByDivision(division));
    }
    
    @GetMapping(value = "{juniorId}/achievements")
    public ResponseEntity<?> getJuniorAchievements(@PathVariable int juniorId) {
        return ResponseEntity.ok().body(juniorService.getJuniorAchievements(juniorId));
    }
    
    @PostMapping
    public ResponseEntity<?> addNewJunior(@RequestBody Junior junior) {
        Junior newJunior = juniorService.addNewJunior(junior);
        return ResponseEntity
                   .created(URI.create("api/junior/" + newJunior.getJuniorId()))
                   .body(newJunior);
    }
    
    @DeleteMapping(value = "{juniorId}")
    public ResponseEntity<?> deleteJunior(@PathVariable int juniorId) {
        juniorService.deleteJunior(juniorId);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping(value = "{juniorId}")
    public ResponseEntity<?> updateJunior(
        @PathVariable int juniorId,
        @RequestBody Junior junior
    ) {
        return ResponseEntity.ok().body(juniorService.updateJunior(juniorId, junior));
    }
}
