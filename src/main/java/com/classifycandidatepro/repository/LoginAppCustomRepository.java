package com.classifycandidatepro.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.classifycandidatepro.model.LoginVO;
import com.classifycandidatepro.model.PaginationConfigVO;
import com.classifycandidatepro.response.StatusInfo;
import com.model.messages.Messages;

@Repository
public class LoginAppCustomRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	//SELECT app_name,user_name,city,country,state,company_name,email,
	//phone_no,age,description,gender,login_type,total_cost_all_services,service_type from usermaintain 
	//where login_type=:login_type and app_name=:app_name limit:limit offset:offset
	public StatusInfo retriveUsersForLoginTypeAndAppNameForPagination(String appName,int loginType,PaginationConfigVO paginationConfigVO) {

		StatusInfo statusInfo = new StatusInfo();

		try {

			String sql = Messages.SQLS.RETRIVE_LOGINDETAILS_FOR_LOGINTYPE_AND_APPNAME_PAGINATED_SQL;

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("limit", paginationConfigVO.getLimit());
			paramMap.put("offset", paginationConfigVO.getStart());
			paramMap.put("app_name", appName);
			paramMap.put("login_type", loginType);

			List<LoginVO> testScoreList = namedParameterJdbcTemplate.query(sql, paramMap,
					new LoginVOPaginationMapper());

			if (null == testScoreList) {
				statusInfo.setStatus(false);
				statusInfo.setErrMessage("Could not Find Any Test Score List");
				return statusInfo;

			}

			//SELECT COUNT(*) FROM usermaintain WHERE app_name=:app_name 
			//and login_type=:login_type
			String sql1 = Messages.SQLS.RETRIVE_TOTAL_COUNT_FOR_APP_NAME_AND_LOGINTYPE_SQL;

			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			paramMap1.put("login_type", loginType);
			paramMap1.put("app_name", appName);
			
			

			int count = namedParameterJdbcTemplate.queryForObject(sql1, paramMap1, Integer.class);

			statusInfo.setTotal(count);

			statusInfo.setModel(testScoreList);
			statusInfo.setStatus(true);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMessage(e.getMessage());
			System.out.println("Exception" + e);
			return statusInfo;
		}

		return statusInfo;
	}
	
	//SELECT app_name,user_name,city,country,state,company_name,email,
	//phone_no,age,description,gender,login_type,total_cost_all_services,
	//service_type from usermaintain 
	//where login_type=:login_type and app_name=:app_name limit:limit offset:offset

	private final class LoginVOPaginationMapper implements RowMapper<LoginVO> {

		public LoginVO mapRow(ResultSet rs, int arg1) throws SQLException {
			LoginVO loginVO = new LoginVO();
			
			loginVO.setAge(rs.getInt("age"));
			loginVO.setApp_name(rs.getString("app_name"));
			loginVO.setUser_name(rs.getString("user_name"));
			loginVO.setCity(rs.getString("city"));
			loginVO.setCountry(rs.getString("country"));
			loginVO.setState(rs.getString("state"));
			loginVO.setTotal_cost_all_services(rs.getDouble("total_cost_all_services"));
			loginVO.setCompany_name(rs.getString("company_name"));
			loginVO.setLoginType(rs.getInt("login_type"));
			loginVO.setGender(rs.getString("gender"));
			loginVO.setPhone_no(rs.getString("phone_no"));
			loginVO.setEmail(rs.getString("email"));
			loginVO.setService_type(rs.getString("service_type"));
			loginVO.setDescription(rs.getString("description"));
		
			return loginVO; 
		}
	}

}
