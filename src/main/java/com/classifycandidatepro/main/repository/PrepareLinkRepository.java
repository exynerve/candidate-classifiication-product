package com.classifycandidatepro.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classifycandidatepro.storageobjects.CompanyInformationVO;
import com.classifycandidatepro.storageobjects.PrepareLinkVO;

public interface PrepareLinkRepository 
extends JpaRepository<PrepareLinkVO, String>{

}
