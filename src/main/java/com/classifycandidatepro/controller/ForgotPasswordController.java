package com.classifycandidatepro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.classifycandidatepro.model.ForgotPasswordModel;
import com.classifycandidatepro.model.LicenseAppName;
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
public class ForgotPasswordController {

	@Autowired
	private UserMaintainenceRepository userMaintainenceRepository;

	@Autowired
	private LicenseAppNameRepository licenseAppNameRepository;

	@PostMapping("/forgotPassword")
	public AJAXResponse forgotPassword(@Valid @RequestBody ForgotPasswordModel forgotPasswordModel) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			String appName = forgotPasswordModel.getAppName();
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

			String userName = forgotPasswordModel.getUserName();

			if (null == userName || userName.isEmpty()) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage("Username cannot be Empty");
				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);
				return ajaxResponse;

			}

			UserMaintainId userMaintainId = new UserMaintainId();

			userMaintainId.setAppName(appName);
			userMaintainId.setUserName(userName);

			Optional<UserMaintainence> userMaintainence = userMaintainenceRepository.findById(userMaintainId);

			UserMaintainence userMaintainenceTemp = userMaintainence.get();

			if (null == userMaintainenceTemp) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.NO_USER_FOUND);
				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);
				return ajaxResponse;

			}

			String emailId = userMaintainenceTemp.getEmail();

			sendSimpleMessage(emailId, "Dont Worry we always tend to Forget",
					userMaintainenceTemp.getPassword());

		} catch (Exception e) {

			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("Critical Error has Occured in the Application");

			ajaxResponse.setStatus(false);
			ajaxResponse.setErrMessages(errMessages);

			return ajaxResponse;

		}

		ajaxResponse.setSucessMsg("Password has been Send to Mail Id");
		ajaxResponse.setStatus(true);
		return ajaxResponse;

	}

	@Autowired
	public JavaMailSender emailSender;

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

}
