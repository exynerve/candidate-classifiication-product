package com.classifycandidatepro.main.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.classifycandidatepro.model.ClassifyUserCompute;
import com.classifycandidatepro.model.ClusterTypesVO;
import com.classifycandidatepro.model.CompanyNamesVO;
import com.classifycandidatepro.model.DataSet;
import com.classifycandidatepro.model.HistoryProfileUserSession;
import com.classifycandidatepro.model.KNNDistanceComputeVector;
import com.classifycandidatepro.model.PaginationConfigVO;
import com.classifycandidatepro.model.Question;
import com.classifycandidatepro.model.QuestionTypesVO;
import com.classifycandidatepro.model.RatingStorageUIObj;
import com.classifycandidatepro.model.RatingTypesNames;
import com.classifycandidatepro.model.ScoreGraph;
import com.classifycandidatepro.model.ServiceList;
import com.classifycandidatepro.response.StatusInfo;
import com.classifycandidatepro.storageobjects.CompanyInformationVO;
import com.classifycandidatepro.storageobjects.PrepareLinkVO;
import com.model.messages.Messages;

@Repository
public class ClassifyCandidateRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<ServiceList> obtainAllServiceTypeList() {

		List<ServiceList> serviceList = null;
		try {

			String SQL = Messages.SQLS.RETRIVE_SERVICELIST_SQL;

			serviceList = namedParameterJdbcTemplate.query(SQL, new ServiceTypeListMapper());

		} catch (Exception e) {
			return null;
		}

