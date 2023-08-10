package com.example.sportsteamwebsiteapi.news;

import com.example.sportsteamwebsiteapi.attachment.Attachment;
import com.example.sportsteamwebsiteapi.attachment.AttachmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final AttachmentRepository attachmentRepository;
    
    public NewsService(NewsRepository newsRepository, AttachmentRepository attachmentRepository) {
        this.newsRepository = newsRepository;
        this.attachmentRepository = attachmentRepository;
    }
    
    public News addNews(News news) {
        if (news.getNewsTitle() == null || news.getNewsTitle().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "News title cannot be empty"
            );
        }
        if (
            news.getPublicationDate() == null
            || news.getPublicationDate().isBefore(LocalDateTime.now())
        ) {
            news.setPublicationDate(LocalDateTime.now());
        }
        news.setAttachments(null);
        return newsRepository.save(news);
    }
    
    public List<News> getAllPublishedNews(String fromStr, String toStr) {
        List<News> newsList = newsRepository.findAllByOrderByPublicationDateDesc();
        newsList.removeIf(
            news -> news.getPublicationDate().isAfter(LocalDateTime.now())
        );
        if (fromStr == null && toStr == null) {
            return newsList;
        } else {
            LocalDateTime from;
            LocalDateTime to;
            
            if (fromStr != null) {
                try {
                    from = LocalDateTime.parse(fromStr);
                } catch (Exception e) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid date format"
                    );
                }
            } else {
                from = null;
            }
            if (toStr != null) {
                try {
                    to = LocalDateTime.parse(toStr);
                } catch (Exception e) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid date format"
                    );
                }
            } else {
                to = null;
            }
            
            if (from != null && to != null) {
                newsList.removeIf(
                    news -> news.getPublicationDate().isBefore(from)
                            || news.getPublicationDate().isAfter(to)
                );
            } else if (from != null) {
                newsList.removeIf(
                    news -> news.getPublicationDate().isBefore(from)
                );
            } else {
                newsList.removeIf(
                    news -> news.getPublicationDate().isAfter(to)
                );
            }
            return newsList;
        }
    }
    
    public List<News> getAllScheduledNews() {
        List<News> scheduledNewsList = newsRepository.findAllByOrderByPublicationDateAsc();
        scheduledNewsList.removeIf(
            news -> news.getPublicationDate().isBefore(LocalDateTime.now())
        );
        return scheduledNewsList;
    }
    
    public News updateNews(Integer newsId, News updatedNews) {
        News news = newsRepository.findById(newsId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "News with id " + newsId + " does not exist."
            )
        );
        if (updatedNews.getNewsTitle() != null) {
            if (updatedNews.getNewsTitle().isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "News title cannot be empty."
                );
            }
            news.setNewsTitle(updatedNews.getNewsTitle());
        }
        if (updatedNews.getDescription() != null) {
            news.setDescription(updatedNews.getDescription());
        }
        if (
            updatedNews.getPublicationDate() != null
            && updatedNews.getPublicationDate().isAfter(LocalDateTime.now())
        ) {
            news.setPublicationDate(updatedNews.getPublicationDate());
        }
        if (updatedNews.getHashtags() != null) {
            news.setHashtags(updatedNews.getHashtags());
        }
        return newsRepository.save(news);
    }
    
    public void deleteNews(Integer newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "News with id " + newsId + " does not exist."
            );
        }
        newsRepository.deleteById(newsId);
    }
    
    public News getNewsById(Integer newsId) {
        return newsRepository.findById(newsId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "News with id " + newsId + " does not exist."
            )
        );
    }
    
    public Attachment addAttachment(int newsId, Attachment attachment, String mediaType) {
        if (Objects.equals(mediaType, "image")) {
            if (attachment.getImage() == null || attachment.getImage().length == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
            }
        } else if (Objects.equals(mediaType, "video")) {
            if (attachment.getVideoUrl() == null || attachment.getVideoUrl().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media type not valid");
        }
        
        News news = newsRepository.findById(newsId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "News with id " + newsId + " does not exist"
            )
        );
        attachment.setAttachmentType(mediaType);
        news.getAttachments().add(attachment);
        newsRepository.save(news);
        return attachment;
    }
    
    public Iterable<Attachment> getAttachments(Integer newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "News with id " + newsId + " does not exist"
            )
        );
        return news.getAttachments();
    }
    
    public Iterable<News> getNewsWithImages() {
        List<News> newsList = getAllPublishedNews(null, null);
        newsList.removeIf(
            news -> news.getAttachments().stream().noneMatch(
                attachment -> Objects.equals(attachment.getAttachmentType(), "image")
            )
        );
        return newsList;
    }
    
    public Iterable<News> getNewsWithVideos() {
        List<News> newsList = getAllPublishedNews(null, null);
        newsList.removeIf(
            news -> news.getAttachments().stream().noneMatch(
                attachment -> Objects.equals(attachment.getAttachmentType(), "video")
            )
        );
        return newsList;
    }
    
    public void deleteAttachment(Integer newsId, Integer attachmentId) {
        News news = newsRepository.findById(newsId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "News with id " + newsId + " does not exist"
            )
        );
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Attachment with id " + attachmentId + " does not exist"
            )
        );
        news.getAttachments().remove(attachment);
        newsRepository.save(news);
    }
    
    public void deleteAttachments(Integer newsId) {
        News news = newsRepository.findById(newsId).orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "News with id " + newsId + " does not exist"
            )
        );
        news.getAttachments().clear();
        newsRepository.save(news);
    }
}
