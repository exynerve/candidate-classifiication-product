package com.classifycandidatepro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.classifycandidatepro.model.ForgotPasswordFree;
import com.classifycandidatepro.model.LicenseAppName;
import com.classifycandidatepro.repository.LicenseAppNameRepository;
import com.classifycandidatepro.response.AJAXResponse;
import com.classifycandidatepro.response.ErrorMessagesObj;
import com.utils.LicenseUtils;


@CrossOrigin
@RestController
public class ForgotPasswordFreeController {

	@Autowired
	private LicenseAppNameRepository licenseAppNameRepository;

	@PostMapping("/forgotPasswordCustom")
	public AJAXResponse forgotPassword(@RequestBody ForgotPasswordFree forgotPasswordModel) {

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
			
			String email= forgotPasswordModel.getMailId();
			if (null == email) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage("Email is Mandatory");
				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);
				return ajaxResponse;
			}
			
			String messageToSend= forgotPasswordModel.getMessageToBeSend();
			if (null == email) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage("Message to be Send is Mandatory");
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
			
			
			sendSimpleMessage(email, "Dont Worry we always tend to Forget",
				messageToSend);

		} catch (Exception e) {

			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("Critical Error has Occured in the Application");
			errMessages.add(errorMessagesObj);
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