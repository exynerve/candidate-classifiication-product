      angular.module("saveQuestionApp", ["kendo.directives"]).controller("saveQuestionController", function ($scope, $http) {

            $scope.errorMsg = "";
            $scope.sucessMsg = "";
            $scope.showSucess = false;
            $scope.showError = false;

            $scope.register = {
            	answer1: "",
            	answer2: "",
            	answer3: "",
            	answer4: "",
            	rating1:"",
            	rating2:"",
            	rating3:"",
            	rating4:"",
            	questiontype:"",
            	questionDesc:""
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
        
        
        $http.get(contextPath+'/viewRatings')
        .then(function(response) {
            $scope.ratings = response.data.model;
        	$scope.ratingDataSource={
        		 data:  $scope.ratings,
                 schema: {
                     model: {
                         fields: {
                        	 		rating: { type: "int" }
                         		 }
                     }
                 }
        	};
            
        });
        
        
        
        $scope.submitQuestion = function () {

            $scope.errorMsg = "";
            $scope.sucessMsg = "";
            $scope.showSucess = false;
            $scope.showError = false;
			$scope.data_submission=true;

			
			
            if (_isEmpty($scope.register.questionDesc)) {
                $scope.showError = true;
                $scope.errorMsg = "Question Description is mandatory";
                $scope.data_submission=false;
                return;
            }

            if (_isEmpty($scope.register.answer1)) {
                $scope.showError = true;
                $scope.errorMsg = "Answer1 is mandatory";
                $scope.data_submission=false;
                return;
            }
            
            if (_isEmpty($scope.register.answer2)) {
                $scope.showError = true;
                $scope.errorMsg = "Answer2 is mandatory";
                $scope.data_submission=false;
                return;
            }
            
            if (_isEmpty($scope.register.answer3)) {
                $scope.showError = true;
                $scope.errorMsg = "Answer3 is mandatory";
                $scope.data_submission=false;
                return;
            }
            
            if (_isEmpty($scope.register.answer4)) {
                $scope.showError = true;
                $scope.errorMsg = "Answer4 is mandatory";
                $scope.data_submission=false;
                return;
            }
            
            if (_isEmpty($scope.register.rating1)) {
                $scope.showError = true;
                $scope.errorMsg = "Rating1 is mandatory";
                $scope.data_submission=false;
                return;
            }
            
            if (_isEmpty($scope.register.rating2)) {
                $scope.showError = true;
                $scope.errorMsg = "Rating2 is mandatory";
                $scope.data_submission=false;
                return;
            }
            
            if (_isEmpty($scope.register.rating3)) {
                $scope.showError = true;
                $scope.errorMsg = "Rating3 is mandatory";
                $scope.data_submission=false;
                return;
            }
            
            if (_isEmpty($scope.register.rating4)) {
                $scope.showError = true;
                $scope.errorMsg = "Rating4 is mandatory";
                $scope.data_submission=false;
                return;
            }
			
			if (_isEmpty($scope.register.questiontype)) {
                $scope.showError = true;
                $scope.errorMsg = "Question Type is Mandatory";
                $scope.data_submission=false;
                return;
            }

            $http({
                method: 'POST',
                url: contextPath+'/saveQuestions',
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