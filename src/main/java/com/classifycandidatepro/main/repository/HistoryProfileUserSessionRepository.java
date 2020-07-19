package com.classifycandidatepro.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classifycandidatepro.model.HistoryProfileUserSession;

@Repository
public interface HistoryProfileUserSessionRepository
extends JpaRepository<HistoryProfileUserSession, Long>{

}
