package com.classifycandidatepro.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.classifycandidatepro.main.repository.ClassifyCandidateRepository;
import com.classifycandidatepro.model.PaginationConfigVO;
import com.classifycandidatepro.model.ServiceList;
import com.classifycandidatepro.response.AJAXResponse;
import com.classifycandidatepro.response.ErrorMessagesObj;

@RestController
public class ServiceListController {

	@Autowired
	private ClassifyCandidateRepository serviceProviderRepository;

	@RequestMapping(value = "/viewServiceTypes", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveServiceList(HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			List<ServiceList> serviceList = serviceProviderRepository.obtainAllServiceTypeList();
			
			ajaxResponse.setModel(serviceList);
			ajaxResponse.setStatus(true);

		} catch (Exception e) {

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage(e.getMessage());

			errMessages.add(errorMessagesObj);

			ajaxResponse.setErrMessages(errMessages);

		}

		return ajaxResponse;

	}

}
