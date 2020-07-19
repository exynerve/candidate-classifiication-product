package com.classifycandidatepro.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.classifycandidatepro.model.ServiceTypeModel;
import com.classifycandidatepro.repository.ServiceTypeRepository;
import com.exception.ResourceNotFoundException;


@CrossOrigin
@RestController
public class ServiceTypeController {

	@Autowired
	private ServiceTypeRepository serviceTypeRepository; 

	@PostMapping("/servicetype")
	public ServiceTypeModel saveServiceType(@Valid @RequestBody ServiceTypeModel serviceTypeModel) {
		return serviceTypeRepository.save(serviceTypeModel);
	}

	@GetMapping("/servicetype")
    public Page<ServiceTypeModel> getAllServiceTypes(Pageable pageable) {
        return serviceTypeRepository.findAll(pageable);
    }
	
	@DeleteMapping("/servicetype/{servicetypeId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long servicetypeId) {
        return serviceTypeRepository.findById(servicetypeId)
                .map(question -> {
                	serviceTypeRepository.deleteById(servicetypeId);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Service Type not found with id " + servicetypeId));
    }
	
}
