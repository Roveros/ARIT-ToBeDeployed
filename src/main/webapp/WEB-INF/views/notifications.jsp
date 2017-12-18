<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
	<ul id="nav">
		<li><a href="incidents">Incidents</a></li>
		<li><a href="notifications">Notifications ${notifications}</a></li>
		<li><a href="myprofile">My Profile</a></li>
		<li><a href="javascript:formSubmit()">Logout</a></li>
	</ul>
		<!-- For logging out -->
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
		<form action="${logoutUrl}" method='POST' id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Notifications Page</title>
	</head>
	
	<body>
		<!-- attribute from login method -->
		<h3>Hello ${username}</h3>
		
		<sec:authorize access="hasRole('ROLE_USER')">
			<h3>Notification List</h3>
			<c:if test="${!empty listIncidents}">
				<table id="hor-minimalist-a">
				<tr>
					<th >Incident ID</th>
					<th >Student Number</th>
					<th >Student Name</th>
					<th >Incident Status</th>
					<th >View</th>	
					<th >Delete</th>				
				</tr>
				<c:forEach items="${listIncidents}" var="incident">
					<tr>
						<td>${incident.id}</td>
						<td>${incident.student_number}</td>
						<td>${incident.student_name}</td>
						<td>${incident.status}</td>
						<td><a href="<c:url value='/incident/${incident.id}' />" >View</a></td>
						<td><a href="<c:url value='/remove/${incident.id}' />" >Delete</a></td>
					</tr>
				</c:forEach>
				</table>
			</c:if>
			</sec:authorize>
			<br>
		<sec:authorize access="!hasRole('ROLE_USER')">
			<h3>Incident List</h3>
			<c:if test="${!empty listIncidents}">
				<table id="hor-minimalist-a">
				<tr>
					<th >Incident ID</th>
					<th >Student Number</th>
					<th >Student Name</th>
					<th >Incident Status</th>
					<th >View</th>
				</tr>
				<c:forEach items="${listIncidents}" var="incident">
					<tr>
						<td>${incident.id}</td>
						<td>${incident.student_number}</td>
						<td>${incident.student_name}</td>
						<td>${incident.status}</td>
						<td><a href="<c:url value='/incident/${incident.id}' />" >View</a></td>
					</tr>
				</c:forEach>
				</table>
			</c:if>
		</sec:authorize>
		<br>	
	</body>
</html>
