<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet" />
<title>Error Page</title>
</head>
<body>
<div class="container">
<div class="alert alert-danger">
  <strong>Error : </strong> ${errorMsg}
</div>
    <p>Go back to <a href="<c:url value='/list' />">List of All Employees</a></p>
   </div>  
</body>
 
</html>