		return serviceList;
	}

	private final class ServiceTypeListMapper implements RowMapper<ServiceList> {

		public ServiceList mapRow(ResultSet rs, int arg1) throws SQLException {

			ServiceList serviceList = new ServiceList();
			serviceList.setServicedesc(rs.getString("servicedesc"));
			serviceList.setServicename(rs.getString("servicename"));
			return serviceList;
		}
	}

	public List<CompanyNamesVO> retriveCompanyNamesList() {

		List<CompanyNamesVO> companyNamesVOList = null;
		try {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("login_type", 10);

			String SQL = Messages.SQLS.RETRIVE_DISTINCT_COMPANYNAMES_SQL;

			companyNamesVOList = namedParameterJdbcTemplate.query(SQL, paramMap, new CompanyNameMapper());

		} catch (Exception e) {
			return null;
		}

		return companyNamesVOList;

	}

	public List<CompanyNamesVO> retriveCompanyNamesListForServiceType(String serviceType) {

		List<CompanyNamesVO> companyNamesVOList = null;
		try {

			String SQL = Messages.SQLS.RETRIVE_DISTINCT_COMPANYNAMES_FOR_SERVICETYPE_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("service_type", serviceType);
			paramMap.put("login_type", 10);

			System.out.println("SQL=" + SQL);
			System.out.println("PARAMMAP" + paramMap);

			companyNamesVOList = namedParameterJdbcTemplate.query(SQL, paramMap, new CompanyNameMapper());

		} catch (Exception e) {
			return null;
		}

		return companyNamesVOList;

	}

	private final class CompanyNameMapper implements RowMapper<CompanyNamesVO> {

		public CompanyNamesVO mapRow(ResultSet rs, int arg1) throws SQLException {

			CompanyNamesVO companyNamesVO = new CompanyNamesVO();
			companyNamesVO.setCompanyname(rs.getString("company_name"));

			return companyNamesVO;
		}
	}

	public List<RatingTypesNames> retriveRatingTypeNamesList() {

		List<RatingTypesNames> ratingTypesNamesList = null;
		try {

			String SQL = Messages.SQLS.RETRIVE_DISTINCT_RATINGTYPENAMES_SQL;

			ratingTypesNamesList = namedParameterJdbcTemplate.query(SQL, new RatingTypesNamesMapper());

		} catch (Exception e) {
			return null;
		}

		return ratingTypesNamesList;

	}

	private final class RatingTypesNamesMapper implements RowMapper<RatingTypesNames> {

		public RatingTypesNames mapRow(ResultSet rs, int arg1) throws SQLException {

			RatingTypesNames ratingTypesNames = new RatingTypesNames();
			ratingTypesNames.setRatingdesc(rs.getString("ratingdesc"));
			ratingTypesNames.setRatingname(rs.getString("ratingname"));

			return ratingTypesNames;
		}
	}

	public StatusInfo insertRatingStorage(RatingStorageUIObj ratingStorageObj) {

		StatusInfo statusInfo = new StatusInfo();
		try {

			String sql = Messages.SQLS.INSERT_RATING_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("company_name", ratingStorageObj.getCompanyName());
			paramMap.put("ratingtypename", ratingStorageObj.getRatingType());
			paramMap.put("rating", ratingStorageObj.getRating());
			paramMap.put("servicetypename", ratingStorageObj.getServicetype());
			paramMap.put("userid", ratingStorageObj.getUserId());

			namedParameterJdbcTemplate.update(sql, paramMap);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			return statusInfo;
		}

		statusInfo.setStatus(true);
		return statusInfo;
	}

	public StatusInfo retriveAllRatingPaginationConfig(PaginationConfigVO paginationConfigVO) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_RATINGINFO_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());

			List<RatingStorageUIObj> ratingList = namedParameterJdbcTemplate.query(sql, paramMap,
					new RatingVOPaginationMapper());

			if (null == ratingList) {
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Rating List");
				return statusInfo;

			}

			// SELECT COUNT(*) FROM usermaintain WHERE app_name=:app_name
			// and login_type=:login_type
			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_RATING_SQL;

			Map<String, Object> paramMap1 = null;

			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(ratingList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}

	private final class RatingVOPaginationMapper implements RowMapper<RatingStorageUIObj> {

		public RatingStorageUIObj mapRow(ResultSet rs, int arg1) throws SQLException {

			RatingStorageUIObj ratingStorage = new RatingStorageUIObj();

			ratingStorage.setCompanyName(rs.getString("company_name"));
			ratingStorage.setRating(rs.getDouble("rating"));
			ratingStorage.setRatingType(rs.getString("ratingtypename"));
			ratingStorage.setServicetype(rs.getString("servicetypename"));
			ratingStorage.setUserId(rs.getString("userid"));

			return ratingStorage;
		}
	}

	// SELECT totalrating FROM totalratingcompany
	// WHERE companyname=:companyname AND ratingtype=:ratingtype
	// AND servicetype=:servicetype
	public double retriveRatingForServiceTypeAndCompanyNameAndRatingType(String servicetype, String companyName,
			String ratingType) {
		double rating = -1;
		try {

			String sql = Messages.SQLS.RETRIVE_TOTALRATING_FOR_COMPANYNAME_AND_SERVICETYPE_AND_RATINGTYPE_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("companyname", companyName);
			paramMap.put("ratingtype", ratingType);
			paramMap.put("servicetype", servicetype);
			rating = namedParameterJdbcTemplate.queryForObject(sql, paramMap, Double.class);
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return -1;
		}
		return rating;
	}

	// INSERT INTO
	// TOTALRATINGCOMPANY(companyname,totalrating,ratingtype,servicetype)
	// VALUES(:companyname,:totalrating,:ratingtype,:servicetype)
	public StatusInfo insertCompanyRatingStorage(RatingStorageUIObj ratingStorageObj) {
		StatusInfo statusInfo = new StatusInfo();
		try {

			String sql = Messages.SQLS.INSERT_TOTALRATING_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("companyname", ratingStorageObj.getCompanyName());
			paramMap.put("ratingtype", ratingStorageObj.getRatingType());
			paramMap.put("totalrating", ratingStorageObj.getRating());
			paramMap.put("servicetype", ratingStorageObj.getServicetype());

			namedParameterJdbcTemplate.update(sql, paramMap);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			return statusInfo;
		}

		statusInfo.setStatus(true);
		return statusInfo;
	}

	public StatusInfo updateCompanyRatingStorage(RatingStorageUIObj ratingStorageObj) {
		StatusInfo statusInfo = new StatusInfo();
		try {

			String sql = Messages.SQLS.UPDATE_TOTALRATING_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("companyname", ratingStorageObj.getCompanyName());
			paramMap.put("ratingtype", ratingStorageObj.getRatingType());
			paramMap.put("totalrating", ratingStorageObj.getRating());
			paramMap.put("servicetype", ratingStorageObj.getServicetype());

			namedParameterJdbcTemplate.update(sql, paramMap);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			return statusInfo;
		}

		statusInfo.setStatus(true);
		return statusInfo;
	}

	public StatusInfo retriveAllTotalRatingPaginationConfig(PaginationConfigVO paginationConfigVO) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_RATINGINFOTOTAL_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());

			List<RatingStorageUIObj> ratingList = namedParameterJdbcTemplate.query(sql, paramMap,
					new RatingVOTotalPaginationMapper());

			if (null == ratingList) {
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Rating List");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_TOTALRATING_SQL;

			Map<String, Object> paramMap1 = null;

			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(ratingList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}

	private final class RatingVOTotalPaginationMapper implements RowMapper<RatingStorageUIObj> {

		public RatingStorageUIObj mapRow(ResultSet rs, int arg1) throws SQLException {

			RatingStorageUIObj ratingStorage = new RatingStorageUIObj();

			ratingStorage.setCompanyName(rs.getString("companyname"));
			ratingStorage.setRating(rs.getDouble("totalrating"));
			ratingStorage.setRatingType(rs.getString("ratingtype"));
			ratingStorage.setServicetype(rs.getString("servicetype"));

			return ratingStorage;
		}
	}

	public List<ClusterTypesVO> retriveAllClusterTypes() {
		List<ClusterTypesVO> clusterTypeList = null;
		try {

			Map<String, Object> paramMap = null;

			String SQL = Messages.SQLS.RETRIVE_DISTINCT_CLUSTERTYPES_SQL;

			clusterTypeList = namedParameterJdbcTemplate.query(SQL, paramMap, new ClusterTypeMapper());

		} catch (Exception e) {
			return null;
		}

		return clusterTypeList;
	}

	private final class ClusterTypeMapper implements RowMapper<ClusterTypesVO> {

		public ClusterTypesVO mapRow(ResultSet rs, int arg1) throws SQLException {
			ClusterTypesVO clusterTypesVO = new ClusterTypesVO();
			clusterTypesVO.setCluster(rs.getString("cluster"));
			return clusterTypesVO;
		}
	}

	// SELECT company_name,cluster,company_imageurl,companyurl
	// FROM companyinformation limit :limit offset :offset
	public StatusInfo retriveAllCompanyInformationPaginationConfig(PaginationConfigVO paginationConfigVO) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_COMPANYINFO_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());

			List<CompanyInformationVO> ratingList = namedParameterJdbcTemplate.query(sql, paramMap,
					new CompanyInformationPaginationMapper());

			if (null == ratingList) {
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Company Information List");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_COMPANYINFORMATION_SQL;

			Map<String, Object> paramMap1 = null;

			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(ratingList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}

	private final class CompanyInformationPaginationMapper implements RowMapper<CompanyInformationVO> {

		public CompanyInformationVO mapRow(ResultSet rs, int arg1) throws SQLException {

			CompanyInformationVO companyInformationVO = new CompanyInformationVO();
			companyInformationVO.setCluster(rs.getString("cluster"));
			companyInformationVO.setCompanyImageURL(rs.getString("company_imageurl"));
			companyInformationVO.setCompanyName(rs.getString("company_name"));
			companyInformationVO.setCompanyURL(rs.getString("companyurl"));

			return companyInformationVO;
		}
	}

	public List<QuestionTypesVO> retriveAllQuestionTypes() {
		List<QuestionTypesVO> questionTypeList = null;
		try {

			Map<String, Object> paramMap = null;

			String SQL = Messages.SQLS.RETRIVE_DISTINCT_QUESTIONTYPES_SQL;

			questionTypeList = namedParameterJdbcTemplate.query(SQL, paramMap, new QuestionTypeMapper());

		} catch (Exception e) {
			return null;
		}

		return questionTypeList;
	}

	private final class QuestionTypeMapper implements RowMapper<QuestionTypesVO> {

		public QuestionTypesVO mapRow(ResultSet rs, int arg1) throws SQLException {

			QuestionTypesVO questionTypesVO = new QuestionTypesVO();
			questionTypesVO.setQuestiondesc(rs.getString("questiondesc"));
			questionTypesVO.setQuestiontype(rs.getString("questiontype"));
			return questionTypesVO;
		}
	}

	// SELECT prepare_name,prepare_url,question_type FROM
	// preparelink limit :limit offset :offset
	public StatusInfo retriveAllPrepareLinksPaginationConfig(PaginationConfigVO paginationConfigVO) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_PREPARELINKS_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());

			List<PrepareLinkVO> prepareLink = namedParameterJdbcTemplate.query(sql, paramMap,
					new PrepareLinkPaginationMapper());

			if (null == prepareLink) {
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Prepare Link");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_PREPARELINKS_SQL;

			Map<String, Object> paramMap1 = null;

			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(prepareLink);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}

	private final class PrepareLinkPaginationMapper implements RowMapper<PrepareLinkVO> {

		public PrepareLinkVO mapRow(ResultSet rs, int arg1) throws SQLException {

			PrepareLinkVO prepareLinkVO = new PrepareLinkVO();

			prepareLinkVO.setPrepareName(rs.getString("prepare_name"));
			prepareLinkVO.setPrepareUrl(rs.getString("prepare_url"));
			prepareLinkVO.setQuestionType(rs.getString("question_type"));

			return prepareLinkVO;
		}
	}

	public StatusInfo retriveAllQuestionsForPagination(PaginationConfigVO paginationConfigVO) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_ALLQUESTIONS_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());

			List<Question> questionList = namedParameterJdbcTemplate.query(sql, paramMap,
					new QuestionsPaginationMapper());

			if (null == questionList) {
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Question List");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_QUESTIONS_SQL;

			Map<String, Object> paramMap1 = null;

			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(questionList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}

	private final class QuestionsPaginationMapper implements RowMapper<Question> {

		public Question mapRow(ResultSet rs, int arg1) throws SQLException {

			Question question = new Question();

			question.setAnswer1(rs.getString("answer1"));
			question.setAnswer2(rs.getString("answer2"));
			question.setAnswer3(rs.getString("answer3"));
			question.setAnswer4(rs.getString("answer4"));
			question.setQuestionDesc(rs.getString("question_desc"));
			question.setQuestiontype(rs.getString("questiontype"));
			question.setRating1(rs.getInt("rating1"));
			question.setRating2(rs.getInt("rating2"));
			question.setRating3(rs.getInt("rating3"));
			question.setRating4(rs.getInt("rating4"));

			return question;
		}
	}

	public StatusInfo deleteFromClassifyUserCompute(String userId) {
		StatusInfo statusInfo = new StatusInfo();
		try {

			statusInfo = new StatusInfo();
			String sql = Messages.SQLS.DELETE_FROM_CLASSIFYUSERCOMPUTE_FOR_USERID_SQL;
			jdbcTemplate.update(sql, userId);
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;

		}
	}

	public StatusInfo insertBatchClassifyUserInfo(List<ClassifyUserCompute> nutritionUserComputes) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			String sql = Messages.SQLS.INSERT_BATCH_CLASSIFY_USERCOMPUTE_SQL;

			for (ClassifyUserCompute userComputeVO : nutritionUserComputes) {
				jdbcTemplate.update(sql,
						new Object[] { userComputeVO.getUserId(), userComputeVO.getFeatureType(),
								userComputeVO.getScore(), userComputeVO.getSessionId() },
						new int[] { Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.VARCHAR });
			}

			statusInfo.setStatus(true);

			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	public StatusInfo deleteFromClassifyUserCompute(String userId, String sessionId) {
		StatusInfo statusInfo = new StatusInfo();
		try {

			statusInfo = new StatusInfo();
			String sql = Messages.SQLS.DELETE_FROM_CLASSIFYUSERCOMPUTE_FOR_USERID_AND_SESSIONID_SQL;
			jdbcTemplate.update(sql, userId, sessionId);
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;

		}
	}

	public List<Integer> retriveDistinctMarksFromTestData() {

		List<Integer> distinctMarksList = null;
		try {

			Map<String, Object> paramMap = null;

			String SQL = Messages.SQLS.RETRIVE_DISTINCT_TOTALMARKS_FOR_DATASETS_SQL;

			distinctMarksList = namedParameterJdbcTemplate.queryForList(SQL, paramMap, Integer.class);

		} catch (Exception e) {
			return null;
		}

		return distinctMarksList;
	}

	public List<DataSet> retriveDataSetsForMarks(int marksForDataSet) {
		List<DataSet> dataSetList = null;
		try {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("TOTALMARKS", marksForDataSet);

			String SQL = Messages.SQLS.RETRIVE_DATASETS_FOR_TOTALMARKS_SQL;

			dataSetList = namedParameterJdbcTemplate.query(SQL, paramMap, new DataSetMapper());

		} catch (Exception e) {
			return null;
		}

		return dataSetList;
	}

	private final class DataSetMapper implements RowMapper<DataSet> {

		public DataSet mapRow(ResultSet rs, int arg1) throws SQLException {
			DataSet dataSet = new DataSet();
			dataSet.setAptitude(rs.getDouble("APTITUDE"));
			dataSet.setGeneral(rs.getDouble("GENERAL"));
			dataSet.setTechnical(rs.getDouble("TECHNICAL"));
			dataSet.setClusterNo(rs.getInt("CLUSTERNO"));
			dataSet.setTotalmarks(rs.getDouble("TOTALMARKS"));
			return dataSet;
		}
	}

	//user_id=:user_id and session_id=:session_id
	public StatusInfo deleteKNNDistanceMatrixForSessionIdAndUserId(String sessionId, String userId) {
		StatusInfo statusInfo = new StatusInfo();
		try {

			statusInfo = new StatusInfo();
			String sql = Messages.SQLS.DELETE_FROM_KNNDISTANCEVECTOR_FOR_USERID_AND_SESSIONID_SQL;
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("user_id", userId);
			paramMap.put("session_id", sessionId);
			
			namedParameterJdbcTemplate.update(sql,paramMap);
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;

		}
	}

	public List<KNNDistanceComputeVector> retriveKNNDistanceMatrixForUserIdAndSessionIdDistanceAsc(String userId,
			String sessionId) {
		List<KNNDistanceComputeVector> knnDistanceComputeVectorList = null;
		try {
 
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_id", userId);
			paramMap.put("session_id", sessionId);

			String SQL = Messages.SQLS.RETRIVE_KNNDISTANCECOMPUTEVECTOR_FOR_USERID_AND_SESSIONID_SQL;

			knnDistanceComputeVectorList = namedParameterJdbcTemplate.query(SQL, paramMap, new KNNDistanceComputeVectorMapper());

		} catch (Exception e) {
			return null;
		}

		return knnDistanceComputeVectorList;
	}
	
	private final class KNNDistanceComputeVectorMapper implements RowMapper<KNNDistanceComputeVector> {

		public KNNDistanceComputeVector mapRow(ResultSet rs, int arg1) throws SQLException {
			KNNDistanceComputeVector knnDistanceCompute = new KNNDistanceComputeVector();
			knnDistanceCompute.setClusterNo(rs.getInt("cluster_no"));
			knnDistanceCompute.setDistance(rs.getDouble("distance"));
			knnDistanceCompute.setSessionId(rs.getString("session_id"));
			knnDistanceCompute.setUserId(rs.getString("user_id"));
			knnDistanceCompute.setId(rs.getLong("id"));
			
			return knnDistanceCompute;
		}
	}

	public List<CompanyInformationVO> retriveCompanyInformationForClusterName(String clusterlabel) {
		List<CompanyInformationVO> companyInformationVO = null; 
		try {
 
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("cluster", clusterlabel);
		
			String SQL = Messages.SQLS.RETRIVE_COMPANYINFO_FOR_CLUSTERNO_SQL;

			companyInformationVO = namedParameterJdbcTemplate.query(SQL, paramMap, new CompanyInformationVOMapper());

		} catch (Exception e) {
			return null;
		}

		return companyInformationVO;
	}
	
	private final class CompanyInformationVOMapper implements RowMapper<CompanyInformationVO> {

		public CompanyInformationVO mapRow(ResultSet rs, int arg1) throws SQLException {
			CompanyInformationVO companyInformation = new CompanyInformationVO();
		 
			companyInformation.setCluster(rs.getString("cluster"));
			companyInformation.setCompanyImageURL(rs.getString("company_imageurl"));
			companyInformation.setCompanyName(rs.getString("company_name"));
			companyInformation.setCompanyURL(rs.getString("companyurl"));
			
			return companyInformation;
		}
	}

	public StatusInfo retriveAllHistoryForPaginationForUser(PaginationConfigVO paginationConfigVO, String userId) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_HISTORYINFO_FOR_USERID_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());
			paramMap.put("user_id", userId);
			
			

			List<HistoryProfileUserSession> historyProfileList = namedParameterJdbcTemplate.query(sql, paramMap,
					new HistoryProfileUserSessionMapper()); 

			if (null == historyProfileList) {
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Rating List");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_HISTORY_FORUSERID_SQL;

			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			paramMap1.put("user_id",userId);

			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(historyProfileList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}
	
	private final class HistoryProfileUserSessionMapper implements RowMapper<HistoryProfileUserSession> {

		public HistoryProfileUserSession mapRow(ResultSet rs, int arg1) throws SQLException {
			HistoryProfileUserSession historyProfileUserSession = new HistoryProfileUserSession();
		 
			historyProfileUserSession.setClusterName(rs.getString("cluster_name"));
			historyProfileUserSession.setClusterNo(rs.getInt("cluster_no"));
			historyProfileUserSession.setSessionId(rs.getString("session_id"));
			historyProfileUserSession.setUserId(rs.getString("user_id"));
			historyProfileUserSession.setId(rs.getLong("id"));
			
			return historyProfileUserSession;
		}
	}

	public StatusInfo retriveAllKNNForPaginationForUser(PaginationConfigVO paginationConfigVO, String userId) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_KNNINFO_FOR_USERID_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());
			paramMap.put("user_id", userId);
			
			

			List<KNNDistanceComputeVector> knnList = namedParameterJdbcTemplate.query(sql, paramMap,
					new KNNDistanceComputeVectorMapper()); 

			if (null == knnList) { 
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any KNN List");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_KNN_FORUSERID_SQL;

			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			paramMap1.put("user_id",userId);

			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(knnList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}

	public StatusInfo retriveAllScoreForPaginationOfSpecificUser(PaginationConfigVO paginationConfigVO, String userId) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_SCOREINFO_FOR_USERID_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());
			paramMap.put("USERID", userId);
			
			

			List<ClassifyUserCompute> classifyUserList = namedParameterJdbcTemplate.query(sql, paramMap,
					new ClassifyUserComputeMapper()); 
 
			if (null == classifyUserList) { 
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Classify User List");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_CLASSIFYUSER_FORUSERID_SQL;

			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			paramMap1.put("USERID",userId);

			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(classifyUserList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	} 
	
	private final class ClassifyUserComputeMapper implements RowMapper<ClassifyUserCompute> {

		public ClassifyUserCompute mapRow(ResultSet rs, int arg1) throws SQLException {

			ClassifyUserCompute classifyUserCompute = new ClassifyUserCompute();
			classifyUserCompute.setFeatureType(rs.getString("FEATURETYPE"));
			classifyUserCompute.setScore(rs.getDouble("SCORE"));
			classifyUserCompute.setSessionId(rs.getString("SESSIONID"));
			classifyUserCompute.setUserId(rs.getString("USERID"));
			
			return classifyUserCompute;
		}
	}

	public StatusInfo retriveAllScoreForSpecificUser(String userId,String featureType) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_TECHNICALSCOREINFO_FOR_USERID_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("USERID", userId);
			paramMap.put("FEATURETYPE", featureType);
			

			List<ScoreGraph> scoreGraphList = namedParameterJdbcTemplate.query(sql, paramMap,
					new ScoreGraphMapper()); 
 
			if (null == scoreGraphList) {  
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Score Mapper List");
				return statusInfo;

			}

			int iterationNo=1;
			for(ScoreGraph scoreGraph:scoreGraphList){
				scoreGraph.setIterationNo(iterationNo);
				iterationNo=iterationNo+1;
			}
		
			statusInfo.setTotal(scoreGraphList.size());

			statusInfo.setModel(scoreGraphList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	} 
	
	private final class ScoreGraphMapper implements RowMapper<ScoreGraph> {

		public ScoreGraph mapRow(ResultSet rs, int arg1) throws SQLException {

			ScoreGraph scoreGraph = new ScoreGraph(); 
			scoreGraph.setScore(rs.getDouble("SCORE"));
			scoreGraph.setSessionObj(rs.getString("SESSIONOBJ"));
		
			return scoreGraph;
		}
	}

	public StatusInfo retriveAllDataSetsForPagination(PaginationConfigVO paginationConfigVO) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_DATASETS_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());
			
			

			List<DataSet> classifyUserList = namedParameterJdbcTemplate.query(sql, paramMap,
					new DataSetMapper()); 
 
			if (null == classifyUserList) { 
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Classify User List");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_DATASETS_SQL;

			Map<String, Object> paramMap1 = null;
			
			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(classifyUserList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}

	public StatusInfo retriveAllKNNForPagination(PaginationConfigVO paginationConfigVO) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_KNNINFO_FOR_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());

			List<KNNDistanceComputeVector> knnList = namedParameterJdbcTemplate.query(sql, paramMap,
					new KNNDistanceComputeVectorMapper()); 

			if (null == knnList) { 
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any KNN List");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_KNN_FORALLUSERS_SQL;

			Map<String, Object> paramMap1 = null;

			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(knnList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo; 
	}

	public StatusInfo retriveAllHistoryForPagination(PaginationConfigVO paginationConfigVO) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_HISTORYINFO_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());
			
			

			List<HistoryProfileUserSession> historyProfileList = namedParameterJdbcTemplate.query(sql, paramMap,
					new HistoryProfileUserSessionMapper()); 

			if (null == historyProfileList) {
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Rating List");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_HISTORY_SQL;

			Map<String, Object> paramMap1 = null;
		
			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(historyProfileList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}

	public StatusInfo retriveAllScoreForPagination(PaginationConfigVO paginationConfigVO) {
		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_SCOREINFO_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());
			

			List<ClassifyUserCompute> classifyUserList = namedParameterJdbcTemplate.query(sql, paramMap,
					new ClassifyUserComputeMapper()); 
 
			if (null == classifyUserList) { 
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Classify User List");
				return statusInfo;

			}

			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_CLASSIFYUSER_SQL;

			Map<String, Object> paramMap1 = null;


			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(classifyUserList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}  

} 
