package com.example.sportsteamwebsiteapi.news;

import com.example.sportsteamwebsiteapi.attachment.Attachment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/news")
@CrossOrigin
public class NewsController {
    private final NewsService newsService;
    
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    
    @PostMapping
    public ResponseEntity<?> addNews(@RequestBody News news) {
        News newNews = newsService.addNews(news);
        return ResponseEntity.created(URI.create("api/news/" + newNews.getNewsId())).body(newNews);
    }
    
    @PostMapping(value = "/{newsId}/addImage")
    public ResponseEntity<?> addImage(
        @PathVariable int newsId,
        @RequestBody Attachment attachment
    ) {
        Attachment newAttachment = newsService.addAttachment(
            newsId,
            attachment,
            "image"
        );
        return ResponseEntity
                   .created(URI.create("api/news/" + newsId + "/attachments"))
                   .body(newAttachment);
    }
    
    @PostMapping(value = "/{newsId}/addVideo")
    public ResponseEntity<?> addVideo(
        @PathVariable int newsId,
        @RequestBody Attachment attachment
    ) {
        Attachment newAttachment = newsService.addAttachment(
            newsId,
            attachment,
            "video"
        );
        return ResponseEntity
                   .created(URI.create("api/news/" + newsId + "/attachments"))
                   .body(newAttachment);
    }
    
    @PutMapping(value = "/{newsId}")
    public ResponseEntity<?> updateNews(
        @PathVariable Integer newsId,
        @RequestBody News news
    ) {
        return ResponseEntity.ok().body(newsService.updateNews(newsId, news));
    }
    
    @GetMapping
    public ResponseEntity<?> getAllPublishedNews(
        @RequestParam(required = false) String from,
        @RequestParam(required = false) String to
    ) {
        return ResponseEntity.ok().body(newsService.getAllPublishedNews(from, to));
    }
    
    @GetMapping("/scheduled")
    public ResponseEntity<?> getAllScheduledNews() {
        return ResponseEntity.ok().body(newsService.getAllScheduledNews());
    }
    
    @GetMapping("/{newsId}")
    public ResponseEntity<?> getNewsById(@PathVariable Integer newsId) {
        return ResponseEntity.ok().body(newsService.getNewsById(newsId));
    }
    
    @GetMapping(value = "/{newsId}/attachments")
    public ResponseEntity<?> getAttachments(@PathVariable Integer newsId) {
        return ResponseEntity.ok().body(newsService.getAttachments(newsId));
    }
    
    @GetMapping(value = "/newsWithImages")
    public ResponseEntity<?> getNewsWithImages() {
        return ResponseEntity.ok().body(newsService.getNewsWithImages());
    }
    
    @GetMapping(value = "/newsWithVideos")
    public ResponseEntity<?> getNewsWithVideos() {
        return ResponseEntity.ok().body(newsService.getNewsWithVideos());
    }
    
    @DeleteMapping(value = "/{newsId}")
    public ResponseEntity<?> deleteNews(@PathVariable Integer newsId) {
        newsService.deleteNews(newsId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping(value = "/{newsId}/deleteAttachment/{attachmentId}")
    public ResponseEntity<?> deleteAttachment(
        @PathVariable Integer newsId,
        @PathVariable Integer attachmentId
    ) {
        newsService.deleteAttachment(newsId, attachmentId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping(value = "/{newsId}/deleteAttachments")
    public ResponseEntity<?> deleteAttachments(@PathVariable Integer newsId) {
        newsService.deleteAttachments(newsId);
        return ResponseEntity.ok().build();
    }
}
