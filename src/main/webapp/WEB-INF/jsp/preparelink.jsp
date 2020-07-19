<jsp:include page="/WEB-INF/jsp/includekendo.jsp"></jsp:include>
<head>
<script src="<%=request.getContextPath()%>/customjs/savepreparelink.js"></script>

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/adminmenu.jsp"></jsp:include>



	<div class="container">

		<div class="col-xs-12">
			<div ng-app="savePrepareLinkApp" ng-controller="savePrepareLinkController">

				<div class="alert alert-info">
					<strong>Info!</strong>Prepare Link Information Save
				</div>

				<div class="panel panel-primary">

					<div class="panel-heading">Prepare Link Information Save</div>
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
									<label for="prepareName">Prepare Link Name:</label> 
									<input type="text"
										ng-model="register.prepareName" 
										ng-disabled="data_submission"
										class="form-control" id="prepareName">
								</div>
								
							</div>



							<div class="col-xs-6">
								
								<div class="form-group">
									<label for="prepareUrl">Prepare URL:</label> 
									<input type="text"
										ng-model="register.prepareUrl" 
										ng-disabled="data_submission"
										class="form-control" 
										id="prepareUrl">
								</div>
							
							</div>

						</div>

						<div class="col-xs-12">

						
							<div class="col-xs-6">

								<div class="form-group">
									<label for="questionType">Type :</label>
									
									 <select
										kendo-drop-down-list="questiontype" id="questiontype"
										k-data-text-field="'questiondesc'" k-data-value-field="'questiontype'"
										k-data-source="questionTypeDataSource" ng-model="register.questionType"
										ng-disabled="data_submission" class="form-control">
									</select>

								</div>


							</div>

						</div>



						<div class="col-xs-12">

							<div class="col-xs-6">
								<div class="form-group">
									<div class="col-sm-offset-6 col-sm-6">
										<button type="submit" ng-disabled="data_submission"
											class="btn btn-primary" ng-click="submitPrepareLink()">Submit
											Save Prepare Link</button>
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