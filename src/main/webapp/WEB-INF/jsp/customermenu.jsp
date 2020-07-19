<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath()%>/css/styles.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id='cssmenu'>

		<ul>

			<li class='active '><a
				href="<%=request.getContextPath()%>/navcustomerhome"><span>Home</span></a></li>
		</ul>

		<ul>

			<li class='active '><a
				href="<%=request.getContextPath()%>/navviewpreparelinkscandidate"><span>Preparation</span></a></li>
		</ul>

		<ul>

			<li class='active '><a
				href="<%=request.getContextPath()%>/viewscreeningtestuser"><span>Take
						Test</span></a></li>
		</ul>

		<ul>
			<li class='active '><a
				href="<%=request.getContextPath()%>/viewhistoryusernav"><span>View
						History</span></a></li>
		</ul>

		<ul>
			<li class='active '><a
				href="<%=request.getContextPath()%>/viewknndistanceusernav"><span>View
				KNN</span></a></li>
		</ul>

		<ul>
			<li class='active '><a
				href="<%=request.getContextPath()%>/viewscoreusernav"><span>View
				Score</span></a></li>
		</ul>

		<ul>
			<li class='has-sub'><a href='#'>Score Graph<span></span></a>
				<ul>
					<li><a
						href="<%=request.getContextPath()%>/viewaptitudeuserscorenav"><span>
								Aptitude Graph </span></a></li>
					<li><a
						href="<%=request.getContextPath()%>/viewgeneraluserscorenav"><span>
								General Graph </span></a></li>
					<li><a
						href="<%=request.getContextPath()%>/viewtechnicaluserscorenav"><span>
								Technical Graph </span></a></li>
				</ul></li>
		</ul>
		<ul>
			<li class='has-sub '><a
				href='<%=request.getContextPath()%>/logout.do'>Logout<span></span></a></li>
		</ul>
	</div>
</body>
</html>