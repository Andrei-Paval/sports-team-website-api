package com.example.sportsteamwebsiteapi.sponsor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SponsorService {
    private final SponsorRepository sponsorRepository;
    
    public SponsorService(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }
    
    public Sponsor addNewSponsor(Sponsor sponsor) {
        if (sponsor.getSponsorName() == null || sponsor.getSponsorName().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Sponsor name is required"
            );
        }
        if (sponsor.getSponsorWebsite() == null || sponsor.getSponsorWebsite().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Sponsor website is required"
            );
        }
        return sponsorRepository.save(sponsor);
    }
    
    public Iterable<Sponsor> getAllSponsors() {
        return sponsorRepository.findAll();
    }
    
    public Sponsor getSponsorById(int id) {
        return sponsorRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Sponsor with id " + id + " does not exist."
            )
        );
    }
    
    public void deleteSponsorById(int id) {
        if (sponsorRepository.existsById(id)) {
            sponsorRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Sponsor with id " + id + " does not exist."
            );
        }
    }
    
    public void deleteAllSponsors() {
        sponsorRepository.deleteAll();
    }
    
    public Sponsor updateSponsor(int sponsorId, Sponsor updatedSponsor) {
        Sponsor sponsor = sponsorRepository.findById(sponsorId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Sponsor with id " + sponsorId + " does not exist."
            )
        );
        
        if (
            updatedSponsor.getSponsorName() != null
            && !updatedSponsor.getSponsorName().isEmpty()
        ) {
            sponsor.setSponsorName(updatedSponsor.getSponsorName());
        }
        if (
            updatedSponsor.getSponsorWebsite() != null
            && !updatedSponsor.getSponsorWebsite().isEmpty()
        ) {
            sponsor.setSponsorWebsite(updatedSponsor.getSponsorWebsite());
        }
        if (
            updatedSponsor.getSeason() != null
            && !updatedSponsor.getSeason().isEmpty()
        ) {
            sponsor.setSeason(updatedSponsor.getSeason());
        }
        if (updatedSponsor.getSponsorLogo() != null) {
            sponsor.setSponsorLogo(updatedSponsor.getSponsorLogo());
        }
        
        return sponsorRepository.save(sponsor);
    }
}
