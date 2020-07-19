package com.classifycandidatepro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classifycandidatepro.model.ServiceTypeModel;


public interface ServiceTypeRepository extends JpaRepository<ServiceTypeModel, Long> {

}
