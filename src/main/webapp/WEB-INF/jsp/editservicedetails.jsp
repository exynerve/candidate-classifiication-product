<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <link href="<%=request.getContextPath()%>/css/styles.css"
         rel="stylesheet" type="text/css">
   </head>
   <body>
      <div id='cssmenu'>
            <ul>
               <li class='active '><a
                  href="<%=request.getContextPath()%>/jsp/editservicedetails.jsp"><span>Edit Service Details</span></a></li>
            </ul>
         <ul>
            <li class='has-sub '><a href='<%=request.getContextPath()%>/logout.do'>Logout<span></span></a></li>
         </ul>
      </div>
   </body>
</html>