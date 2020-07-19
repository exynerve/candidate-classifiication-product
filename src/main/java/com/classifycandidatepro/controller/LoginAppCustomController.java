package com.classifycandidatepro.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.classifycandidatepro.model.PaginationConfigVO;
import com.classifycandidatepro.repository.LoginAppCustomRepository;
import com.classifycandidatepro.response.AJAXResponse;
import com.classifycandidatepro.response.ErrorMessagesObj;
import com.classifycandidatepro.response.StatusInfo;
import com.model.messages.Messages;
import com.mysql.fabric.xmlrpc.base.Array;

@CrossOrigin
@RestController
public class LoginAppCustomController {

	@Autowired
	private LoginAppCustomRepository loginAppCustomRepository;

	@RequestMapping(value = "/retriveAllCustomUsersForLoginType/{appName}/{loginType}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse retrivePaginatedUserInformation(
			@ModelAttribute PaginationConfigVO paginatedConfig, HttpServletRequest request,
			@PathVariable String appName, @PathVariable Integer loginType) {

		AJAXResponse ajaxResponse = new AJAXResponse();

		try {
			StatusInfo statusInfo = loginAppCustomRepository.retriveUsersForLoginTypeAndAppNameForPagination(appName,
					loginType, paginatedConfig);

			if (!statusInfo.isStatus()) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(statusInfo.getErrMessage());
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;

			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(statusInfo.getModel());
			ajaxResponse.setTotal(statusInfo.getTotal());
	
		} catch (Exception e) {
			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage(e.getMessage());
			errMessages.add(errorMessagesObj);

			ajaxResponse.setErrMessages(errMessages);

			return ajaxResponse;
		}
		return ajaxResponse;
	}

}
