      angular.module("savePrepareLinkApp", ["kendo.directives"]).controller("savePrepareLinkController", function ($scope, $http) {

            $scope.errorMsg = "";
            $scope.sucessMsg = "";
            $scope.showSucess = false;
            $scope.showError = false;

            $scope.register = {
            	prepareUrl: "",
            	questionType: "",
            	prepareName: ""

            };
            
        _isEmpty = function (val) {
            return (val === undefined || val == null || val.length <= 0) ? true : false;
        }

        $http.get(contextPath+'/viewQuestionTypes')
        .then(function(response) {
            $scope.questionTypes = response.data.model;
        	$scope.questionTypeDataSource={
        		 data:  $scope.questionTypes,
                 schema: {
                     model: {
                         fields: {
                        	 		questiontype: { type: "string" },
                        	 		questiondesc: { type: "string" }
                         		 }
                     }
                 }
        	};
            
        });
        
        
        
        $scope.submitPrepareLink = function () {

            $scope.errorMsg = "";
            $scope.sucessMsg = "";
            $scope.showSucess = false;
            $scope.showError = false;
			$scope.data_submission=true;

			
			
            if (_isEmpty($scope.register.prepareName)) {
                $scope.showError = true;
                $scope.errorMsg = "Prepare Name is mandatory";
                $scope.data_submission=false;
                return;
            }

            if (_isEmpty($scope.register.prepareUrl)) {
                $scope.showError = true;
                $scope.errorMsg = "Prepare URL is mandatory";
                $scope.data_submission=false;
                return;
            }
			
			if (_isEmpty($scope.register.questionType)) {
                $scope.showError = true;
                $scope.errorMsg = "Question Type is Mandatory";
                $scope.data_submission=false;
                return;
            }

            $http({
                method: 'POST',
                url: contextPath+'/savePrepareInfo',
                data: $scope.register,
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                console.log('Response');
                console.log(response);
                var JsonData = response.data;

                if (JsonData.status == false) {
                    $scope.showError = true;
                     $scope.showSucess = false;
                    $scope.errorMsg = JsonData.errMessages[0].errMessage;
                    $scope.data_submission=false;
                    return;
                } else {

                    $scope.showSucess = true;
                    $scope.showError = false;
                    $scope.sucessMsg = JsonData.sucessMsg;
                    $scope.data_submission=false;
                    return;
                }
				
					$scope.data_submission=false;


            }, function (response) {

			   $scope.data_submission=false;

                $scope.showError = true;
                $scope.showSucess = false;
                $scope.errorMsg = "An Internal error. Please Contact System Admin";
                
                return;

            });

        };


        });