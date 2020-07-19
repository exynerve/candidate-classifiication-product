<!DOCTYPE html>
<%@page import="com.model.messages.Messages"%>
<script type="text/javascript" >
var contextPath='<%=request.getContextPath()%>';
var app_name='<%=Messages.Keys.APP_NAME%>';
var end_point='<%=Messages.EndPoints.REGISTER_ENDPOINT%>';
</script>
<html class="no-js">
<!--<![endif]-->
<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="cssmenu/styles.css">
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script src="cssmenu/script.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,800,700' rel='stylesheet' type='text/css'>


    <!-- kendo and angular js -->
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
            "DejaVu Sans": "https://kendo.cdn.telerik.com/2016.2.607/styles/fonts/DejaVu/DejaVuSans.ttf",
            "DejaVu Sans|Bold": "https://kendo.cdn.telerik.com/2016.2.607/styles/fonts/DejaVu/DejaVuSans-Bold.ttf",
            "DejaVu Sans|Bold|Italic": "https://kendo.cdn.telerik.com/2016.2.607/styles/fonts/DejaVu/DejaVuSans-Oblique.ttf",
            "DejaVu Sans|Italic": "https://kendo.cdn.telerik.com/2016.2.607/styles/fonts/DejaVu/DejaVuSans-Oblique.ttf",
            "WebComponentsIcons": "https://kendo.cdn.telerik.com/2017.1.223/styles/fonts/glyphs/WebComponentsIcons.ttf"
        });
    </script>
    <!-- Load Pako ZLIB library to enable PDF compression -->
    <script src="https://kendo.cdn.telerik.com/2017.3.1026/js/pako_deflate.min.js"></script>


    <meta charset="utf-8"/>
    <meta content="text/html charset=UTF-8" http-equiv="Content-Type"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Register</title>
    <meta name="description"
          content="We are a vertically integrated online interior design and furniture company. We custom design home interiors, show you how your new home will look in 3D, manufacture the furniture at our factory and undertake project execution. 5 Year Warranty "/>
    <meta name="keywords"
          content="full house furnishing, full home interiors, home interiors, custom design furniture, interior desinger, interior designs, modular kitchen, furniture online, wardrobes online, furniture online india, bedroom furniture, online furniture, home furniture online, living room furniture, office furniture "/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="icon" href="css/static/images/miscellaneous/favicon.ico?3"/>

    <style>
		
		.productBtn{
			color: #fff!important;
			background-color: #4CAF50!important;
			padding: 8px 16px;
			float: left;
			width: auto;
			border: none;
			display: block;
			outline: 0;
		}

    </style>

	   <script src="<%=request.getContextPath()%>/js/serviceProviderApp.js"></script>


</head>


<body>


