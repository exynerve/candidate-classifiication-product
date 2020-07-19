package com.classifycandidatepro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.classifycandidatepro.model.LicenseAppName;


@Repository
public interface LicenseAppNameRepository extends JpaRepository<LicenseAppName, String> {

}
