package com.classifycandidatepro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classifycandidatepro.model.SubjectsModel;



@Repository
public interface SubjectRepository extends JpaRepository<SubjectsModel, Long> {

}
