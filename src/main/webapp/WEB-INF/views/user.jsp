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
	<li><a href="../admin">Back</a></li>
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
		<title>Admin Page</title>
	</head>
	<img src="<c:url value="../resources/images/ITBlogoNight.jpg"/>" class="logo"/>
		<body>
		<!-- attribute from login method -->
		<h3>Hello ${userName}</h3>

			<c:url var="addAction" value="/admin/add" ></c:url>
			
			<form:form action="${addAction}" commandName="userRoles">
				<table id="hor-minimalist-b">
					<c:if test="${!empty userRoles.users.username}">
					<tr>
						<td>
							<form:label path="user_role_id">
								<spring:message text="ID"/>
							</form:label>
						</td>
						<td>
							<form:input path="user_role_id" readonly="true" size="8"  disabled="true" />
							<form:hidden path="user_role_id" />
						</td> 
					</tr>
					</c:if>
					<tr>
						<td>
							<form:label path="users.username">
								<spring:message text="Name"/>
							</form:label>
						</td>
						<td>
							<form:input path="users.username" />
						</td> 
					</tr>
					<tr>
						<td>
							<form:label path="users.password">
								<spring:message text="Password"/>
							</form:label>
						</td>
						<td>
							<form:input path="users.password" />
						</td>
					</tr>
					<tr>
					   <td><form:label path="role">Role</form:label></td>
					   <td>
					      <form:select path="role">
					         <%-- <form:option value="NONE" label="Select"/> --%>
					         <form:options items="${roleList}" />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
						<td>
							<form:label path="users.enabled">
								<spring:message text="Enabled"/>
							</form:label>
						</td>
					   	<td>
					      	<form:select path="users.enabled">				          	
					        	<form:options items="${choiceList}" />
					      	</form:select>     	
					   	</td>
					</tr>	
					<tr>
						<td>
							<form:label path="users.reset_token">
								<spring:message text="Reset Token"/>
							</form:label>
						</td>
						<td>
							<form:input path="users.reset_token" readonly="true" size="8"  disabled="true" />
							<form:hidden path="users.reset_token" />
						</td> 
					</tr>			
					<tr>
						<td colspan="2">
							<c:if test="${!empty userRoles.users.username}">
								<input type="submit"
									value="<spring:message text="Edit User"/>" />
							</c:if>
							<c:if test="${empty userRoles.users.username}">
								<input type="submit"
									value="<spring:message text="Add User"/>" />
							</c:if>
						</td>
					</tr>
				</table>
			</form:form>		
	</body>
</html>