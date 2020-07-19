package com.model.messages;

public interface Messages {

	String CRITICAL_ERROR = "Critical Error has Occured";
	String DEGREE_REQUIRED_ERROR = "Degree is Required";
	String SPECIFICATION_REQUIRED_ERROR = "Specification is Required";
	String ADDRESS_REQUIRED_ERROR = "Address is Required";
	String PINCODE_REQUIRED_ERROR = "Pin Code is Required";
	String PINCODE_NUMERIC_ERROR = "Pin Code must be Numeric";
	String FATHERNAME_REQUIRED_ERROR = "Father Name is Required";
	String FATHERNUMBER_REQUIRED_ERROR = "Father Number is Required";
	String FATHERNUMBER_NUMERIC_ERROR = "Father Number is Numeric";
	String FATHEREMAIL_REQUIRED_ERROR = "Father Email is Required";
	String MOTHERNAME_REQUIRED_ERROR = "Mother Name is Required";
	String MOTHERNUMBER_REQUIRED_ERROR = "Mother Number is Required";
	String MOTHERNUMBER_NUMERIC_ERROR = "Mother Number is Numeric";
	String MOTHEREMAIL_REQUIRED_ERROR = "Mother Email is Required";
	String GUARDIANNAME_REQUIRED_ERROR = "Guardian Name is Required";
	String GUARDIANEMAIL_REQUIRED_ERROR = "Guardian Email is Required";
	String GUARDIANNUMBER_REQUIRED_ERROR = "Guardian Number is Required";
	String GUARDNUMBER_NUMERIC_ERROR = "Guardian Number must be Numeric";
	String ADMISSIONTYPE_REQUIRED_ERROR = "Admission Type is Required";
	String CHALLANNUMBER_REQUIRED_ERROR = "Challan Number is Required";
	String FEEPAID_REQUIRED_ERROR = "Fees Paid is Required";
	String RESIDENCE_ADDRESS_REQUIRED_ERROR = "Residence Address is Required";
	String RESIDENCE_STATUS_REQUIRED_ERROR = "Residence Status is Required";
	String SEMESTER_MARKS_REQUIRED = "Semester Marks is Required";
	String SUBJECT_LIST_REQUIRED = "Subject List is Required";
	String ACTIVITY_LIST_REQUIRED = "Activity List is Required";
	String DEPT_REQUIRED = "Department is Required";

	String INVALID_USER_PASSWORD_MSG = "Invalid Username/Password";
	String NO_USER_FOUND = "No User has been Found";
	String OLD_PASSWORD_CANNOT_BE_EMPTY = "Old Password Cannot be Empty";
	String NEW_PASSWORD_CANNOT_BE_EMPTY = "New Password Cannot be Empty";
	String CONFIRM_NEW_PASSWORD_CANNOT_BE_EMPTY = "Conform New Password cannot be Same";
	String NEW_PASSWORD_CONFIRM_PASSWORD_NOT_SAME = "New Password and Confirm Password cannot be Same";
	String OLD_PASSWORD_NOT_MATCHING_RECORDS = "Old Password does not match records";
	String SESSION_EXPIRED = "Session has Expired";
	String REGISTER_SUCESSFUL = "User has Registered Sucessfully";
	String LOGIN_ID_INVALID = "Login Id is Invalid";
	String SAVE_RATING = "Rating for the Service is Saved Sucessfully";
	String SAVE_COMPANY_FAILED = "Saving of Company Rating has Failed";
	String COULD_NOT_SAVE_COMPANY_INFO = "Could not save the Company Information";
	String COMPANY_NAME_EXISTS = "Company Name Already Exists";
	String SAVE_COMPANY_SUCESSFUL = "Saving of Company is Sucessful";
	String COULD_NOT_SAVE_PREPARELINK_INFO = "Could not Save Prepare Link Information";
	String SAVE_PREPAREINFO_SUCESSFUL = "Saving of Prepare Information is Sucessful";
	String PREPARE_NAME_EXISTS = "Prepare Name already Exists";
	String COULD_NOT_SAVE_QUESTION_INFO = "Could not Save the Question Information";
	String SAVE_QUESTIONINFO_SUCESSFUL = "Saving of Question Information is Sucesssful";
	String QUESDESC_EXISTS = "Question Description Already Exists";
	String ADMIN_CONTACT = "Please Contact Administrator"; 

