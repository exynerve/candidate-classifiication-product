angular.module("ratingApp", ["kendo.directives"]).controller("ratingController", function($scope,$http) {
    	
    	$scope.showInfoMessage =false;
    
    	$scope.showSucessMessage=false;
    	
    	$scope.showFailureMessage=false;
    	
    	
    	 $http.get(contextPath+'/viewServiceTypes')
         .then(function(response) {
            $scope.serviceTypes = response.data.model;
         	$scope.serviceTypeDataSource={
         		 data:  $scope.serviceTypes,
                  schema: {
                      model: {
                          fields: {
                         	 servicename: { type: "string" },
                         	 servicedesc: { type: "string" }
                          }
                      }
                  }
         	};
             
         });
    	
    	
    	$http({
		    method: 'GET',
		    url: contextPath+'/viewAllCompanyNames',
		    headers: {'Content-Type': 'application/json'}
		}).then(function(response){
				$scope.companynames = response.data.model;
	        	$scope.companyNameDataSource={
	        		 data:  $scope.companynames,
	                 schema: {
	                     model: {
	                         fields: {
	                        	 companyname: { type: "string" }
	                         }
	                     }
	                 }
	        	};
		});
    	
    	//Obtain the Rating Types
    	$http({
		    method: 'GET',
		    url: contextPath+'/viewAllRatingTypes',
		    headers: {'Content-Type': 'application/json'}
		}).then(function(response){
				$scope.ratingtypenames = response.data.model;
	        	$scope.ratingTypeDataSource={
	        		 data:  $scope.ratingtypenames,
	                 schema: {
	                     model: {
	                         fields: {
	                        	 ratingname: { type: "string" },
	                        	 ratingdesc: { type: "string" }
	                         }
	                     }
	                 }
	        	};
		});
    	
    	//Obtain the Ratings
    	$http({
		    method: 'GET',
		    url: contextPath+'/viewAllRatings',
		    headers: {'Content-Type': 'application/json'}
		}).then(function(response){
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
    	
    	
    	$scope.change_service_type=function(){
    		console.log('value');
    		console.log($scope.register.servicetype);
    		$scope.companynames=[];
    		
    		$http({
    		    method: 'GET',
    		    url: contextPath+'/retriveAllCompanyNamesForServiceType',
    		    params: {type: $scope.register.servicetype},
    		    headers: {'Content-Type': 'application/json'}
    		}).then(function(response){
    			 $scope.companynames = response.data.model;
    			 $scope.companyNameDataSource={
    	        		 data:  $scope.companynames,
    	                 schema: {
    	                     model: {
    	                         fields: {
    	                        	 companyname: { type: "string" }
    	                         }
    	                     }
    	                 }
    	        	};
    		});
    	}
    	
    	
    	
    	
    	$scope.submitRating=function(){
    		
    		$scope.disable_server_request =true;
    	
    		if(!$scope.register.servicetype){
    			$scope.failureMsg="Please select Service Type";
    			return;
    		}
    		
    		if(!$scope.register.companyName){
    			$scope.failureMsg="Please select Company Name";
    			return;
    		}
    		
    		if(!$scope.register.ratingType){
    			$scope.failureMsg="Please select Rating Type";
    			return;
    		}
    		
    		if(!$scope.register.rating){
    			$scope.failureMsg="Please select Rating";
    			return;
    		}
    		
    		var obj={};
        	obj.servicetype=$scope.register.servicetype;
    		obj.companyName=$scope.register.companyName;
    		obj.ratingType=$scope.register.ratingType;
    		obj.rating=$scope.register.rating;
    		
    		$scope.failureMsg='';
    		$scope.sucessMsg='';
    		$scope.showSucessMessage=false;
    		$scope.showFailureMessage=false;
    		
    		$http({
    		    method: 'POST',
    		    url: contextPath+'/storerating',
    		    data: obj,
    		    headers: {'Content-Type': 'application/json'}
    		}).then(function(response){
    			console.log('Response');
    			$scope.showInfoMessage =false;
    			console.log(response);
    			var JsonData = response.data;
    			
    			if (JsonData.ebErrors != null) {
					var errorObj = JsonData.errMessages;
					for (i = 0; i < errorObj.length; i++) {
						var value = errorObj[i].errMessage;
						$scope.showFailureMessage=true;	
						$scope.failureMsg=value;
					}
					
				} else {
					$scope.showInfoMessage =false;
					var value = JsonData.sucessMsg;
					$scope.showSucessMessage=true;
					$scope.sucessMsg=value;
				}
    			
    			$scope.disable_server_request =false;
    			
    		});
    		
    	};
    });