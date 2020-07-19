<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.model.messages.Messages"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Technical Score Graph</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/extjs41/resources/css/ext-all.css" />
<script type="text/javascript" >
var contextPath='<%=request.getContextPath()%>';
var app_name_registered='<%=Messages.Keys.APP_NAME%>';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/extjs41/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/customjs/viewaptitudeuserscore.js"></script>


</head>
<body>
<jsp:include page="/WEB-INF/jsp/customermenu.jsp"></jsp:include>

<div id="content">

<div id="confirmationMessage"></div>

<div id="graph">

</div>
</div>
</body>
</html>