<div class="container" ng-app="serviceProviderApp" id="contentContainer">


 <div class="panel panel-primary">
        
    <div class="panel-heading">Register Service Provider</div>
	<div class="panel-body">

    <div ng-controller="serviceProviderController" class="main-content-wrapper">

	    <!-- Start of Error Messages -->
        <div class="row">
            <div class="alert alert-success" ng-show="showSucess">{{ sucessMsg }}</div>
            <div class="alert alert-danger" ng-show="showError">{{ errorMsg }}</div>
        </div>
		<!-- End of Error Messages -->

		<!-- Start of Row -->
		 <div class="row">
		 
			<!--- User Name -->
			<div class="col-lg-4 col-md-4 col-xs-12 col-sm-12">

                <div class="form-group">
                    <label for="userName"> User Name:</label>
                    <input type="text" class="form-control" id="userName"
                           ng-disabled="data_submission" ng-required="true"
                           name="userName" ng-model="userName" maxlength="20">
                </div>
            </div>
			<!--- User Name -->
			
			 <!--- Password -->
			<div class="col-lg-4 col-md-4 col-xs-12  col-sm-12">
         	   
                
                <div class="form-group">
                    <label for="password"> Password:</label>
                    <input type="password" class="form-control" id="password"
                           ng-disabled="data_submission" ng-required="true"
                           name="password" ng-model="register.password" maxlength="20">
                </div>
                
            </div>
			
			<!--- Password -->
			
			<!--- Email -->
			
            <div class="col-lg-4 col-md-4 col-xs-12  col-sm-12">
                
                <div class="form-group">
                    <label for="email">Email address:</label>
                    <input type="email" class="form-control" id="email"
                           name="email"
                           ng-disabled="data_submission"
                           ng-model="register.email" maxlength="40" ng-required="true">
                </div>
            </div>
			
			<!--- Email -->
	
			
		 
		 </div> <!-- End of 1st Row -->
		
		 <div class="row"> <!-- Start of 2nd Row -->
		 
		 	<!-- Phone No -->
            <div class="col-lg-4 col-md-4 col-xs-12  col-sm-12">
                
                <div class="form-group">
                    <label for="phoneNo">Phone No:</label>
                    <input type="text" class="form-control" id="phoneNo" name="phoneNo"
                           ng-disabled="data_submission"
                           ng-model="register.phoneNo"
                           maxlength="15" ng-required="true">
                </div>
            </div>
			<!-- Phone No -->
			
			<!-- Company Name -->
			
            <div class="col-lg-4 col-md-4 col-xs-12  col-sm-12">
                
					<div class="form-group">
                    <label for="companyName">Company Name:</label>
                    <input type="text" class="form-control" id="companyName" name="companyName"
                           ng-disabled="data_submission"
                           ng-model="register.companyName"
                           maxlength="15" ng-required="true">
 					</div>
                  
            </div>
			
			<!-- Company Name -->
			
			<!-- Company Description -->
			
            <div class="col-lg-4 col-md-4 col-xs-12  col-sm-12">
                
					<div class="form-group">
                    <label for="description">Services/Company Description:</label>
                    <textarea type="text" class="form-control" id="description" name="description"
                           ng-disabled="data_submission"
                           ng-model="register.description"
                           maxlength="100" ng-required="true"></textarea>
 					</div>
                  
            </div>
			
			<!-- Company Description -->
		 
		 </div><!-- End of 2nd Row -->
		
		<div class="row"> <!-- Start of 3rd Row -->
		
			<!-- Country -->
			
			<div class="col-lg-4 col-md-4 col-xs-12  col-sm-12">

                <div class="form-group">
                    <label for="country">Country</label>
                    <input type="text" class="form-control" id="country" name="country"
                           ng-disabled="data_submission"
                           ng-model="register.country"
                           maxlength="15" ng-required="true">
                </div>
				
            </div>
			
			<!-- Country -->
		
			<!-- Start of State -->
		
			<div class="col-lg-4 col-md-4 col-xs-12  col-sm-12">

                <div class="form-group">
                    <label for="state">State</label>
                    <input type="text" class="form-control" id="state" name="state"
                           ng-disabled="data_submission"
                           ng-model="register.state"
                           maxlength="15" ng-required="true">
                </div>
			</div>
			
			<!-- End of State -->
			
			<!-- Start of City -->
			
			<div class="col-lg-4 col-md-4 col-xs-12  col-sm-12">
                

                <div class="form-group">
                    <label for="city">City</label>
                    <input type="text" class="form-control" id="city" name="city"
                           ng-disabled="data_submission"
                           ng-model="register.city"
                           maxlength="15" ng-required="true">
                </div>
            </div>
			
			<!-- End of City -->
			
			
                
		
		
		</div> <!-- End of 3rd Row -->
		
		<div class="row">
		
		
		
			<!-- Start of Service Type -->
			
			 <div class="col-lg-2 col-md-2 col-xs-12  col-sm-12">
                <div class="form-group">
                <label for="serviceType">Service Type</label>
        		<select kendo-drop-down-list="serviceType"
            	k-data-text-field="'servicename'"
            	k-data-value-field="'servicename'"
            	k-data-source="serviceTypeDataSource"
            	ng-model="register.serviceType"
            	class="form-control"></select>
                </div>
                
            </div>
			
				
			<!-- End of Service Type -->
		
			 <div class="col-lg-2 col-md-2 col-xs-12  col-sm-12">
		
			<!-- Start of Cost -->
			<div class="form-group">
                    <label for="totalCostAllServices">Cost</label>
                    <input type="text" class="form-control" id="totalCostAllServices" name="totalCostAllServices"
                           ng-disabled="data_submission"
                           ng-model="register.totalCostAllServices"
                           maxlength="15" ng-required="true">
            </div>
            </div>
			<!-- End of Cost -->
        
		
		</div>
		
		<div class="row"><!-- Start of 5th Row -->
		
			<div class="col-lg-4 col-md-4 col-sm=12 col-xs-12">
			
			</div>
			
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
		
				<button type="submit" class="productBtn" ng-click="register_serviceprovider()">Register Service Provider</button>
			
			</div>
			
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<a class="productBtn" href="<%=request.getContextPath()%>/">Home</a>
			</div>
		
		</div> <!-- End of 5th Row -->
		
		
       

    </div> <!-- Closing of Controller -->
    
	</div> <!-- Closing of Panel Body -->

	</div><!-- Closing of Panel primary -->
	
</div> 
</body>