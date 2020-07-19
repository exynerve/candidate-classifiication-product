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
				href="<%=request.getContextPath()%>/adminPage"><span>Home</span></a></li>
			<li class='active '><a
				href="<%=request.getContextPath()%>/viewUsersPage"><span>View Users</span></a></li>
				
			<li class='has-sub '><a href='#'><span>Company Maintain</span></a>
				<ul>
					<li><a href="<%=request.getContextPath()%>/navCompanyName"><span> Create Company</span></a></li>
					<li><a href="<%=request.getContextPath()%>/navViewCompanies"><span> View Company Information</span></a></li>
			
				</ul>
			</li>
			
			
			<li class='has-sub '><a href='#'><span>Prepare Link Maintain</span></a>
				<ul>
					<li><a href="<%=request.getContextPath()%>/navPrepareLink"><span> Create Prepare Link</span></a></li>
					<li><a href="<%=request.getContextPath()%>/navViewPrepareLinks"><span> View Prepare Links</span></a></li>
			
				</ul>
			</li>
			
			
			<li class='has-sub '><a href='#'><span>Questions Maintain</span></a>
				<ul>
					<li><a href="<%=request.getContextPath()%>/navQuestionLink"><span> Create Question</span></a></li>
					<li><a href="<%=request.getContextPath()%>/navViewQuestionLinks"><span> View Questions</span></a></li>
			
				</ul>
			</li>
			
			<li class='has-sub '><a href='#'><span>KNN All Users Computation</span></a>
				<ul>
					<li><a href="<%=request.getContextPath()%>/viewdatasetsnav"><span> View Data Sets</span></a></li>
					<li><a href="<%=request.getContextPath()%>/viewhistoryallusersnav"><span> History</span></a></li>
					<li><a href="<%=request.getContextPath()%>/viewknnallusersnav"><span> KNN</span></a></li>
					<li><a href="<%=request.getContextPath()%>/viewscoreallusersnav"><span> Score</span></a></li>
			
				</ul>
			</li>
			
			
			<li class='active '>
			<a
				href="<%=request.getContextPath()%>/logout.do">
				<span>Logout</span>
			</a>
			</li>
		</ul>
	</div>
</body>
</html>