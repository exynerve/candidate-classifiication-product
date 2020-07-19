package com.classifycandidatepro.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.classifycandidatepro.model.ActivityModel;
import com.classifycandidatepro.model.AdditionalConfig;
import com.classifycandidatepro.model.LicenseAppName;
import com.classifycandidatepro.model.ServicesModel;
import com.classifycandidatepro.model.ServicesTempModel;
import com.classifycandidatepro.model.SkillSetModel;
import com.classifycandidatepro.model.SubjectsModel;
import com.classifycandidatepro.model.UserMaintainId;
import com.classifycandidatepro.model.UserMaintainence;
import com.classifycandidatepro.repository.ActivityRepository;
import com.classifycandidatepro.repository.LicenseAppNameRepository;
import com.classifycandidatepro.repository.ServicesRepository;
import com.classifycandidatepro.repository.SkillSetRepository;
import com.classifycandidatepro.repository.SubjectRepository;
import com.classifycandidatepro.repository.UserMaintainenceRepository;
import com.classifycandidatepro.response.AJAXResponse;
import com.classifycandidatepro.response.ErrorMessagesObj;
import com.model.messages.Messages;
import com.utils.DateCompareEnum;
import com.utils.DateUtils;
import com.utils.LicenseUtils;


@CrossOrigin
@RestController
public class UserMaintainenceController {

	public static final String CRITICAL_ERROR = "Critical Error has Occured";

	@Autowired
	private UserMaintainenceRepository userMaintainenceRepository;

	@Autowired
	private LicenseAppNameRepository licenseAppNameRepository;

	@Autowired
	private ServicesRepository servicesRepository;

	@Autowired
	private SkillSetRepository skillSetRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@PostMapping("/register")
	public @ResponseBody AJAXResponse registerUser(@Valid @RequestBody UserMaintainence userMaintain,
			Pageable pageable) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {
			String appName = userMaintain.getUserMaintainId().getAppName();

			if (null == appName || (appName != null && appName.isEmpty())) {
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

			if (null == licenseAppName.get()) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage("App License is Mandatory");
				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);
				return ajaxResponse;

			}

			LicenseAppName licenseAppName2 = licenseAppName.get();
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

			AdditionalConfig additionalConfig = userMaintain.getAdditionalConfig();

