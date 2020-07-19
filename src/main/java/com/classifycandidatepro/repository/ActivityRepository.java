package com.classifycandidatepro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classifycandidatepro.model.ActivityModel;



@Repository
public interface ActivityRepository extends JpaRepository<ActivityModel, Long> {

}