	interface Keys {
		String OBJ = "OBJ";
		String LOGINID_SESSION = "LOGINID_SESSION";
		String LOGINTYPE_SESSION = "LOGINTYPE_SESSION";
		int ADMIN_TYPE = 1;
		String APP_NAME = "APP+91-9739419677";
		int CUSTOMER_TYPE = 2;
		int PROVIDER_TYPE = 3;
	}

	interface Page {
		String REGISTER_PAGE = "registeruser";
		String LOGIN_PAGE = "login";
		String CLIENT_PAGE = "client";
		String ADMIN_PAGE = "admin";
		String WELCOME_PAGE = "welcome";
	}

	interface SQLS {

		String RETRIVE_LOGINDETAILS_FOR_LOGINTYPE_AND_APPNAME_PAGINATED_SQL = "SELECT app_name,user_name,city,country,state,company_name,email,phone_no,age,description,gender,login_type,total_cost_all_services,service_type from usermaintain where login_type=:login_type and app_name=:app_name limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_APP_NAME_AND_LOGINTYPE_SQL = "SELECT COUNT(*) FROM usermaintain WHERE app_name=:app_name and login_type=:login_type";
		String RETRIVE_SERVICELIST_SQL = "SELECT servicename,servicedesc FROM servicetypelist";
		String RETRIVE_DISTINCT_COMPANYNAMES_SQL = "SELECT company_name FROM usermaintain WHERE login_type=:login_type";
		String RETRIVE_DISTINCT_COMPANYNAMES_FOR_SERVICETYPE_SQL = "SELECT company_name FROM usermaintain WHERE service_type=:service_type and login_type=:login_type";
		String RETRIVE_DISTINCT_RATINGTYPENAMES_SQL = "SELECT ratingname,ratingdesc from ratingtypelist";
		String INSERT_RATING_SQL = "INSERT INTO COMPANYRATING(company_name,userid,ratingtypename,rating,servicetypename) VALUES(:company_name,:userid,:ratingtypename,:rating,:servicetypename)";
		String RETRIVE_RATINGINFO_PAGINATED_SQL = "SELECT company_name,userid,ratingtypename,rating,servicetypename FROM COMPANYRATING limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_RATING_SQL = "SELECT COUNT(*) FROM COMPANYRATING";
		String RETRIVE_TOTALRATING_FOR_COMPANYNAME_AND_SERVICETYPE_AND_RATINGTYPE_SQL = "SELECT totalrating FROM totalratingcompany WHERE companyname=:companyname AND ratingtype=:ratingtype AND servicetype=:servicetype";
		String INSERT_TOTALRATING_SQL = "INSERT INTO TOTALRATINGCOMPANY(companyname,totalrating,ratingtype,servicetype) VALUES(:companyname,:totalrating,:ratingtype,:servicetype)";
		String UPDATE_TOTALRATING_SQL = "UPDATE TOTALRATINGCOMPANY SET totalrating=:totalrating WHERE companyname=:companyname AND ratingtype=:ratingtype AND servicetype=:servicetype";
		String RETRIVE_RATINGINFOTOTAL_PAGINATED_SQL = "SELECT companyname,totalrating,ratingtype,servicetype FROM TOTALRATINGCOMPANY limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_TOTALRATING_SQL = "SELECT COUNT(*) FROM TOTALRATINGCOMPANY";
		String RETRIVE_DISTINCT_CLUSTERTYPES_SQL = "SELECT DISTINCT CLUSTER FROM CLUSTERTYPES";
		String RETRIVE_COMPANYINFO_PAGINATED_SQL = "SELECT company_name,cluster,company_imageurl,companyurl FROM companyinformation limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_COMPANYINFORMATION_SQL = "SELECT COUNT(*) FROM companyinformation";
		String RETRIVE_DISTINCT_QUESTIONTYPES_SQL = "SELECT questiontype,questiondesc FROM questiontypes";
		String RETRIVE_PREPARELINKS_PAGINATED_SQL = "SELECT prepare_name,prepare_url,question_type FROM preparelink limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_PREPARELINKS_SQL = "SELECT COUNT(*) FROM preparelink";
		String RETRIVE_ALLQUESTIONS_PAGINATED_SQL = "SELECT question_desc,answer1,answer2,answer3,answer4,questiontype,rating1,rating2,rating3,rating4 from questions limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_QUESTIONS_SQL = "SELECT COUNT(*) FROM questions";
		String DELETE_FROM_CLASSIFYUSERCOMPUTE_FOR_USERID_SQL = "DELETE FROM CLASSIFYUSERCOMPUTE WHERE USERID=?";
		String INSERT_BATCH_CLASSIFY_USERCOMPUTE_SQL = "INSERT INTO CLASSIFYUSERCOMPUTE(USERID,FEATURETYPE,SCORE,SESSIONID) VALUES(?,?,?,?)";
		String DELETE_FROM_CLASSIFYUSERCOMPUTE_FOR_USERID_AND_SESSIONID_SQL = "DELETE FROM CLASSIFYUSERCOMPUTE WHERE USERID=? AND SESSIONID=?";
		String RETRIVE_DISTINCT_TOTALMARKS_FOR_DATASETS_SQL = "SELECT DISTINCT TOTALMARKS FROM DATASETS";
		String RETRIVE_DATASETS_FOR_TOTALMARKS_SQL = "SELECT CLUSTERNO,APTITUDE,GENERAL,TECHNICAL,TOTALMARKS FROM DATASETS WHERE TOTALMARKS=:TOTALMARKS";
		String DELETE_FROM_KNNDISTANCEVECTOR_FOR_USERID_AND_SESSIONID_SQL = "DELETE FROM knndistancecomputevector WHERE user_id=:user_id and session_id=:session_id";
		String RETRIVE_KNNDISTANCECOMPUTEVECTOR_FOR_USERID_AND_SESSIONID_SQL = "SELECT id,session_id,user_id,cluster_no,distance FROM knndistancecomputevector WHERE user_id=:user_id and session_id=:session_id ORDER BY distance ASC";
		String RETRIVE_COMPANYINFO_FOR_CLUSTERNO_SQL = "SELECT companyurl,company_imageurl,cluster,company_name FROM companyinformation where cluster=:cluster";
		String RETRIVE_HISTORYINFO_FOR_USERID_PAGINATED_SQL = "SELECT id,cluster_name,cluster_no,session_id,user_id from historyprofileusersession where user_id=:user_id limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_HISTORY_FORUSERID_SQL = "SELECT COUNT(*) FROM historyprofileusersession WHERE user_id=:user_id";
		String RETRIVE_KNNINFO_FOR_USERID_PAGINATED_SQL = "SELECT id,cluster_no,distance,session_id,user_id FROM knndistancecomputevector WHERE user_id=:user_id limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_KNN_FORUSERID_SQL = "SELECT COUNT(*) FROM knndistancecomputevector WHERE user_id=:user_id  ";
		String RETRIVE_SCOREINFO_FOR_USERID_PAGINATED_SQL = "SELECT USERID,FEATURETYPE,SCORE,SESSIONID FROM CLASSIFYUSERCOMPUTE WHERE USERID=:USERID limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_CLASSIFYUSER_FORUSERID_SQL = "SELECT COUNT(*) FROM CLASSIFYUSERCOMPUTE WHERE USERID=:USERID";
		String RETRIVE_TECHNICALSCOREINFO_FOR_USERID_SQL = "SELECT DISTINCT SESSIONID AS SESSIONOBJ,SCORE FROM CLASSIFYUSERCOMPUTE WHERE USERID=:USERID AND FEATURETYPE=:FEATURETYPE GROUP BY SESSIONID";
		String RETRIVE_DATASETS_PAGINATED_SQL = "SELECT CLUSTERNO,APTITUDE,GENERAL,TECHNICAL,TOTALMARKS FROM DATASETS limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_DATASETS_SQL = "SELECT COUNT(*) FROM DATASETS";
		String RETRIVE_KNNINFO_FOR_PAGINATED_SQL = "SELECT id,cluster_no,distance,session_id,user_id FROM knndistancecomputevector limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_KNN_FORALLUSERS_SQL = "SELECT COUNT(*) FROM knndistancecomputevector";
		String RETRIVE_HISTORYINFO_PAGINATED_SQL = "SELECT id,cluster_name,cluster_no,session_id,user_id from historyprofileusersession limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_HISTORY_SQL = "SELECT COUNT(*) FROM historyprofileusersession";
		String RETRIVE_SCOREINFO_PAGINATED_SQL = "SELECT USERID,FEATURETYPE,SCORE,SESSIONID FROM CLASSIFYUSERCOMPUTE limit :limit offset :offset";
		String RETRIVE_TOTAL_COUNT_FOR_CLASSIFYUSER_SQL = "SELECT COUNT(*) FROM CLASSIFYUSERCOMPUTE";
		
	}  

