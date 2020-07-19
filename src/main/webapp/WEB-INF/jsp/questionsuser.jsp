<%@page import="com.model.messages.Messages"%>
<%@page import="com.classifycandidatepro.response.AJAXResponse"%>
<%@page import="com.classifycandidatepro.model.AnswerVO"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html> 
<html>
<head>
		<title>Questions</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/errormsg.css">
	
		<script>
			function preventBack(){window.history.forward();}
			setTimeout("preventBack()", 0);
			window.onunload=function(){null};
		</script>
	
</head>

<body background="<%=request.getContextPath()%>/img/slider/banner.jpg">
<a href="<%=request.getContextPath()%>/navcustomerhome" class="btn btn-blue btn-effect">User Home</a>
<div class="isa_success">

	<font color="red"></font>
	<form action="<%=request.getContextPath()%>/onlineTestUser" method="post">

		<%
			AJAXResponse ajaxResponse = (AJAXResponse) request
						.getAttribute(Messages.Keys.OBJ);
				AnswerVO questionTableValue = (AnswerVO)ajaxResponse.getModel();
		%>

		<table>
			<tr>
				<td><input type="hidden" name="patName"
					value="<%=questionTableValue.getPatName()%>" /></td>
			</tr>

			<tr>

					<div style="border-color: black; background-color: aqua;">
						<table>
							<tr>
								<td><%=questionTableValue.getQuestion().getQuestionDesc()%>
									<input type="hidden" name="question.questionDesc"
									value="<%=questionTableValue.getQuestion().getQuestionDesc()%>" />
								</td>
							</tr>
							<tr>
								<td>
								
									<input type="hidden" name="question.quesId"
									value="<%=questionTableValue.getQuestion().getQuesId()%>" />
									<input type="hidden" name="question.pageNumber"
									value="<%=questionTableValue.getQuestion().getPageNumber()%>" />
									<input type="hidden" name="pageNumberGlobal"
									value="<%=questionTableValue.getQuestion().getPageNumber()%>" />

									<% String ans1 = questionTableValue.getQuestion().getAnswer1();
											if(ans1!=null && !ans1.isEmpty()){
										%> <input type="hidden" name="question.answer1"
									value="<%=ans1%>" />
							<tr>
								<td><%=ans1%></td>
								<td><input type="radio" name="question.selectedAnswer"
									value="1" /></td>
							</tr>
							<%
											}
										%>

							<% String ans2 = questionTableValue.getQuestion().getAnswer2();
											if(ans2!=null && !ans2.isEmpty()){
										%>
							<input type="hidden" name="question.answer2" value="<%=ans2%>" />

							<tr>
								<td><%=questionTableValue.getQuestion().getAnswer2()%></td>
								<td><input type="radio" name="question.selectedAnswer"
									value="2" /></td>
							</tr>
							<%
											}
										%>


							<% String ans3 = questionTableValue.getQuestion().getAnswer3();
											if(ans3!=null && !ans3.isEmpty()){
										%>
							<input type="hidden" name="question.answer3" value="<%=ans3%>" />
							<tr>
								<td><%=questionTableValue.getQuestion().getAnswer3()%></td>
								<td><input type="radio" name="question.selectedAnswer"
									value="3" /></td>
							</tr>
							<%
											}
										%>

							<% String ans4 = questionTableValue.getQuestion().getAnswer4();
											if(ans4!=null && !ans4.isEmpty()){
										%>
							<input type="hidden" name="question.answer4" value="<%=ans4%>" />
							<tr>
								<td><%=questionTableValue.getQuestion().getAnswer4()%></td>
								<td><input type="radio" name="question.selectedAnswer"
									value="4" /></td>
							</tr>
							<%
											}
										%>

							<tr>
								<input type="hidden" name="question.rating1"
									value="<%=questionTableValue.getQuestion().getRating1()%>" />
								<input type="hidden" name="question.rating2"
									value="<%=questionTableValue.getQuestion().getRating2()%>" />
								<input type="hidden" name="question.rating3"
									value="<%=questionTableValue.getQuestion().getRating3()%>" />
								<input type="hidden" name="question.rating4"
									value="<%=questionTableValue.getQuestion().getRating4()%>" />
								<input type="hidden" name="question.questiontype"
									value="<%=questionTableValue.getQuestion().getQuestiontype()%>" />
								</td>

							</tr>
						</table>
					</div>
			</tr>
		</table>
</div>
		<table>
			<tr>
				<td><input type="submit" name="actionCode" class="btn btn-blue btn-effect" value="NEXT" /></td>
				<%
					int pageNumberTemp =questionTableValue.getQuestion().getPageNumber();
				
					if(pageNumberTemp>1){
				%>
				<td><input type="submit" class="btn btn-blue btn-effect" name="actionCode" value="BACK" /></td>
				<%
					}
				%>
			</tr>
		</table>
	</form>
</div>
</body>
</html>