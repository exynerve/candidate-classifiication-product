      angular.module("saveCompanyApp", ["kendo.directives"]).controller("saveCompanyController", function ($scope, $http) {

            $scope.errorMsg = "";
            $scope.sucessMsg = "";
            $scope.showSucess = false;
            $scope.showError = false;

            $scope.register = {
            	companyURL: "",
            	companyName: "",
            	companyImageURL: "",
                cluster: ""

            };
            
        _isEmpty = function (val) {
            return (val === undefined || val == null || val.length <= 0) ? true : false;
        }

        $http.get(contextPath+'/viewClusterTypes')
        .then(function(response) {
            $scope.clusterTypes = response.data.model;
        	$scope.clusterDataSource={
        		 data:  $scope.clusterTypes,
                 schema: {
                     model: {
                         fields: {
                        	 		cluster: { type: "string" }
                         		 }
                     }
                 }
        	};
            
        });
        
        
        
        $scope.submitCompany = function () {

            $scope.errorMsg = "";
            $scope.sucessMsg = "";
            $scope.showSucess = false;
            $scope.showError = false;
			$scope.data_submission=true;

			
			
            if (_isEmpty($scope.register.companyName)) {
                $scope.showError = true;
                $scope.errorMsg = "Company Name is mandatory";
                $scope.data_submission=false;
                return;
            }

            if (_isEmpty($scope.register.companyURL)) {
                $scope.showError = true;
                $scope.errorMsg = "Company URL is mandatory";
                $scope.data_submission=false;
                return;
            }
			
			if (_isEmpty($scope.register.companyImageURL)) {
                $scope.showError = true;
                $scope.errorMsg = "Company Image URL is Mandatory";
                $scope.data_submission=false;
                return;
            }


            if (_isEmpty($scope.register.cluster)) {
                $scope.showError = true;
                $scope.errorMsg = "Cluster is mandatory";
                $scope.data_submission=false;
                return;
            }

				
		
		

            $http({
                method: 'POST',
                url: contextPath+'/saveCompanyInfo',
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