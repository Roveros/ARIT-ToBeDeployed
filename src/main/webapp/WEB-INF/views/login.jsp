<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<ul id="nav">
	<li><a href="">Assessment Related Incident Tool - ARIT</a></li>
</ul>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login Page</title>
	</head>
	<img src="<c:url value="resources/images/ITBlogoNight.jpg"/>" class="logo"/>
	
	<body>
	<div class = "center">
		<c:if test="${not empty error}"><div>${error}</div></c:if>
		<c:if test="${not empty message}"><div>${message}</div></c:if>
	</div>
		
	<!-- form for logging in. "home" is the action, POST's on submission -->
	<form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST'>
		<table style="margin: auto;">
			<tr>
				<td>Username:</td>
				<td><input type='text' name='username' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
			</tr>			
		</table>
			<div class = "center">
				<input name="submit" type="submit" value="submit" />
			</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
		<div class = recovery> Forgotten Password? <a href="recovery">Click Here</a></div>
	</body>
</html>