package com.classifycandidatepro.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.classifycandidatepro.storageobjects.CompanyInformationVO;

@Repository
public interface CompanyInformationRepository 
extends JpaRepository<CompanyInformationVO, String> {
	
	public List<CompanyInformationVO> findAllByCluster(String cluster);

}
