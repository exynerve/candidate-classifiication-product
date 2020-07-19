package com.classifycandidatepro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.classifycandidatepro.model.ServicesModel;
import com.classifycandidatepro.repository.ServicesRepository;


@CrossOrigin
@RestController
public class ServicesController {
	
	@Autowired
	private ServicesRepository servicesRepository;
	
	@GetMapping("/services")
    public Page<ServicesModel> getServices(Pageable pageable) {
        return servicesRepository.findAll(pageable);
    }
	
	@GetMapping("/services/{appName}")
    public Page<ServicesModel> getServicesByAppName(Pageable pageable, @PathVariable String appName) {
        return servicesRepository.findByUserMaintainenceUserMaintainIdAppName(appName,pageable);
    }
	
	
	

}
