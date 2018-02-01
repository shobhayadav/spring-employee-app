<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Employee Application Home</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link type="text/css" href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet" />
</head>
<body>
	
		<div class="container">
						<div class="well well-sm">Employee List</div>
						<table  class="table table-bordered table-hover table-striped">
							<thead>
								<tr>
									<th>Name</th>
									<th>Joining Date</th>
									<th>Salary</th>
									<th>SSN</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							     <c:forEach items="${employees}" var="employee">
								<tr class="row100 body">
									<td >${employee.name}</td>
									<td >${employee.joiningDate}</td>
									<td >${employee.salary}</td>
									<td ><a href="<c:url value='/edit-${employee.ssn}-employee' />">${employee.ssn}</a></td>
									<td ><a href="<c:url value='/delete-${employee.ssn}-employee' />">delete</a></td>
								</tr>
								  </c:forEach>
							</tbody>
						</table>
					<a href="<c:url value='/new' />">Add New Employee</a>
				</div>
	
</body>
</html>