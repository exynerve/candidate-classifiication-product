package com.classifycandidatepro.main.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.classifycandidatepro.main.repository.ClassifyCandidateRepository;
import com.classifycandidatepro.main.repository.CompanyInformationRepository;
import com.classifycandidatepro.main.repository.PrepareLinkRepository;
import com.classifycandidatepro.main.repository.QuestionRepository;
import com.classifycandidatepro.model.AnswerVO;
import com.classifycandidatepro.model.ClassifyInfo;
import com.classifycandidatepro.model.ClusterTypesVO;
import com.classifycandidatepro.model.CompanyNamesVO;
import com.classifycandidatepro.model.LoginModel;
import com.classifycandidatepro.model.PaginationConfigVO;
import com.classifycandidatepro.model.Question;
import com.classifycandidatepro.model.QuestionTypesVO;
import com.classifycandidatepro.model.RatingValues;
import com.classifycandidatepro.response.AJAXResponse;
import com.classifycandidatepro.response.AJAXResponseLogin;
import com.classifycandidatepro.response.ErrorMessagesObj;
import com.classifycandidatepro.response.StatusInfo;
import com.classifycandidatepro.service.ClassifyCandidateService;
import com.classifycandidatepro.storageobjects.CompanyInformationVO;
import com.classifycandidatepro.storageobjects.PrepareLinkVO;
import com.helper.util.HelperUtil;

import com.model.messages.Messages;

@Controller
public class ClassifyCandidateProController {

	@Autowired
	private ClassifyCandidateRepository classifyCandidateRepository;

	@Autowired
	private PrepareLinkRepository prepareLinkRepository;

	@Autowired
	private CompanyInformationRepository companyInformationRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private ClassifyCandidateService classifyCandidateService;