			if (additionalConfig.getIsCityRequired() >= 1) {

				String city = userMaintain.getCity();
				if (null == city || (city != null && city.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("City is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsCollegeNameRequired() >= 1) {

				String collegeName = userMaintain.getCollegeName();
				if (null == collegeName || (collegeName != null && collegeName.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("College Name is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsCountryRequired() >= 1) {

				String countryName = userMaintain.getCountry();
				if (null == countryName || (countryName != null && countryName.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Country Name is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsDobRequired() >= 1) {

				String dob = userMaintain.getDob();
				if (null == dob || (dob != null && dob.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Date of Birth is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsSecretAnswer1Required() >= 1) {

				String secretAnswer1 = userMaintain.getSecretAnswer1();
				if (null == secretAnswer1 || (secretAnswer1 != null && secretAnswer1.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Secret Answer1 is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsSecretAnswer2Required() >= 1) {

				String secretAnswer2 = userMaintain.getSecretAnswer2();
				if (null == secretAnswer2 || (secretAnswer2 != null && secretAnswer2.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Secret Answer2 is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsSecretQuestion1Required() >= 1) {

				String secretQuestion1 = userMaintain.getSecretQuestion1();
				if (null == secretQuestion1 || (secretQuestion1 != null && secretQuestion1.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Secret Question1 is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsSecretQuestion2Required() >= 1) {

				String secretQuestion2 = userMaintain.getSecretQuestion2();
				if (null == secretQuestion2 || (secretQuestion2 != null && secretQuestion2.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Secret Question2 is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsStateRequired() >= 1) {

				String state = userMaintain.getState();
				if (null == state || (state != null && state.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("State is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsUniversityRequired() >= 1) {

				String university = userMaintain.getUniversity();
				if (null == university || (university != null && university.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("University is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsUsnNumberRequired() >= 1) {

				String usnNumber = userMaintain.getUsnNumber();
				if (null == usnNumber || (usnNumber != null && usnNumber.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("USN Number is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsAgeRequired() >= 1) {

				int age = userMaintain.getAge();
				if (age <= 12) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Age is Required Field and Must be Minimum 12 years");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsGenderRequired() >= 1) {

				String gender = userMaintain.getGender();
				if (null == gender || (gender != null && gender.isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Gender is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsGenderRequired() >= 1) {

				String gender = userMaintain.getGender();

				List<String> genderValidValues = new ArrayList<String>();

				genderValidValues.add("MALE");
				genderValidValues.add("FEMALE");
				genderValidValues.add("TRANSGENDER");

				if (!genderValidValues.contains(gender)) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Please Provide Valid Gender");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsBranchRequired() >= 1) {

				String branch = userMaintain.getBranch();
				if (null == branch || (branch != null && branch.isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Branch is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;
				}
			}

			if (additionalConfig.getIsProjectTitleRequired() >= 1) {

				String projectTitle = userMaintain.getProjectTitle();
				if (null == projectTitle || (projectTitle != null && projectTitle.isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Project Title is Required Field");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;
				}
			}

			if (additionalConfig.getIsYearOfProjectRequired() >= 1) {

				int year = userMaintain.getYearOfProject();
				if (year < 2010) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Please Send Valid Year");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;
				}
			}

			if (additionalConfig.getIsDobRequired() >= 1) {
				// Additional Validations DOB
				DateUtils dateUtils = new DateUtils();

				Date date = dateUtils.convertGivenDate(userMaintain.getDob());

				if (null == date) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Not a Valid Date for DOB");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

				Date dateTemp = new Date();

				DateCompareEnum dateCompare = dateUtils.compareDates(date, dateTemp);

				if (dateCompare == DateCompareEnum.AFTER || dateCompare == DateCompareEnum.EQUAL
						|| dateCompare == DateCompareEnum.COMPAREERROR) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("DOB Cannot be Today or After Today");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;
				}

			}

			UserMaintainId userMaintainId = new UserMaintainId();

			userMaintainId.setAppName(appName);
			userMaintainId.setUserName(userMaintain.getUserMaintainId().getUserName());

			Optional<UserMaintainence> userValue = userMaintainenceRepository.findById(userMaintainId);

			if (userValue.isPresent()) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage("User Name Already Exist Please use diffrent User Name");
				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);
				return ajaxResponse;
			}

			List<String> emails = userMaintainenceRepository.findByUserMaintainIdAppNameAndEmail(
					userMaintain.getUserMaintainId().getAppName(), userMaintain.getEmail(), pageable);

			if (emails != null && !emails.isEmpty()) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage("Email Already Exist Please use diffrent Email");
				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);
				return ajaxResponse;
			}

			// Now Putting the Logic of Skill Set

			if (additionalConfig.getIsSkillSetRequired() >= 1) {

				if (null == userMaintain.getSkillSetNames()
						|| userMaintain.getSkillSetNames() != null && userMaintain.getSkillSetNames().isEmpty()) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Skill Set Names are Required");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			// Now putting the Logic of Services Set with Cost

			if (additionalConfig.getIsServicesRequired() >= 1) {

				if (null == userMaintain.getServices()
						|| (userMaintain.getServices() != null && userMaintain.getServices().isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Services are Required");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;
				}

			}

			// Now putting the logic of Company Name
			if (additionalConfig.getIsCompanyNameRequired() >= 1) {

				if (null == userMaintain.getCompanyName()
						|| (userMaintain.getCompanyName() != null && userMaintain.getCompanyName().isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Company Name is Required");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			// Now putting the logic of Description

			if (additionalConfig.getIsDescriptionRequired() >= 1) {

				if (null == userMaintain.getDescription()
						|| (userMaintain.getDescription() != null && userMaintain.getDescription().isEmpty())) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Description is Required");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			// Now putting Logic of Total Cost for All Services
			if (additionalConfig.getIsTotalCostForAllServicesRequired() >= 1) {

				if (userMaintain.getTotalCostAllServices() <= 0) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Total Cost is Required and must be higher than 0");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			// Now putting Logic of Total Cost for All Services
			if (additionalConfig.getIsTotalRatingRequired() >= 1) {

				if (userMaintain.getTotalRating() < 0) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Total Rating is Required and cannot be less than 0");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			// Find
			if (additionalConfig.getIsStreetAddress1Required() >= 1) {

				if (null == userMaintain.getStreetAddressLine1() || (userMaintain.getStreetAddressLine1() != null
						&& userMaintain.getStreetAddressLine1().isEmpty()))
					ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage("Street Address 1 is required");
				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);
				return ajaxResponse;

			}

			if (additionalConfig.getIsStreetAddress2Required() >= 1) {

				if (null == userMaintain.getStreetAddressLine2() || (userMaintain.getStreetAddressLine2() != null
						&& userMaintain.getStreetAddressLine2().isEmpty()))
					ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage("Street Address 2 is required");
				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);
				return ajaxResponse;

			}

			if (additionalConfig.getIsServiceTypeRequired() >= 1) {

				if (null == userMaintain.getServiceType()
						|| (userMaintain.getServiceType() != null && userMaintain.getServiceType().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage("Service Type is required");
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsDegreeRequired() >= 1) {

				if (null == userMaintain.getDegree()
						|| (userMaintain.getDegree() != null && userMaintain.getDegree().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.DEGREE_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsSpecificationRequired() >= 1) {

				if (null == userMaintain.getSpecification()
						|| (userMaintain.getSpecification() != null && userMaintain.getSpecification().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.SPECIFICATION_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsAddressRequired() >= 1) {

				if (null == userMaintain.getAddress()
						|| (userMaintain.getAddress() != null && userMaintain.getAddress().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.ADDRESS_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsPinCodeRequired() >= 1) {

				if (null == userMaintain.getPinCode()
						|| (userMaintain.getPinCode() != null && userMaintain.getPinCode().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.PINCODE_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

				try {

					new Integer(userMaintain.getPinCode());

				} catch (Exception e) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.PINCODE_NUMERIC_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsFatherNameRequired() >= 1) {

				if (null == userMaintain.getFatherName()
						|| (userMaintain.getFatherName() != null && userMaintain.getFatherName().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.FATHERNAME_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsFatherNumberRequired() >= 1) {

				if (null == userMaintain.getFatherNumber()
						|| (userMaintain.getFatherNumber() != null && userMaintain.getFatherNumber().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.FATHERNUMBER_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

				try {

					new Integer(userMaintain.getFatherNumber());

				} catch (Exception e) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.FATHERNUMBER_NUMERIC_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsFatherEmailRequired() >= 1) {

				if (null == userMaintain.getFatherEmail()
						|| (userMaintain.getFatherEmail() != null && userMaintain.getFatherEmail().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.FATHEREMAIL_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsFatherEmailRequired() >= 1) {

				if (null == userMaintain.getFatherEmail()
						|| (userMaintain.getFatherEmail() != null && userMaintain.getFatherEmail().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.FATHEREMAIL_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsMotherNameRequired() >= 1) {

				if (null == userMaintain.getMotherName()
						|| (userMaintain.getMotherName() != null && userMaintain.getMotherName().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.MOTHERNAME_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsMotherNameRequired() >= 1) {

				if (null == userMaintain.getMotherNumber()
						|| (userMaintain.getMotherNumber() != null && userMaintain.getMotherNumber().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.MOTHERNUMBER_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

				try {

					new Integer(userMaintain.getMotherNumber());

				} catch (Exception e) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.MOTHERNUMBER_NUMERIC_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}
			}

			if (additionalConfig.getIsMotherEmailRequired() >= 1) {

				if (null == userMaintain.getMotherEmail()
						|| (userMaintain.getMotherEmail() != null && userMaintain.getMotherEmail().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.MOTHEREMAIL_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsLocalGuardNameRequired() >= 1) {

				if (null == userMaintain.getLocalGuardName()
						|| (userMaintain.getLocalGuardName() != null && userMaintain.getLocalGuardName().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.GUARDIANNAME_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsLocalGuardEmailRequired() >= 1) {

				if (null == userMaintain.getLocalGuardEmail()
						|| (userMaintain.getLocalGuardEmail() != null && userMaintain.getLocalGuardEmail().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.GUARDIANEMAIL_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsLocalGuardNumberRequired() >= 1) {

				if (null == userMaintain.getLocalGuardNumber() || (userMaintain.getLocalGuardNumber() != null
						&& userMaintain.getLocalGuardNumber().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.GUARDIANNUMBER_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

				try {

					new Integer(userMaintain.getLocalGuardNumber());

				} catch (Exception e) {

					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.GUARDNUMBER_NUMERIC_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsAdmissionTypeRequired() >= 1) {

				if (null == userMaintain.getAdmissionType()
						|| (userMaintain.getAdmissionType() != null && userMaintain.getAdmissionType().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.ADMISSIONTYPE_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsChallanNumberRequired() >= 1) {

				if (null == userMaintain.getChallanNumber()
						|| (userMaintain.getChallanNumber() != null && userMaintain.getChallanNumber().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.CHALLANNUMBER_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsFeePaidRequired() >= 1) {

				if (userMaintain.getFeePaid() <= 0) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.FEEPAID_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsResidentialAddressRequired() >= 1) {

				if (null == userMaintain.getResidentialAddress() || (userMaintain.getResidentialAddress() != null
						&& userMaintain.getResidentialAddress().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.RESIDENCE_ADDRESS_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsResidenceStatusRequired() >= 1) {

				if (null == userMaintain.getResidenceStatus()
						|| (userMaintain.getResidenceStatus() != null && userMaintain.getResidenceStatus().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.RESIDENCE_STATUS_REQUIRED_ERROR);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsSemesterMarksRequired() >= 1) {

				if (userMaintain.getSemesterMarks() <= 0) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.SEMESTER_MARKS_REQUIRED);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsSubjectsRequired() >= 1) {

				if (null == userMaintain.getSubjects()
						|| (userMaintain.getSubjects() != null && userMaintain.getSubjects().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.SUBJECT_LIST_REQUIRED);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsActivityRequired() >= 1) {

				if (null == userMaintain.getActivities()
						|| (userMaintain.getActivities() != null && userMaintain.getActivities().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.ACTIVITY_LIST_REQUIRED);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			if (additionalConfig.getIsDepartmentRequired() >= 1) {

				if (null == userMaintain.getDepartment()
						|| (userMaintain.getDepartment() != null && userMaintain.getDepartment().isEmpty())) {
					ajaxResponse.setStatus(false);
					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.DEPT_REQUIRED);
					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);
					return ajaxResponse;

				}

			}

			UserMaintainence res = userMaintainenceRepository.save(userMaintain);

			if (res != null) {

				if (additionalConfig.getIsSkillSetRequired() >= 1) {

					List<SkillSetModel> skillSetModels = new ArrayList<SkillSetModel>();

					for (String skillName : userMaintain.getSkillSetNames()) {

						SkillSetModel skillSetModel = new SkillSetModel();
						skillSetModel.setSkillName(skillName);
						skillSetModel.setUserMaintainence(res);

						skillSetModels.add(skillSetModel);

					}
					// save the Skills Set
					skillSetRepository.saveAll(skillSetModels);

				}

				if (additionalConfig.getIsServicesRequired() >= 1) {

					List<ServicesModel> servicesModels = new ArrayList<ServicesModel>();
					for (ServicesTempModel servicesTempModel : userMaintain.getServices()) {
						ServicesModel servicesModelT = new ServicesModel();
						servicesModelT.setServiceName(servicesTempModel.getServiceName());
						servicesModelT.setCost(servicesTempModel.getServiceCost());
						servicesModelT.setUserMaintainence(res);
						servicesModelT.setCompanyNameService(res.getCompanyName());
						servicesModels.add(servicesModelT);
					}

					// Save the Services Cost
					servicesRepository.saveAll(servicesModels);

				}

				if (additionalConfig.getIsActivityRequired() >= 1) {

					List<ActivityModel> activityModels = new ArrayList<ActivityModel>();
					for (String activityModel : userMaintain.getActivities()) {

						ActivityModel activityModel2 = new ActivityModel();

						activityModel2.setActivityName(activityModel);

						activityModels.add(activityModel2);
					}

					// Save the Activities Cost
					activityRepository.saveAll(activityModels);

				}

				// Saving the Subjects

				if (additionalConfig.getIsSubjectsRequired() >= 1) {

					List<SubjectsModel> subjectModels = new ArrayList<SubjectsModel>();
					for (String subject : userMaintain.getSubjects()) {

						SubjectsModel subjectModel = new SubjectsModel();

						subjectModel.setSubjectName(subject);

						subjectModels.add(subjectModel);
					}

					// Save the Subjects
					subjectRepository.saveAll(subjectModels);

				}

				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(res);
				return ajaxResponse;
			} else {
				ajaxResponse.setStatus(false);
				return ajaxResponse;
			}

		} catch (Exception e) {

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage(CRITICAL_ERROR);
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);

			return ajaxResponse;

		}
	}

	@GetMapping("/register")
	public Page<UserMaintainence> getLicenseApp(Pageable pageable) {
		return userMaintainenceRepository.findAll(pageable);
	}

	@GetMapping("/register/{appName}/{userName}")
	public Optional<UserMaintainence> getRegisterInfo(Pageable pageable, @PathVariable String appName,
			@PathVariable String userName) {

		UserMaintainId userMaintainId = new UserMaintainId();

		userMaintainId.setAppName(appName);
		userMaintainId.setUserName(userName);

		return userMaintainenceRepository.findById(userMaintainId);
	}

	@GetMapping("/register/{appName}")
	public Page<UserMaintainence> getAllRegisterInfo(@PathVariable String appName) {

		UserMaintainId userMaintainId = new UserMaintainId();

		userMaintainId.setAppName(appName);

		Pageable pageRequest = createPageRequest();

		Page<UserMaintainence> listUsers = userMaintainenceRepository.findByUserMaintainIdAppName(appName, pageRequest);

		if (null == listUsers || (listUsers != null && !listUsers.hasContent())) {

			System.out.println("List of Users");
			return null;
		}

		return listUsers;

	}

	@GetMapping("/findUsersByLoginType/{appName}/{loginType}")
	public Page<UserMaintainence> getAllRegisterInfoForLoginType(Pageable pageable, @PathVariable String appName,
			@PathVariable Integer loginType) {

		UserMaintainId userMaintainId = new UserMaintainId();

		userMaintainId.setAppName(appName);

		return userMaintainenceRepository.findByUserMaintainIdAppNameAndLoginType(appName, loginType, pageable);
	}

	@GetMapping("/findAllUsersForAppName/{appName}")
	public Page<UserMaintainence> findAllUsersForAppName(@PathVariable String appName) {

		UserMaintainId userMaintainId = new UserMaintainId();

		userMaintainId.setAppName(appName);

		Pageable pageRequest = createPageRequest();

		Page<UserMaintainence> listUsers = userMaintainenceRepository.findByUserMaintainIdAppName(appName, pageRequest);

		return listUsers;

	}

	@GetMapping("/findUserNamesForAppName/{appName}")
	public AJAXResponse findAllUserNamesForAppName(@PathVariable String appName) {
		AJAXResponse ajaxResponse = new AJAXResponse();

		try {
			List<String> list = userMaintainenceRepository.findByUserMaintainIdAppName(appName);

			if (list != null && !list.isEmpty()) {
				ajaxResponse.setModel(list);
				ajaxResponse.setStatus(true);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();
			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage("No Users Found");
			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;
		} catch (Exception e) {

			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();

			errorMessagesObj.setErrMessage(e.getMessage());
			errMessages.add(errorMessagesObj);
			ajaxResponse.setStatus(false);
			ajaxResponse.setErrMessages(errMessages);
			return ajaxResponse;
		}
	}

	@SuppressWarnings("deprecation")
	private Pageable createPageRequest() {
		return new PageRequest(0, 1000);
	}

}
