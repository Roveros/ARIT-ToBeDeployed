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
	
	
	<body>
			<h3>
				Change your password here
			</h3>
			<c:url var="addAction" value="/myprofile" ></c:url>
			
			<form:form action="${addAction}" commandName="password">
			<table id="hor-minimalist-b">
					<tr>
						<td>
							<form:label path="password">
								<spring:message text="Password"/>
							</form:label>
						</td>
						<td>
							<form:input path="password" />
						</td> 
					</tr>	
					<tr>
						<td>
							<form:label path="password_confirmation">
								<spring:message text="Confirm Password"/>
							</form:label>
						</td>
						<td>
							<form:input path="password_confirmation" />
						</td> 
					</tr>	
					<tr>
						<td>
							<form:label path="reset_token">
								<spring:message text="User"/>
							</form:label>
						</td>
						<td>
							<form:input path="reset_token" readonly="true" disabled="true" />
							<form:hidden path="reset_token" />
						</td> 
					</tr>	
					<tr>
						<td colspan="2">						
							
								<input type="submit"
									value="<spring:message text="Submit"/>" />
							
						</td>
					</tr>
				</table>
			</form:form>		
	</body>
</html>
