<!DOCTYPE html>
<html>
<%@page import="com.model.messages.Messages"%>
<%@page import="com.classifycandidatepro.response.AJAXResponse"%>
<%@page import="com.classifycandidatepro.model.AnswerVO"%>
<%@page import="com.classifycandidatepro.model.ClassifyInfo"%>
<%@page import="com.classifycandidatepro.model.SuggestionObj"%>
<%@page import="com.classifycandidatepro.storageobjects.CompanyInformationVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<head>
<title>Analysis User</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <style>

.message1{
background:yellow;
color:black;
padding:10px;
}
</style>

<script>
			function preventBack(){window.history.forward();}
			setTimeout("preventBack()", 0);
			window.onunload=function(){null};
		</script>
</head>
<body>
	<a href="<%=request.getContextPath()%>/navcustomerhome""
		class="btn btn-blue btn-effect">Home</a>
<div class="container">
	<%
		AJAXResponse ajaxResponse = (AJAXResponse) request
				.getAttribute(Messages.Keys.OBJ);
				ClassifyInfo classifyInfo = (ClassifyInfo) ajaxResponse
				.getModel();
		
		if(classifyInfo.isStatus()){
			
			if(null==classifyInfo.getModel()){
				
				
			}else{
			
				List<CompanyInformationVO> companyInformationVOs =(List<CompanyInformationVO>) classifyInfo.getModel();
			
				if(null==companyInformationVOs){
			%>
				<div class="alert alert-danger">
					No Recommendations as the Company Info does not have details set by Admin
				</div>
			
			<%
					
				}else{
					
					for(CompanyInformationVO companyInformationVO:companyInformationVOs){
						
				%>
					<div class="panel panel-primary">
					<div class="panel-heading"><%=companyInformationVO.getCompanyName()%></div>
					<div class="panel-body">
					<div class="row">
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
							
							<div>
								Company Name :<h1> <%=companyInformationVO.getCompanyName()%></h1>
								Cluster No: <h1><%=companyInformationVO.getCluster()%></h1>
							</div>
						
						</div>
						<div class="col-lg-3 col-md-4 col-sm-12 col-xs-12">
							<div>
								<img src="<%=request.getContextPath()%>/companyimages/<%=companyInformationVO.getCompanyImageURL()%>" class="image_container">
							</div>
						
						</div>
						
						<div class="col-lg-5 col-md-4 col-sm-12 col-xs-12">
							<a href="<%=companyInformationVO.getCompanyURL()%>"><%=companyInformationVO.getCompanyURL()%></a>
						</div>
					</div>
					</div>
					</div>	
					<div style="height:10px;"></div>
				<%	
					}
					
					
				}
			
			}
			%>
			
	<%
		}else{
			
			String errorMessage=classifyInfo.getErrMsg();
	%>
		<div class="alert alert-danger">
			<%=errorMessage%>
		</div>
	<%
			
		}
		%>
</div>		