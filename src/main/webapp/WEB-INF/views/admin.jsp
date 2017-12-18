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
	<img src="<c:url value="resources/images/ITBlogoNight.jpg"/>" class="logo"/>
	
	
	<body>
		<!-- attribute from login method -->
		<h3>Hello ${userName}</h3>
		<c:if test="${!empty listUserRoles}">
<!-- 		users : username password enabled 
		user-roles: user_role_id username role -->
<!-- 		<tr>
			<td >ID</td>
			<td >Username</td>
			<td >Password</td>
			<td >Role</td>	
			<td >Enabled</td>
			<tr> -->
			<table id="hor-minimalist-a">
			<th >ID</th>
			<th >Username</th>
			<th >Password</th>
			<th >Role</th>	
			<th >Enabled</th>
			<c:forEach items="${listUserRoles}" var="userRoles" >
					<tr>
						<td>${userRoles.user_role_id}</td>
						<td>${userRoles.users.username}</td>
						<td>${userRoles.users.password}</td>
						<%-- <td>${userRoles.role}</td> --%>
						<c:choose>
							<c:when test="${userRoles.role == 'ROLE_ADMIN'}">
								<td>Administrator</td>
							</c:when>
							<c:when test="${userRoles.role == 'ROLE_HOD_DPT1'}">
								<td>Department 1</td>
							</c:when>
							<c:when test="${userRoles.role == 'ROLE_HOD_DPT2'}">
								<td>Department 2</td>
							</c:when>
							<c:when test="${userRoles.role == 'ROLE_HOD_DPT3'}">
								<td>Department 3</td>
							</c:when>
							<c:when test="${userRoles.role == 'ROLE_HOD_DPT4'}">
								<td>Department 4</td>
							</c:when>
							<c:when test="${userRoles.role == 'ROLE_USER'}">
								<td>User</td>
							</c:when>
							<c:otherwise>
								<td>${userRoles.role}</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${userRoles.users.enabled == '1'}">
								<td>Yes</td>
							</c:when>
							<c:when test="${userRoles.users.enabled == '0'}">
								<td>No</td>
							</c:when>
							<c:otherwise>
								<td>${userRoles.users.enabled}</td>
							</c:otherwise>
						</c:choose>
						<td><a href="<c:url value='/user/${userRoles.user_role_id}' />" >View</a></td>
						<td><a href="<c:url value='/removeUser/${userRoles.user_role_id}' />" >Delete</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
			<h3>
				Add a User
			</h3>
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
								<spring:message text="Username"/>
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
						<td>
							<form:label path="role">
								<spring:message text="Role"/>
							</form:label>
						</td>
					   <td>
					      <form:select path="role">
					      <%--    <form:option value="NONE" label="Select"/> --%>
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
