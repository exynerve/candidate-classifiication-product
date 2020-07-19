<jsp:include page="/WEB-INF/jsp/includekendo.jsp"></jsp:include>
<head>
<script src="<%=request.getContextPath()%>/customjs/savequestion.js"></script>

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/adminmenu.jsp"></jsp:include>



	<div class="container">

		<div class="col-xs-12">
			<div ng-app="saveQuestionApp" ng-controller="saveQuestionController">

				<div class="alert alert-info">
					<strong>Info!</strong>Prepare Question
				</div>

				<div class="panel panel-primary">

					<div class="panel-heading">Question Information Save</div>
					<div class="panel-body">

						<div class="col-xs-12">

							<div ng-show="showInfoMessage">
								<div class="alert alert-warning">Please Wait Until
									Response Comes</div>

							</div>

							<div ng-show="showSucess">

								<div class="alert alert-success">{{sucessMsg}}</div>

							</div>

							<div ng-show="showError">

								<div class="alert alert-danger">{{errorMsg}}</div>

							</div>

						</div>

						<div class="col-xs-12">

							<div class="col-xs-6">

								<div class="form-group">
									<label for="questionDesc">Question Desc:</label>
									
									<textarea rows="10" 
									cols="10" 
									id="questionDesc"
									name="questionDesc"
									ng-model="register.questionDesc"
									ng-disabled="data_submission"
									class="form-control" 
									>
									
									
									</textarea>
									
								</div>
								
							</div>



							<div class="col-xs-6">
								
								<div class="form-group">
									<label for="answer1">Answer1:</label> 
									<input type="text"
										ng-model="register.answer1" 
										ng-disabled="data_submission"
										class="form-control" 
										id="answer1">
								</div>
							
							</div>

						</div>
						
						<div class="col-xs-12">
						
							<!-- Answer2 -->
								
							<div class="col-xs-6">
								
								<div class="form-group">
									<label for="answer2">Answer2:</label> 
									<input type="text"
										ng-model="register.answer2" 
										ng-disabled="data_submission"
										class="form-control" 
										id="answer2">
								</div>
							
							</div>
							
							<!-- Answer2 -->
							
							<!-- Answer3 -->
							
							<div class="col-xs-6">
								
								<div class="form-group">
									<label for="answer3">Answer3:</label> 
									<input type="text"
										ng-model="register.answer3" 
										ng-disabled="data_submission"
										class="form-control" 
										id="answer3">
								</div>
							
							</div>
							
							<!-- Answer3 -->
						
						
						</div>
						
						<div class="col-xs-12">
						
						<!-- Answer4 -->
						
							
							<div class="col-xs-6">
								
								<div class="form-group">
									<label for="answer4">Answer4:</label> 
									<input type="text"
										ng-model="register.answer4" 
										ng-disabled="data_submission"
										class="form-control" 
										id="answer4">
								</div>
							
							</div>
							
							<!-- Answer4 -->
						
						<div class="col-xs-6">
								
								<div class="form-group">
									
									<label for="rating1">Rating1 :</label>
									
									 <select
										kendo-drop-down-list="rating1" id="rating1"
										k-data-text-field="'rating'" k-data-value-field="'rating'"
										k-data-source="ratingDataSource" ng-model="register.rating1"
										ng-disabled="data_submission" class="form-control">
									</select>
									
									
								</div>
							
							</div>	
					
						
						
						</div>
						
						
						<!--  Div for Rating 2 and Rating3 -->
						<div class="col-xs-12">
						
							<div class="col-xs-6">
								
								<div class="form-group">
									
									<label for="rating2">Rating2 :</label>
									
									 <select
										kendo-drop-down-list="rating2" id="rating2"
										k-data-text-field="'rating'" k-data-value-field="'rating'"
										k-data-source="ratingDataSource" ng-model="register.rating2"
										ng-disabled="data_submission" class="form-control">
									</select>
									
									
								</div>
							
							</div>
							
							<div class="col-xs-6">
								
								<div class="form-group">
									
									<label for="rating3">Rating3 :</label>
									
									 <select
										kendo-drop-down-list="rating3" id="rating3"
										k-data-text-field="'rating'" k-data-value-field="'rating'"
										k-data-source="ratingDataSource" ng-model="register.rating3"
										ng-disabled="data_submission" class="form-control">
									</select>
									
									
								</div>
							
							</div>	
					
						
						</div>
						
						<!--  Div for Rating 4 -->
						<div class="col-xs-12">
						
							<div class="col-xs-6">
								
								<div class="form-group">
									
									<label for="rating4">Rating4 :</label>
									
									 <select
										kendo-drop-down-list="rating4" id="rating4"
										k-data-text-field="'rating'" k-data-value-field="'rating'"
										k-data-source="ratingDataSource" ng-model="register.rating4"
										ng-disabled="data_submission" class="form-control">
									</select>
									
									
								</div>
							
							</div>
							
							<!--  Question Type -->

								<div class="col-xs-6">

								<div class="form-group">
									<label for="questionType">Type :</label>
									
									 <select
										kendo-drop-down-list="questiontype" id="questiontype"
										k-data-text-field="'questiondesc'" k-data-value-field="'questiontype'"
										k-data-source="questionTypeDataSource" ng-model="register.questiontype"
										ng-disabled="data_submission" class="form-control">
									</select>

								</div>


							</div>
								
							<!--  End of Question Type -->						
						</div>
						
						

						<div class="col-xs-12">

							<div class="col-xs-6">
								<div class="form-group">
									<div class="col-sm-offset-6 col-sm-6">
										<button type="submit" ng-disabled="data_submission"
											class="btn btn-primary" ng-click="submitQuestion()">
											Save Question</button>
									</div>
								</div>
							</div>

						</div>


						<div class="col-xs-6"></div>

					</div>



				</div>
			</div>
		</div>
	</div>

</body>
</html>