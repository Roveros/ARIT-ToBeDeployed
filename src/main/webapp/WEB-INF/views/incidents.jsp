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
		<title>Incidents Page</title>
	</head>
	
	<body>
		<!-- attribute from login method -->
		<h3>Hello ${username}</h3>
		
		<sec:authorize access="hasRole('ROLE_USER')">
			<c:if test="${!empty listIncidents}">
			<h3>Incident List</h3>
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
			<h3>
				Record an Incident
			</h3>
			
			<c:url var="addAction" value="/incident/add" ></c:url>
			
			<form:form action="${addAction}" commandName="incident">
				<table id="hor-minimalist-b">
					<c:if test="${!empty incident.student_name}">
					<tr>
						<td>
							<form:label path="id">
								<spring:message text="ID"/>
							</form:label>
						</td>
						<td>
							<form:input path="id" readonly="true" size="8" disabled="true"/>	
					    	<form:hidden path="id" />					    			
						</td> 
					</tr>
					<tr>
						<td>
							<form:label path="date_created">
								<spring:message text="Date Created"/>
							</form:label>
						</td>
						<td>
							<form:input path="date_created" readonly="true" disabled="true"/>	
					    	<form:hidden path="date_created" />					    			
						</td> 
					</tr>
					</c:if>
					<tr>
						<td>
							<form:label path="student_name">
								<spring:message text="Student Name"/>
							</form:label>
						</td>
						<td>
							<form:input path="student_name" />
						</td> 
					</tr>
					<tr>
						<td>
							<form:label path="student_number">
								<spring:message text="Student Number"/>
							</form:label>
						</td>
						<td>
							<form:input path="student_number" />
						</td>
					</tr>
					<tr>
						<td>
							<form:label path="course_description">
								<spring:message text="Course Description"/>
							</form:label>
						</td>
						<td>
							<form:input path="course_description" />
						</td> 
					</tr>
					<tr>
					   <td><form:label path="year">Year</form:label></td>
					   <td>
					      <form:select path="year">
					      <%--    <form:option value="NONE" label="Select"/> --%>
					         <form:options items="${yearList}" />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
						<td>
							<form:label path="module_code">
								<spring:message text="Module Code"/>
							</form:label>
						</td>
						<td>
							<form:input path="module_code" />
						</td> 
					</tr>
					<tr>
						<td>
							<form:label path="lecturer">
								<spring:message text="Lecturer"/>
							</form:label>
						</td>
					    <td>
					    	<form:input path="lecturer" value="${username}" readonly="true" disabled="true"/>	
					    	<form:hidden path="lecturer" />					    					    
						</td> 
					</tr>
					<tr>
						<td>
							<form:label path="department">
								<spring:message text="Department"/>
							</form:label>
						</td>
					   <td>
					      <form:select path="department">
					      <%--    <form:option value="NONE" label="Select"/> --%>
					         <form:options items="${departmentList}" />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
					   <td><form:label path="plagiarism">Plagiarism Suspected</form:label></td>
					   <td>
					      <form:select path="plagiarism">
					         <form:option value="Yes" label="Yes"/>
					         <form:options items="${choiceList}" />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
				    	<tr>
				         	<td><form:label path="summary">Incident Summary</form:label></td>
				         	<td><form:textarea path="summary" rows="5" cols="30" /></td>				     
						</tr>					
					<tr>
						<td colspan="2">
							<c:if test="${!empty incident.student_name}">
								<input type="submit"
									value="<spring:message text="Edit Incident"/>" />
							</c:if>
							<c:if test="${empty incident.student_name}">
								<input type="submit"
									value="<spring:message text="Add Incident"/>" />
							</c:if>
						</td>
					</tr>
				</table>
			</form:form>
		</sec:authorize>
		
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
	</body>
</html>
