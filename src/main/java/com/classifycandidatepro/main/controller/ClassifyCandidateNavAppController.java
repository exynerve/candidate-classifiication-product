package com.classifycandidatepro.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.classifycandidatepro.response.AJAXResponseLogin;
import com.model.messages.Messages;

@Controller
public class ClassifyCandidateNavAppController {

	@RequestMapping("/")
	public String homePage(Model model) {
		return "welcome";
	}

	@RequestMapping("/registerServicePage")
	public String registerServiceProviderPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "registerserviceprovider";
	}

	@RequestMapping("/registerPage")
	public String registerPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "registeruser";
	}

	@RequestMapping("/adminPage")
	public String viewAdminHomePage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "admin";
	}

	@RequestMapping("/customerPage")
	public String viewUserHomePage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "customer";
	}

	@RequestMapping("/providerPage")
	public String viewProviderHomePage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "provider";
	}

	@RequestMapping("/viewUsersPage")
	public String viewUserPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewusers";
	}

	@RequestMapping("/viewProvidersPage")
	public String viewProvidersPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewproviders";
	}

	@RequestMapping("/viewProcessedDataPage")
	public String viewProcessedDataPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewprocesseddatapage";
	}

	@RequestMapping("/navratingservices")
	public String providerRatingPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "ratingserviceprovider";
	}

	@RequestMapping("/navViewRatingsAll")
	public String navViewRatingsAllPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewratingall";
	}

	@RequestMapping("/navViewTotalRatingsAll")
	public String navViewTotalRatingsAll(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewratingtotalall";
	}

	@RequestMapping("/navproviderecommendations")
	public String navProviderRecommendations(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "providerrecommendations";
	}

	@RequestMapping("/providersSearchPage")
	public String providerSearcchPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "searchprovider";
	}

	@RequestMapping("/changePasswordAdmin")
	public String changePasswordAdminPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "changepasswordadmin";
	}

	@RequestMapping("/changePasswordUser")
	public String changePasswordUserPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "changepassworduser";
	}

	@RequestMapping("/changePasswordProvider")
	public String changePasswordProviderPage(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "changepasswordprovider";
	}

	@RequestMapping("/navCompanyName")
	public String navCompanyName(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "companyname";
	}

	@RequestMapping("/navViewCompanies")
	public String navViewCompanies(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewcompanies";
	}

	@RequestMapping("/navPrepareLink")
	public String navPrepareLink(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "preparelink";
	}

	@RequestMapping("/navViewPrepareLinks")
	public String navViewPrepareLinks(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewpreparelinks";
	}

	@RequestMapping("/navviewpreparelinkscandidate")
	public String navViewPrepareLinksCandidate(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewpreparelinkscandidate";
	}

	@RequestMapping("/navQuestionLink")
	public String navCreateQuestion(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "savequestion";
	}

	@RequestMapping("/navViewQuestionLinks")
	public String navViewQuestions(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewquestions";
	}

	@RequestMapping("/navcustomerhome")
	public String navCustomerHome(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "customer";
	}
	
	
	@RequestMapping("/viewhistoryusernav")
	public String navViewHistoryHome(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewhistoryuser";
	}

	
	
	@RequestMapping("/viewknndistanceusernav")
	public String navViewKNNUserDetails(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewknnuser";
	}
	
	
	
	@RequestMapping("/viewscoreusernav")
	public String navViewSccoreUserDetails(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewscoreuser";
	}
	
	
	@RequestMapping("/viewtechnicaluserscorenav")
	public String navtechnicaluserSccoreNav(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewtechnicaluserscore";
	}
	
	@RequestMapping("/viewaptitudeuserscorenav")
	public String navaptitudeuserSccoreNav(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewaptitudeuserscore";
	}
	
	@RequestMapping("/viewgeneraluserscorenav")
	public String navgeneraluserSccoreNav(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewgeneraluserscore";
	}
	
	@RequestMapping("/viewdatasetsnav")
	public String navviewdatasets(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewdatasets";
	}
	
	@RequestMapping("/viewknnallusersnav")
	public String navViewKNNDetails(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewknnall";
	}
	
	
	
	@RequestMapping("/viewscoreallusersnav")
	public String navViewScoreDetails(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewscoreall";
	}
	
	@RequestMapping("/viewhistoryallusersnav")
	public String navViewHistoryDetails(Model model) {
		model.addAttribute(Messages.Keys.OBJ, new AJAXResponseLogin());
		return "viewhistoryuserall";
	}
	
	
}
