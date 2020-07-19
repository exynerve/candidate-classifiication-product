<!DOCTYPE html>
<%@page import="com.model.messages.Messages"%>
<html class="no-js">
<!--<![endif]-->
<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
<head>
<title>View Companies</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/extjs41/resources/css/ext-all.css" />
<script type="text/javascript" >
var contextPath='<%=request.getContextPath()%>';
var app_name='<%=Messages.Keys.APP_NAME%>';
var view_company_endpoints='<%=Messages.EndPoints.FETCH_COMPANY_NAMES_ENDPOINT%>'
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/extjs41/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/customjs/viewcompanies.js"></script>

	

</head>


<body>
<jsp:include page="/WEB-INF/jsp/adminmenu.jsp"></jsp:include>



<div class="dataSetContainer">
</div>
</body>
</html>