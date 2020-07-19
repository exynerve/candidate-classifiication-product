<!DOCTYPE html>
<%@page import="com.model.messages.Messages"%>
<html class="no-js">
<!--<![endif]-->
<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
<head>
<title>View Data Sets</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/extjs41/resources/css/ext-all.css" />
<script type="text/javascript" >
var contextPath='<%=request.getContextPath()%>';
var app_name='<%=Messages.Keys.APP_NAME%>';
var users_end_point_with_appname_logintype_customer='<%=Messages.EndPoints.FETCH_USERS_END_POINT_APPNAME_CUSTOMER_LOGINTYPE%>'
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/extjs41/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/customjs/viewusers.js"></script>

	

</head>


<body>
<jsp:include page="/WEB-INF/jsp/adminmenu.jsp"></jsp:include>



<div class="dataSetContainer">
</div>
</body>
</html>