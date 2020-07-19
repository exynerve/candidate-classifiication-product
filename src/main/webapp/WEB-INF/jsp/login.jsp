<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page import="java.util.List"%>
<%@page
	import="com.classifycandidatepro.response.AJAXResponseLogin,java.util.List,com.classifycandidatepro.response.ErrorMessagesObj"%>
<%@page import="com.model.messages.Messages"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/reset.css">
<link rel='stylesheet prefetch'
	href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
<link rel='stylesheet prefetch'
	href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/loginstyle.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/errormsg.css">
<script>
	function preventBack() {
		window.history.forward();
	}
	setTimeout("preventBack()", 0);
	window.onunload = function() {
		null
	};
</script>
</head>
<body>
	<%
	AJAXResponseLogin ajax = (AJAXResponseLogin) request.getAttribute(Messages.Keys.OBJ);

		if (null == ajax) {

		} else {
			List<ErrorMessagesObj> ebErrors = ajax.getErrMessages();

			if (null == ebErrors) {

			} else {
				ErrorMessagesObj msg = ebErrors.get(0);
	%>
	<div class="isa_error" name="userNameErr">
		<i class="fa fa-times-circle"></i>
		<%=msg.getErrMessage()%>
	</div>
	<%
		}
		}
	%>

	<div class="module form-module">
		<div class="toggle">
			<i class="fa fa-times fa-pencil"></i>
			<div class="tooltip">Click Me</div>
		</div>
		<div class="form">
			<h2>Login to your account</h2>
			<form action="<%=request.getContextPath()%>/login.do"
				method="post">
				<input type="text" name="userName" placeholder="Username" /> <input
					type="password" name="password" placeholder="Password" />
				<button>Login</button>
			</form>
		</div>
		<div class="cta">
			<a href="<%=request.getContextPath()%>/">Home</a>
		</div>

		<!--<div class="cta"><a href="/forgotpassword.jsp">Forgot your password?</a></div>-->
	</div>
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script src="<%=request.getContextPath()%>/js/login.js"></script>




</body>
</html>