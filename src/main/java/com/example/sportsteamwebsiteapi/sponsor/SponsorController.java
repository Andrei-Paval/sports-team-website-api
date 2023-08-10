package com.example.sportsteamwebsiteapi.sponsor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/sponsor")
@CrossOrigin
public class SponsorController {
    private final SponsorService sponsorService;
    
    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }
    
    @PostMapping
    public ResponseEntity<?> addNewSponsor(@RequestBody Sponsor sponsor) {
        Sponsor newSponsor = sponsorService.addNewSponsor(sponsor);
        return ResponseEntity
                   .created(URI.create("api/sponser/" + newSponsor.getSponsorId()))
                   .body(newSponsor);
    }
    
    @GetMapping
    public ResponseEntity<?> getAllSponsors() {
        return ResponseEntity.ok().body(sponsorService.getAllSponsors());
    }
    
    @GetMapping(value = "/{sponsorId}")
    public ResponseEntity<?> getSponsorById(@PathVariable int sponsorId) {
        return ResponseEntity.ok().body(sponsorService.getSponsorById(sponsorId));
    }
    
    @DeleteMapping(value = "/{sponsorId}")
    public ResponseEntity<?> deleteSponsorById(@PathVariable int sponsorId) {
        sponsorService.deleteSponsorById(sponsorId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping
    public ResponseEntity<?> deleteAllSponsors() {
        sponsorService.deleteAllSponsors();
        return ResponseEntity.ok().build();
    }
    
    @PutMapping(value = "/{sponsorId}")
    public ResponseEntity<?> updateSponsor(
        @PathVariable int sponsorId,
        @RequestBody Sponsor sponsor
    ) {
        return ResponseEntity.ok().body(sponsorService.updateSponsor(sponsorId, sponsor));
    }
}
