package com.classifycandidatepro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.classifycandidatepro.model.LicenseAppName;
import com.classifycandidatepro.model.LoginModel;
import com.classifycandidatepro.model.UserMaintainId;
import com.classifycandidatepro.model.UserMaintainence;
import com.classifycandidatepro.repository.LicenseAppNameRepository;
import com.classifycandidatepro.repository.UserMaintainenceRepository;
import com.classifycandidatepro.response.AJAXResponse;
import com.classifycandidatepro.response.ErrorMessagesObj;
import com.model.messages.Messages;
import com.utils.LicenseUtils;

@CrossOrigin
@RestController
public class LoginController {

	@Autowired
	private UserMaintainenceRepository userMaintainenceRepository;

	@Autowired
	private LicenseAppNameRepository licenseAppNameRepository;

	@PostMapping("/login")
	public AJAXResponse login(@Valid @RequestBody LoginModel loginModel, Pageable pageable) {

		AJAXResponse ajaxResponse = new AJAXResponse();

		try{
		String appName = loginModel.getAppName();
		if (null == appName) {

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("App License is Mandatory");
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;
		}

		Optional<LicenseAppName> licenseAppName = licenseAppNameRepository.findById(appName);

		if (null == licenseAppName) {

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("App License is Mandatory");
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;

		}

		LicenseAppName licenseAppName2 = licenseAppName.get();

		if (null == licenseAppName2) {

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("App License is Mandatory");
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;

		}

		String expiryDate = licenseAppName2.getExpiryDate();

		if (null == expiryDate) {

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("App License is Mandatory");
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;

		}

		LicenseUtils licenseUtils = new LicenseUtils();

		boolean isLicenseExpired = licenseUtils.checkLicenseExpiration(expiryDate);

		if (isLicenseExpired) {

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("License has Expired");
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;
		}

		String userName = loginModel.getUserName();

		if (null == userName || userName.isEmpty()) {
			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("Username Cannot be Empty");
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;
		}

		String password = loginModel.getPassword();

		if (null == password || password.isEmpty()) {
			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("Password Cannot be Empty");
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;
		}

		if (userName.equals(password)) {
			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("Username and Password cannot be Same");
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;
		}

		UserMaintainId userMaintainId = new UserMaintainId();

		userMaintainId.setAppName(appName);
		userMaintainId.setUserName(userName);

		Optional<UserMaintainence> userMaintainence = userMaintainenceRepository.findById(userMaintainId);

		if (null == userMaintainence.get()) {

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage(Messages.INVALID_USER_PASSWORD_MSG);
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;

		}

		UserMaintainence userMaintainenceTemp = userMaintainence.get();

		String actualPassword = userMaintainenceTemp.getPassword();

		if (!actualPassword.equals(password)) {

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage(Messages.INVALID_USER_PASSWORD_MSG);
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;
		}
		
		ajaxResponse.setStatus(true);
		ajaxResponse.setModel(userMaintainenceTemp);

		}catch(Exception e){
			
			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage(Messages.CRITICAL_ERROR);
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;
		}
		
		

		return ajaxResponse;

	}
}
