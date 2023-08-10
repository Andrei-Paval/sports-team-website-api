package com.example.sportsteamwebsiteapi.news;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findAllByOrderByPublicationDateDesc();
    
    List<News> findAllByOrderByPublicationDateAsc();
}