	interface EndPoints {

		String BASE_END_POINT = "http://localhost:8888/";

		String REGISTER_ENDPOINT = BASE_END_POINT + "register";
		String LOGIN_ENDPOINT = BASE_END_POINT + "login";

		String FETCH_USERS_END_POINT_APPNAME_CUSTOMER_LOGINTYPE = BASE_END_POINT + "/retriveAllCustomUsersForLoginType/"
				+ Messages.Keys.APP_NAME + "/" + 2;

		String FETCH_PROVIDERS_END_POINT_APPNAME_CUSTOMER_LOGINTYPE = BASE_END_POINT
				+ "/retriveAllCustomUsersForLoginType/" + Messages.Keys.APP_NAME + "/" + 10;
		
		String FETCH_COMPANY_NAMES_ENDPOINT=BASE_END_POINT+"/viewCompaniesAllForPagination";
		
		String FETCH_PREPARELINK_ENDPOINT=BASE_END_POINT+"/viewPrepareLinkAllForPagination";
		
		String FETCH_QUESTIONS_ENDPOINT=BASE_END_POINT+"/viewQuestionsAllForPagination";
		
	}
	
	interface Views{

		String VIEW_QUESTIONUSER_PAGE = "questionsuser";
		String VIEW_USER_ERROR_PAGE = "erroruser";
		String VIEW_USER_PAGE = "customer";
		String VIEW_ANALYIS_USER_PAGE = "analysisuser";
		
	}
	
	interface SessionIF {

		String ANSWER_SESSION = "ANSWERSESSION";
		String QUESTONVO_SESSION = "QUESTIONLIST";
		String DISPLAY_QUESTIONS = "DISPLAYQUESTIONS";
	}
	
	interface QuestionType{
		
		String APTITUDE_FEATURETYPE="APTITUDE";
		String GENERAL_FEATURETYPE="GENERAL";
		String TECHNICAL_FEATURETYPE="TECHNICAL";
	}
	
	interface ClusterLabel{
		
		String CLUSTERLABEL1 ="HIGHPACK";
		String CLUSTERLABEL2 ="MEDIUMPACK";
		String CLUSTERLABEL3 ="LOWPACK";
	}
}
