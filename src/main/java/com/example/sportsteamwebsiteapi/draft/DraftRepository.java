package com.example.sportsteamwebsiteapi.draft;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DraftRepository extends JpaRepository<Draft, Integer> {
    Iterable<Draft> findAllByOrderByDraftDateDesc();
}
