package com.example.sportsteamwebsiteapi.draft;

import com.example.sportsteamwebsiteapi.attachment.Attachment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/draft")
@CrossOrigin
public class DraftController {
    private final DraftService draftService;
    
    public DraftController(DraftService draftService) {
        this.draftService = draftService;
    }
    
    @PostMapping
    public ResponseEntity<?> addDraft(@RequestBody Draft draft) {
        Draft newDraft = draftService.addDraft(draft);
        return ResponseEntity
                   .created(URI.create("api/draft/" + newDraft.getDraftId()))
                   .body(newDraft);
    }
    
    @PostMapping(value = "/{draftId}/addImage")
    public ResponseEntity<?> addImage(
        @PathVariable int draftId,
        @RequestBody Attachment attachment
    ) {
        Attachment newAttachment = draftService.addAttachment(
            draftId,
            attachment,
            "image"
        );
        return ResponseEntity
                   .created(URI.create("api/draft/" + draftId + "/attachments"))
                   .body(newAttachment);
    }
    
    @PostMapping(value = "/{draftId}/addVideo")
    public ResponseEntity<?> addVideo(
        @PathVariable int draftId,
        @RequestBody Attachment attachment
    ) {
        Attachment newAttachment = draftService.addAttachment(
            draftId,
            attachment,
            "video"
        );
        return ResponseEntity
                   .created(URI.create("api/draft/" + draftId + "/attachments"))
                   .body(newAttachment);
    }
    
    @GetMapping
    public ResponseEntity<?> getAllDrafts() {
        return ResponseEntity.ok().body(draftService.getAllDrafts());
    }
    
    @GetMapping(value = "/{draftId}")
    public ResponseEntity<?> getDraftById(@PathVariable Integer draftId) {
        return ResponseEntity.ok().body(draftService.getDraftById(draftId));
    }
    
    @GetMapping(value = "/{draftId}/attachments")
    public ResponseEntity<?> getAttachments(@PathVariable Integer draftId) {
        return ResponseEntity.ok().body(draftService.getAttachments(draftId));
    }
    
    @GetMapping(value = "/draftsWithImages")
    public ResponseEntity<?> getDraftsWithImages() {
        return ResponseEntity.ok().body(draftService.getDraftsWithImages());
    }
    
    @GetMapping(value = "/draftsWithVideos")
    public ResponseEntity<?> getDraftsWithVideos() {
        return ResponseEntity.ok().body(draftService.getDraftsWithVideos());
    }
    
    @PutMapping(value = "/{draftId}")
    public ResponseEntity<?> updateDraft(
        @PathVariable Integer draftId,
        @RequestBody Draft updatedDraft
    ) {
        return ResponseEntity.ok().body(draftService.updateDraft(draftId, updatedDraft));
    }
    
    @DeleteMapping(value = "/{draftId}/deleteAttachment/{attachmentId}")
    public ResponseEntity<?> deleteAttachment(
        @PathVariable Integer draftId,
        @PathVariable Integer attachmentId
    ) {
        draftService.deleteAttachment(draftId, attachmentId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping(value = "/{draftId}")
    public ResponseEntity<?> deleteDraft(@PathVariable Integer draftId) {
        draftService.deleteDraft(draftId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping(value = "/{draftId}/deleteAttachments")
    public ResponseEntity<?> deleteAttachments(@PathVariable Integer draftId) {
        draftService.deleteAttachments(draftId);
        return ResponseEntity.ok().build();
    }
}
