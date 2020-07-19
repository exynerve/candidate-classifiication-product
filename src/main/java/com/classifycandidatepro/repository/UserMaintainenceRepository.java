package com.classifycandidatepro.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.classifycandidatepro.model.UserMaintainId;
import com.classifycandidatepro.model.UserMaintainence;



@Repository
public interface UserMaintainenceRepository extends JpaRepository<UserMaintainence, UserMaintainId> {

	Page<UserMaintainence> findByUserMaintainId(UserMaintainId region, Pageable pageable);

	Page<UserMaintainence> findByEmail(String email, Pageable pageable);
	
	Page<UserMaintainence> findByUserMaintainIdAppName(String appName, Pageable pageable);

	Page<UserMaintainence> findByUserMaintainIdAppNameAndLoginType(String appName,int loginType, Pageable pageable);

	@Query(name="select e.userName from UserMaintainence e where e.appName=:appName")
	List<String> findByUserMaintainIdAppName(@Param("appName") String appName);
	
	@Query(name="select e.email from UserMaintainence e where e.appName=:appName and e.email=:email")
	List<String> findByUserMaintainIdAppNameAndEmail(String appName,String email, Pageable pageable);
	
}
