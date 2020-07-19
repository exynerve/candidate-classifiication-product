package com.classifycandidatepro.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.classifycandidatepro.model.LicenseAppName;
import com.classifycandidatepro.repository.LicenseAppNameRepository;


@CrossOrigin
@RestController
public class LicenseAppNameController {

	@Autowired
	private LicenseAppNameRepository licenseAppNameRepository;

	@PostMapping("/license")
	public LicenseAppName saveLicenseApp(@Valid @RequestBody LicenseAppName licenseAppName) {
		return licenseAppNameRepository.save(licenseAppName);
	}

	@GetMapping("/license")
    public Page<LicenseAppName> getLicenseApp(Pageable pageable) {
        return licenseAppNameRepository.findAll(pageable);
    }
	
	
}
