<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page session="true"%>
<html>
		<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
		<ul id="nav">
			<li><a href="../incidents">Incidents</a></li>
			<li><a href="../notifications">Notifications</a></li>
			<li><a href="../myprofile">My Profile</a></li>
			<li><a href="javascript:formSubmit()">Logout</a></li>
		</ul>
		<!-- For logging out -->
		<c:url value="../j_spring_security_logout" var="logoutUrl" />
		<form action="${logoutUrl}" method='POST' id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>
	<%-- <img src="<c:url value="../resources/images/ITBlogoNight.jpg"/>" class="logo"/> --%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>File Upload Success</title>

</head>

<body>
	
  <div class='center'>
        File  <strong>${fileName}</strong> uploaded successfully.
        <br/><br/>
        </div>
        
</body>
</html>