<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" >
var contextPath='<%=request.getContextPath()%>';
</script>
    <meta charset="utf-8"/>
    <title>Rating Service Provider</title>

    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.2.621/styles/kendo.common.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.2.621/styles/kendo.rtl.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.2.621/styles/kendo.silver.min.css"/>
    <link rel="stylesheet" href="https://kendo.cdn.telerik.com/2017.2.621/styles/kendo.mobile.all.min.css"/>

   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://kendo.cdn.telerik.com/2017.2.621/js/angular.min.js"></script>
    <script src="https://kendo.cdn.telerik.com/2017.2.621/js/kendo.all.min.js"></script>
	
	 <script>
        /*
            This demo renders the grid in "DejaVu Sans" font family, which is
            declared in kendo.common.css. It also declares the paths to the
            fonts below using <tt>kendo.pdf.defineFont</tt>, because the
            stylesheet is hosted on a different domain.
        */
        kendo.pdf.defineFont({
            "DejaVu Sans"             : "https://kendo.cdn.telerik.com/2016.2.607/styles/fonts/DejaVu/DejaVuSans.ttf",
            "DejaVu Sans|Bold"        : "https://kendo.cdn.telerik.com/2016.2.607/styles/fonts/DejaVu/DejaVuSans-Bold.ttf",
            "DejaVu Sans|Bold|Italic" : "https://kendo.cdn.telerik.com/2016.2.607/styles/fonts/DejaVu/DejaVuSans-Oblique.ttf",
            "DejaVu Sans|Italic"      : "https://kendo.cdn.telerik.com/2016.2.607/styles/fonts/DejaVu/DejaVuSans-Oblique.ttf",
            "WebComponentsIcons"      : "https://kendo.cdn.telerik.com/2017.1.223/styles/fonts/glyphs/WebComponentsIcons.ttf"
        });
    </script>
	<!-- Load Pako ZLIB library to enable PDF compression -->
    <script src="https://kendo.cdn.telerik.com/2017.3.1026/js/pako_deflate.min.js"></script>
 
  <script src="<%=request.getContextPath()%>/customjs/ratingserviceprovider.js"></script>
 
</head>
<body>
 <jsp:include page="/WEB-INF/jsp/customermenu.jsp"></jsp:include>
  
  
  
  <div class="container">
  
  <div class="col-xs-12">
    <div ng-app="ratingApp" ng-controller="ratingController">
    
    	<div class="alert alert-info">
  		<strong>Info!</strong>Rate Service Providers
		</div>
		
		
		
        
    <div class="panel panel-primary">
        
    <div class="panel-heading">Rate Service Providers</div>
	<div class="panel-body">
        
        <div class="col-xs-12">
        
        <div ng-show="showInfoMessage">
        	<div class="alert alert-warning">
        		Please Wait Until Response Comes
        	</div>
        
        </div>
        
        <div ng-show="showSucessMessage">
		
			<div class="alert alert-success">
					{{sucessMsg}}
  			</div>
		
  		</div>
  		
  		<div ng-show="showFailureMessage">
		
			<div class="alert alert-danger">
				{{failureMsg}}
  			</div>
		
  		</div>
        
        </div>
        
        <div class="col-xs-12">
        
        <div class="col-xs-6">
         	<div class="form-group">
        		<label for="productType">Service Type:</label>
        		<select kendo-drop-down-list="serviceType"
            	k-data-text-field="'servicename'"
            	k-data-value-field="'servicename'"
            	k-data-source="serviceTypeDataSource"
            	ng-model="register.servicetype"
            	k-on-change="change_service_type()"
            	ng-disabled="disable_server_request"
            	class="form-control"></select>
        	</div>
  		</div>
        
      
        
        <div class="col-xs-6">
        	<div class="form-group">
        		<label for="companyName">Company Names:</label>
        		<select kendo-drop-down-list="companyName" id="companyName"
            	k-data-text-field="'companyname'"
            	k-data-value-field="'companyname'"
            	k-data-source="companyNameDataSource"
            	ng-model="register.companyName"
            	ng-disabled="disable_server_request"
            	class="form-control"></select>
        	</div>
        </div>
        
        </div>
        
        <div class="col-xs-12">
        
        <div class="col-xs-6">
        	
        	<div class="form-group">
        		<label for="ratingType">Rating Type:</label>
        	    <select kendo-drop-down-list="ratingType" id="ratingType"
            	k-data-text-field="'ratingdesc'"
            	k-data-value-field="'ratingname'"
            	k-data-source="ratingTypeDataSource"
            	ng-model="register.ratingType"
            	ng-disabled="disable_server_request"
            	class="form-control"></select>
        	   
        	</div>
        
        </div>
        <div class="col-xs-6">
        	
        	<div class="form-group">
        		<label for="xpath">Rating :</label>
        	    <select kendo-drop-down-list="rating" id="rating"
            	k-data-text-field="'rating'"
            	k-data-value-field="'rating'"
            	k-data-source="ratingDataSource"
            	ng-model="register.rating"
            	ng-disabled="disable_server_request"
            	class="form-control"></select>
        	   
        	</div>
 
        
        </div>
        
        </div>
        
        
        
        <div class="col-xs-12">
        
        	<div class="col-xs-6">
        	<div class="form-group">
   			 <div class="col-sm-offset-6 col-sm-6">
      				<button type="submit" ng-disabled="disable_server_request" class="btn btn-primary" ng-click="submitRating()">Submit Rating</button>
    		</div>
  			</div>
        	</div>
        	
        	</div>
        	
        	
        	<div class="col-xs-6">
        	</div>
        
        </div>
        
		
		
    </div>
    </div>
    </div>
    </div>
 
</body>
</html>