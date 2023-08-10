package com.example.sportsteamwebsiteapi.draft;

import com.example.sportsteamwebsiteapi.attachment.Attachment;
import com.example.sportsteamwebsiteapi.attachment.AttachmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DraftService {
    private final DraftRepository draftRepository;
    private final AttachmentRepository attachmentRepository;
    
    public DraftService(
        DraftRepository draftRepository,
        AttachmentRepository attachmentRepository
    ) {
        this.draftRepository = draftRepository;
        this.attachmentRepository = attachmentRepository;
    }
    
    public Draft addDraft(Draft draft) {
        if (draft.getDraftTitle() == null || draft.getDraftTitle().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Draft title is required"
            );
        }
        draft.setDraftDate(LocalDateTime.now());
        draft.setAttachments(null);
        return draftRepository.save(draft);
    }
    
    public void deleteDraft(Integer id) {
        if (draftRepository.existsById(id)) {
            draftRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Draft with id " + id + " does not exist"
            );
        }
    }
    
    public Draft updateDraft(int draftId, Draft updatedDraft) {
        Draft draft = draftRepository.findById(draftId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Draft with id " + draftId + " does not exist"
            )
        );
        if (updatedDraft.getDraftTitle() != null && updatedDraft.getDraftTitle().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Draft title cannot be empty"
            );
        }
        draft.setDraftTitle(updatedDraft.getDraftTitle());
        
        if (updatedDraft.getHashtags() != null) {
            draft.setHashtags(updatedDraft.getHashtags());
        }
        if (updatedDraft.getDescription() != null) {
            draft.setDescription(updatedDraft.getDescription());
        }
        draft.setDraftDate(LocalDateTime.now());
        return draftRepository.save(draft);
    }
    
    public Draft getDraftById(Integer id) {
        return draftRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Draft with id " + id + " does not exist"
            )
        );
    }
    
    public Attachment addAttachment(int draftId, Attachment attachment, String mediaType) {
        if (Objects.equals(mediaType, "image")) {
            if (attachment.getImage() == null || attachment.getImage().length == 0) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Image not found"
                );
            }
        } else if (Objects.equals(mediaType, "video")) {
            if (attachment.getVideoUrl() == null || attachment.getVideoUrl().isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Video not found"
                );
            }
        } else {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid media type"
            );
        }
        
        Draft draft = draftRepository.findById(draftId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Draft with id " + draftId + " does not exist"
            )
        );
        attachment.setAttachmentType(mediaType);
        draft.getAttachments().add(attachment);
        draftRepository.save(draft);
        return attachment;
    }
    
    public Iterable<Draft> getAllDrafts() {
        return draftRepository.findAllByOrderByDraftDateDesc();
    }
    
    public Iterable<Attachment> getAttachments(Integer draftId) {
        Draft draft = draftRepository.findById(draftId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Draft with id " + draftId + " does not exist"
            )
        );
        return draft.getAttachments();
    }
    
    public Iterable<Draft> getDraftsWithImages() {
        List<Draft> drafts = (List<Draft>) draftRepository.findAllByOrderByDraftDateDesc();
        drafts.removeIf(
            draft -> draft.getAttachments().stream().noneMatch(
                attachment -> Objects.equals(attachment.getAttachmentType(), "image")
            )
        );
        return drafts;
    }
    
    public Iterable<Draft> getDraftsWithVideos() {
        List<Draft> drafts = (List<Draft>) draftRepository.findAllByOrderByDraftDateDesc();
        drafts.removeIf(
            draft -> draft.getAttachments().stream().noneMatch(
                attachment -> Objects.equals(attachment.getAttachmentType(), "video")
            )
        );
        return drafts;
    }
    
    public void deleteAttachment(Integer draftId, Integer attachmentId) {
        Draft draft = draftRepository.findById(draftId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Draft with id " + draftId + " does not exist"
            )
        );
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Attachment with id " + attachmentId + " does not exist"
            )
        );
        draft.getAttachments().remove(attachment);
        draftRepository.save(draft);
    }
    
    public void deleteAttachments(Integer draftId) {
        Draft draft = draftRepository.findById(draftId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Draft with id " + draftId + " does not exist"
            )
        );
        draft.getAttachments().clear();
        draftRepository.save(draft);
    }
}
