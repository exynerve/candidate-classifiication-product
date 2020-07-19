<jsp:include page="/WEB-INF/jsp/includekendo.jsp"></jsp:include>
<head>
<script src="<%=request.getContextPath()%>/customjs/saveccompany.js"></script>

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/adminmenu.jsp"></jsp:include>



	<div class="container">

		<div class="col-xs-12">
			<div ng-app="saveCompanyApp" ng-controller="saveCompanyController">

				<div class="alert alert-info">
					<strong>Info!</strong>Company Information Save
				</div>




				<div class="panel panel-primary">

					<div class="panel-heading">Company Information Save</div>
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
									<label for="companyName">Company Name:</label> 
									<input type="text"
										ng-model="register.companyName" 
										ng-disabled="data_submission"
										class="form-control" id="companyName">
								</div>
								
							</div>



							<div class="col-xs-6">
								
								<div class="form-group">
									<label for="companyURL">Company URL:</label> 
									<input type="text"
										ng-model="register.companyURL" 
										ng-disabled="data_submission"
										class="form-control" 
										id="companyURL">
								</div>
							
							</div>

						</div>

						<div class="col-xs-12">

							<div class="col-xs-6">

								<div class="form-group">
									<label for="companyImageURL">Company Image URL:</label> 
									<input type="text"
										ng-model="register.companyImageURL"
										ng-disabled="data_submission" 
										class="form-control" 
										id="companyImageURL">
								</div>

							</div>
							<div class="col-xs-6">

								<div class="form-group">
									<label for="cluster">Cluster :</label>
									
									 <select
										kendo-drop-down-list="cluster" id="cluster"
										k-data-text-field="'cluster'" k-data-value-field="'cluster'"
										k-data-source="clusterDataSource" ng-model="register.cluster"
										ng-disabled="data_submission" class="form-control">
									</select>

								</div>


							</div>

						</div>



						<div class="col-xs-12">

							<div class="col-xs-6">
								<div class="form-group">
									<div class="col-sm-offset-6 col-sm-6">
										<button type="submit" ng-disabled="disable_server_request"
											class="btn btn-primary" ng-click="submitCompany()">Submit
											Company</button>
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