	@RequestMapping(value = "/viewAllCompanyNames", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllCompanyNames() {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			List<CompanyNamesVO> companyNamesVOList = classifyCandidateRepository.retriveCompanyNamesList();

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(companyNamesVOList);

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

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/logout.do")
	public String logout(Model model, HttpServletRequest request) {
		AJAXResponse ajaxResponse = new AJAXResponse();

		try {

			ajaxResponse.setStatus(true);

			HttpSession session = request.getSession(false);
			session.invalidate();
			ajaxResponse.setStatus(true);
			model.addAttribute(Messages.Keys.OBJ, new AJAXResponse());
			return "welcome";
		} catch (Exception e) {
			ajaxResponse.setStatus(true);
			model.addAttribute(Messages.Keys.OBJ, new AJAXResponse());
			return "welcome";
		}

	}

	@RequestMapping("/loginUser")
	public String registerUser(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "login";
	}

	@RequestMapping(value = "/login.do", method = { RequestMethod.POST, RequestMethod.GET })
	public String loginPage(Model model, @ModelAttribute LoginModel loginModel, HttpServletRequest request) {

		String loginPageName = "login";
		String adminPageName = "admin";
		String customerPageName = "customer";
		AJAXResponseLogin ajaxResponse = new AJAXResponseLogin();
		try {

			loginModel.setAppName(Messages.Keys.APP_NAME);
			HttpEntity<LoginModel> request1 = new HttpEntity<>(loginModel);
			ResponseEntity<AJAXResponseLogin> response = restTemplate.exchange(Messages.EndPoints.LOGIN_ENDPOINT,
					HttpMethod.POST, request1, AJAXResponseLogin.class);

			ajaxResponse = response.getBody();

			if (ajaxResponse.isStatus()) {

				HttpSession session = request.getSession(true);
				session.setAttribute(Messages.Keys.LOGINID_SESSION, loginModel.getUserName());
				session.setAttribute(Messages.Keys.LOGINTYPE_SESSION, ajaxResponse.getModel().getLoginType());

				if (ajaxResponse.getModel().getLoginType() == Messages.Keys.ADMIN_TYPE) {

					model.addAttribute(Messages.Keys.OBJ, ajaxResponse);
					return adminPageName;
				} else if (ajaxResponse.getModel().getLoginType() == Messages.Keys.CUSTOMER_TYPE) {

					model.addAttribute(Messages.Keys.OBJ, ajaxResponse);
					return customerPageName;

				}
			}

		} catch (Exception e) {

			model.addAttribute(Messages.Keys.OBJ, ajaxResponse);
			return loginPageName;
		}
		model.addAttribute(Messages.Keys.OBJ, ajaxResponse);
		return loginPageName;

	}

	@RequestMapping(value = "/viewClusterTypes", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllClusterTypes() {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			List<ClusterTypesVO> clusterTypesVO = classifyCandidateRepository.retriveAllClusterTypes();

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(clusterTypesVO);

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

	@RequestMapping(value = "/saveCompanyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse saveCompanyInformation(@RequestBody CompanyInformationVO companyInformationVO) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			Optional<CompanyInformationVO> companyInformationVOList = companyInformationRepository
					.findById(companyInformationVO.getCompanyName());

			if (null == companyInformationVOList || !companyInformationVOList.isPresent()) {

				CompanyInformationVO companyInformationVO1 = companyInformationRepository.save(companyInformationVO);

				if (null == companyInformationVO1) {

					ajaxResponse.setStatus(false);

					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.COULD_NOT_SAVE_COMPANY_INFO);

					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);

					return ajaxResponse;
				} else {

					ajaxResponse.setStatus(true);
					ajaxResponse.setSucessMsg(Messages.SAVE_COMPANY_SUCESSFUL);
					return ajaxResponse;
				}

			} else {

				ajaxResponse.setStatus(false);

				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.COMPANY_NAME_EXISTS);

				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;

			}

		} catch (Exception e) {
			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage(e.getMessage());

			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);

			return ajaxResponse;

		}

	}

	@RequestMapping(value = "/viewCompaniesAllForPagination", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllCompanies(@ModelAttribute PaginationConfigVO paginationConfigVO) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			StatusInfo statusInfo = classifyCandidateRepository
					.retriveAllCompanyInformationPaginationConfig(paginationConfigVO);

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

		}
		return ajaxResponse;

	}

	@RequestMapping(value = "/savePrepareInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse savePrepareInformation(@RequestBody PrepareLinkVO prepareLinkVO) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			Optional<PrepareLinkVO> prepareLinkVOList = prepareLinkRepository.findById(prepareLinkVO.getPrepareName());

			if (null == prepareLinkVOList || !prepareLinkVOList.isPresent()) {

				PrepareLinkVO prepareLinkVO1 = prepareLinkRepository.save(prepareLinkVO);

				if (null == prepareLinkVO1) {

					ajaxResponse.setStatus(false);

					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.COULD_NOT_SAVE_PREPARELINK_INFO);

					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);

					return ajaxResponse;
				} else {

					ajaxResponse.setStatus(true);
					ajaxResponse.setSucessMsg(Messages.SAVE_PREPAREINFO_SUCESSFUL);
					return ajaxResponse;
				}

			} else {

				ajaxResponse.setStatus(false);

				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.PREPARE_NAME_EXISTS);

				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;

			}

		} catch (Exception e) {
			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage(e.getMessage());

			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);

			return ajaxResponse;

		}

	}

	@RequestMapping(value = "/viewQuestionTypes", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllQuestionTypes() {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			List<QuestionTypesVO> questionTypesVO = classifyCandidateRepository.retriveAllQuestionTypes();

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(questionTypesVO);

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

	@RequestMapping(value = "/viewPrepareLinkAllForPagination", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllPrepareLinks(@ModelAttribute PaginationConfigVO paginationConfigVO) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			StatusInfo statusInfo = classifyCandidateRepository
					.retriveAllPrepareLinksPaginationConfig(paginationConfigVO);

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

		}
		return ajaxResponse;

	}

	@RequestMapping(value = "/saveQuestions", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse createQuestionInformation(@RequestBody Question question) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			Optional<Question> questionList = questionRepository.findById(question.getQuestionDesc());

			if (null == questionList || !questionList.isPresent()) {

				Question question1 = questionRepository.save(question);

				if (null == question1) {

					ajaxResponse.setStatus(false);

					List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

					ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
					errorMessagesObj.setErrMessage(Messages.COULD_NOT_SAVE_QUESTION_INFO);

					errMessages.add(errorMessagesObj);
					ajaxResponse.setErrMessages(errMessages);

					return ajaxResponse;
				} else {

					ajaxResponse.setStatus(true);
					ajaxResponse.setSucessMsg(Messages.SAVE_QUESTIONINFO_SUCESSFUL);
					return ajaxResponse;
				}

			} else {

				ajaxResponse.setStatus(false);

				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.QUESDESC_EXISTS);

				errMessages.add(errorMessagesObj);
				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;

			}

		} catch (Exception e) {
			ajaxResponse.setStatus(false);
			List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage(e.getMessage());

			errMessages.add(errorMessagesObj);
			ajaxResponse.setErrMessages(errMessages);

			return ajaxResponse;

		}

	}

	@RequestMapping(value = "/viewQuestionsAllForPagination", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllQuestionsForPagination(
			@ModelAttribute PaginationConfigVO paginationConfigVO) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			StatusInfo statusInfo = classifyCandidateRepository.retriveAllQuestionsForPagination(paginationConfigVO);

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

		}
		return ajaxResponse;

	}

	@RequestMapping(value = "/viewRatings", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllRatings() {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			List<RatingValues> ratingVOList = new ArrayList<RatingValues>();

			RatingValues ratingValues = new RatingValues();
			ratingValues.setRating(1);
			ratingVOList.add(ratingValues);

			ratingValues = new RatingValues();
			ratingValues.setRating(2);
			ratingVOList.add(ratingValues);

			ratingValues = new RatingValues();
			ratingValues.setRating(3);
			ratingVOList.add(ratingValues);

			ratingValues = new RatingValues();
			ratingValues.setRating(4);
			ratingVOList.add(ratingValues);

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(ratingVOList);

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

	@RequestMapping(value = "/viewscreeningtestuser", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView obtainTestForUserFromSession(HttpServletRequest request) {

		ModelAndView modelAndView = null;

		try {
			AJAXResponse ajaxResponse = generateAJAXResponseForAnswersVOUser(request);
			if (ajaxResponse.isStatus()) {
				// The next Step to Load questions
				return new ModelAndView(Messages.Views.VIEW_QUESTIONUSER_PAGE, Messages.Keys.OBJ, ajaxResponse);
			}
		} catch (Exception e) {

			e.printStackTrace();

			System.out.println("Error Message is");
			System.out.println(e.getMessage());

			return new ModelAndView(Messages.Views.VIEW_USER_ERROR_PAGE, Messages.Keys.OBJ, Messages.ADMIN_CONTACT);

		}
		return new ModelAndView(Messages.Views.VIEW_USER_PAGE, Messages.Keys.OBJ, Messages.ADMIN_CONTACT);
	}

	private AJAXResponse generateAJAXResponseForAnswersVOUser(HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		ajaxResponse.setStatus(false);

		HttpSession session = request.getSession(false);

		cleanupQuestionsFromSession(session);

		String userId = (String) session.getAttribute(Messages.Keys.LOGINID_SESSION);

		List<AnswerVO> listQuestions = classifyCandidateService.retriveAllGeneralQuestionsForUser(userId);

		if (isNotEmptyAnswers(listQuestions)) {

			ajaxResponse.setStatus(true);

			session.setAttribute(Messages.SessionIF.QUESTONVO_SESSION, listQuestions);

			Map<Integer, AnswerVO> mapAnswerVO = createMapForAllTests(listQuestions);

			session.setAttribute(Messages.SessionIF.DISPLAY_QUESTIONS, mapAnswerVO);

			ajaxResponse.setModel(listQuestions != null ? listQuestions.get(0) : null);

			ajaxResponse.setStatus(true);

		}
		return ajaxResponse;
	}

	private void cleanupQuestionsFromSession(HttpSession session) {
		session.removeAttribute(Messages.SessionIF.DISPLAY_QUESTIONS);
		session.removeAttribute(Messages.SessionIF.ANSWER_SESSION);
		session.removeAttribute(Messages.SessionIF.QUESTONVO_SESSION);
	}

	private boolean isNotEmptyAnswers(List<AnswerVO> listQuestions) {
		return listQuestions != null && !listQuestions.isEmpty();
	}

	private Map<Integer, AnswerVO> createMapForAllTests(List<AnswerVO> answerVOList) {
		Map<Integer, AnswerVO> map = new HashMap<Integer, AnswerVO>();
		if (isNotEmptyAnswers(answerVOList)) {
			for (AnswerVO answerVO : answerVOList) {
				map.put(answerVO.getQuestion().getPageNumber(), answerVO);
			}
		}
		return map;
	}

	@RequestMapping(value = "/onlineTestUser", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView submitAnswer(@ModelAttribute AnswerVO answerVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			HttpSession session = request.getSession(false);

			AnswerVO answerVO2 = updateAnswers(answerVO, session, answerVO.getPageNumberGlobal());

			Map<Integer, AnswerVO> mapAnswerVO = (Map<Integer, AnswerVO>) session
					.getAttribute(Messages.SessionIF.DISPLAY_QUESTIONS);

			boolean executeClassification = false;
			if (answerVO2 != null) {
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(answerVO2);
				return new ModelAndView(Messages.Views.VIEW_QUESTIONUSER_PAGE, Messages.Keys.OBJ, ajaxResponse);
			} else if (null == answerVO2
					&& (mapAnswerVO != null && answerVO.getQuestion().getPageNumber() == mapAnswerVO.size())) {

				executeClassification = true;

			}

			if (executeClassification) {

				Map<Integer, AnswerVO> answerVOMap = (Map<Integer, AnswerVO>) session
						.getAttribute(Messages.SessionIF.ANSWER_SESSION);

				HelperUtil helperUtil = new HelperUtil();

				ArrayList<Question> questionObj = helperUtil.convertAnswerVOMapToQuestionVOList(answerVOMap);

				ClassifyInfo classifyInfo = classifyCandidateService.processClassifyAndFind(questionObj,
						answerVO.getPatName(), "YES", session.getId());
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(classifyInfo);
				return new ModelAndView(Messages.Views.VIEW_ANALYIS_USER_PAGE, Messages.Keys.OBJ, ajaxResponse);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			List<ErrorMessagesObj> errormessages = new ArrayList<ErrorMessagesObj>();

			ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
			errorMessagesObj.setErrMessage(e.getMessage());
			ajaxResponse.setErrMessages(errormessages);
			ajaxResponse.setStatus(false);

			return new ModelAndView(Messages.Views.VIEW_ANALYIS_USER_PAGE, Messages.Keys.OBJ, ajaxResponse);
		}
		return new ModelAndView(Messages.Views.VIEW_ANALYIS_USER_PAGE, Messages.Keys.OBJ, ajaxResponse);

	}

	private AnswerVO updateAnswers(AnswerVO answerVO, HttpSession session, int pageNumber) {

		AnswerVO answerVONew = null;

		Map<Integer, AnswerVO> answerVOMap = (Map<Integer, AnswerVO>) session
				.getAttribute(Messages.SessionIF.ANSWER_SESSION);

		if (answerVOMap != null && !answerVOMap.isEmpty()) {

			answerVOMap.put(answerVO.getQuestion().getQuesId(), answerVO);
		} else {
			answerVOMap = new HashMap<Integer, AnswerVO>();
			answerVOMap.put(answerVO.getQuestion().getQuesId(), answerVO);
		}

		Map<Integer, AnswerVO> displayAnswers = (Map<Integer, AnswerVO>) session
				.getAttribute(Messages.SessionIF.DISPLAY_QUESTIONS);

		session.setAttribute(Messages.SessionIF.ANSWER_SESSION, answerVOMap);

		if (answerVO.getActionCode().equals("NEXT")) {

			if (displayAnswers != null && !displayAnswers.isEmpty() && pageNumber < displayAnswers.size()) {
				answerVONew = new AnswerVO();
				answerVONew = displayAnswers.get(pageNumber + 1);
			}

		} else if (answerVO.getActionCode().equals("BACK")) {

			if (displayAnswers != null && !displayAnswers.isEmpty() && pageNumber <= 2) {
				answerVONew = new AnswerVO();
				answerVONew = displayAnswers.get(pageNumber - 1);
			}
		}

		return answerVONew;

	}

	@RequestMapping(value = "/viewHistorySpecificUser", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllHistoryForPaginationOfSpecificUser(
			@ModelAttribute PaginationConfigVO paginationConfigVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			HttpSession session = request.getSession(false);

			if (null == session.getAttribute(Messages.Keys.LOGINID_SESSION)) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			String userId = (String) session.getAttribute(Messages.Keys.LOGINID_SESSION);

			if (null == userId) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			StatusInfo statusInfo = classifyCandidateRepository.retriveAllHistoryForPaginationForUser(paginationConfigVO,userId);

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

		}
		return ajaxResponse;

	}
	
	
	@RequestMapping(value = "/viewKNNDistanceSpecificUser", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllKNNForPaginationOfSpecificUser(
			@ModelAttribute PaginationConfigVO paginationConfigVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			HttpSession session = request.getSession(false);

			if (null == session.getAttribute(Messages.Keys.LOGINID_SESSION)) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			String userId = (String) session.getAttribute(Messages.Keys.LOGINID_SESSION);

			if (null == userId) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			StatusInfo statusInfo = classifyCandidateRepository.retriveAllKNNForPaginationForUser(paginationConfigVO,userId);

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

		}
		return ajaxResponse;

	}
	
	@RequestMapping(value = "/viewScoreSpecificUser", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllScoreForPaginationOfSpecificUser(
			@ModelAttribute PaginationConfigVO paginationConfigVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			HttpSession session = request.getSession(false);

			if (null == session.getAttribute(Messages.Keys.LOGINID_SESSION)) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			String userId = (String) session.getAttribute(Messages.Keys.LOGINID_SESSION);

			if (null == userId) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			StatusInfo statusInfo = classifyCandidateRepository.retriveAllScoreForPaginationOfSpecificUser(paginationConfigVO,userId);

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

		}
		return ajaxResponse;

	}
	
	@RequestMapping(value = "/viewTechnicalUserScoreGraph", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllTechnicalScoreGraphSecificUser(HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			HttpSession session = request.getSession(false);

			if (null == session.getAttribute(Messages.Keys.LOGINID_SESSION)) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			String userId = (String) session.getAttribute(Messages.Keys.LOGINID_SESSION);

			if (null == userId) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			StatusInfo statusInfo = classifyCandidateRepository.retriveAllScoreForSpecificUser(userId,Messages.QuestionType.TECHNICAL_FEATURETYPE);

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

		}
		return ajaxResponse;

	}
	
	
	
	@RequestMapping(value = "/viewAptitudeUserScoreGraph", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllAptitudeScoreGraphSecificUser(HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			HttpSession session = request.getSession(false);

			if (null == session.getAttribute(Messages.Keys.LOGINID_SESSION)) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			String userId = (String) session.getAttribute(Messages.Keys.LOGINID_SESSION);

			if (null == userId) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			StatusInfo statusInfo = classifyCandidateRepository.retriveAllScoreForSpecificUser(userId,Messages.QuestionType.APTITUDE_FEATURETYPE);

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

		}
		return ajaxResponse;

	}
	
	
	@RequestMapping(value = "/viewGeneralUserScoreGraph", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllGeneralScoreGraphSecificUser(HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			HttpSession session = request.getSession(false);

			if (null == session.getAttribute(Messages.Keys.LOGINID_SESSION)) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			String userId = (String) session.getAttribute(Messages.Keys.LOGINID_SESSION);

			if (null == userId) {

				ajaxResponse.setStatus(false);
				List<ErrorMessagesObj> errMessages = new ArrayList<ErrorMessagesObj>();

				ErrorMessagesObj errorMessagesObj = new ErrorMessagesObj();
				errorMessagesObj.setErrMessage(Messages.SESSION_EXPIRED);
				errMessages.add(errorMessagesObj);

				ajaxResponse.setErrMessages(errMessages);

				return ajaxResponse;
			}

			StatusInfo statusInfo = classifyCandidateRepository.retriveAllScoreForSpecificUser(userId,Messages.QuestionType.GENERAL_FEATURETYPE);

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

		}
		return ajaxResponse;

	}
	
	@RequestMapping(value = "/viewDataSets", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewDataSets(
			@ModelAttribute PaginationConfigVO paginationConfigVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			StatusInfo statusInfo = classifyCandidateRepository.retriveAllDataSetsForPagination(paginationConfigVO);

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

		}
		return ajaxResponse;

	}
	
	
	@RequestMapping(value = "/viewKNNDistanceAllUser", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllKNNForPagination(
			@ModelAttribute PaginationConfigVO paginationConfigVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

		
			StatusInfo statusInfo = classifyCandidateRepository.retriveAllKNNForPagination(paginationConfigVO);

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

		}
		return ajaxResponse;

	}
	
	@RequestMapping(value = "/viewHistoryAllUsers", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllHistoryForPagination(
			@ModelAttribute PaginationConfigVO paginationConfigVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

		
			StatusInfo statusInfo = classifyCandidateRepository.retriveAllHistoryForPagination(paginationConfigVO);

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

		}
		return ajaxResponse;

	}
	
	
	@RequestMapping(value = "/viewScoreAllUsers", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllScoreForPagination(
			@ModelAttribute PaginationConfigVO paginationConfigVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

		
			StatusInfo statusInfo = classifyCandidateRepository.retriveAllScoreForPagination(paginationConfigVO);

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

		}
		return ajaxResponse;

	}

}
