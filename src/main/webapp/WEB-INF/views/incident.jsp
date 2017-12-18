<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
	<ul id="nav">
		<li><a href="../incidents">Incidents</a></li>
		<li><a href="../notifications">Notifications ${notifications}</a></li>
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
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Incident ${id}</title>
	</head>
	<body>
		<sec:authorize access="hasRole('ROLE_USER')">
		<h3>Incident Details</h3>
		<h4>${procedure}</h4>
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
					         <%-- <form:option value="NONE" label="Select"/> --%>
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
						<td>
							<form:label path="lecturer">
								<spring:message text="Lecturer"/>
							</form:label>
						</td>
						<td>
							<form:input path="lecturer" readonly="true" disabled="true"/>	
					    	<form:hidden path="lecturer" />					    			
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
			         	<td><form:label path="summary">Incident Summary</form:label></td>
			         	<td><form:textarea path="summary" rows="5" cols="30" /></td>				     
					</tr>					
					<tr>
					   <td>
					   		<form:label path="first_occurrence">First Occurrence</form:label>
					   </td>
					   <td>
					      <form:select path="first_occurrence">
					          <form:option value="n/a" label="n/a" disabled="true"/>
					         <form:options items="${choiceList}" disabled="true"/>
					      </form:select>     	
					   </td>
					</tr>	
					<c:choose>
		  				<c:when test="${incident.stage.equals('2a') || incident.stage.equals('2b') || incident.stage.equals('2c') || incident.stage.equals('2d')}">
							<tr>
							   <td><form:label path="plagiarism_confirmed">Plagiarism Confirmed</form:label></td>
							   <td>
							      <form:select path="plagiarism_confirmed">
							          <form:option value="n/a" label="n/a" />
							         <form:options items="${choiceList}" />
							      </form:select>     	
							   </td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
							   <td><form:label path="plagiarism_confirmed">Plagiarism Confirmed</form:label></td>
							   <td>
							      <form:select path="plagiarism_confirmed">
							          <form:option value="n/a" label="n/a" disabled="true"/>
							         <form:options items="${choiceList}" disabled="true"/>
							      </form:select>     	
							   </td>
							</tr>					
						</c:otherwise>
					</c:choose>
					<tr>
						<td>
							<form:label path="plagiarism_level">
								<spring:message text="Plagiarism Level"/>
							</form:label>
						</td>
					   <td>
					      <form:select path="plagiarism_level">
					         <%-- <form:option value="NONE" label="Select"/> --%>
					         <form:options items="${numberList}" />
					      </form:select>     	
					   </td>
					</tr>
			    	<tr>
			         	<td><form:label path="sanction">Incident Sanction</form:label></td>
			         	<td><form:textarea path="sanction" rows="5" cols="30" /></td>				     
					</tr>
					<tr>
					   <td><form:label path="meeting_attended">Meeting Attended</form:label></td>
					   <td>
					      <form:select path="meeting_attended">
					          <form:option value="n/a" label="n/a"/>
					         <form:options items="${choiceList}" />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
					   <td><form:label path="sanction_accepted">Sanction Accepted</form:label></td>
					   <td>
					      <form:select path="sanction_accepted">
					          <form:option value="n/a" label="n/a"/>
					         <form:options items="${choiceList}" />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
					   <td><form:label path="uncooperative">Student Uncooperative</form:label></td>
					   <td>
					      <form:select path="uncooperative">
					          <form:option value="n/a" label="n/a"/>
					         <form:options items="${choiceList}" />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
						<td>
							<form:label path="status">
								<spring:message text="Status"/>
							</form:label>
						</td>
					   <td>
					      <form:select path="status">
					      <%--    <form:option value="NONE" label="Select"/> --%>
					         <form:options items="${statusList}" />
					      </form:select>     	
					   </td>
					</tr>			
					<tr>
						<td colspan="2">
							<c:if test="${!empty incident.student_name}">
								<input type="submit"
									value="<spring:message text="Update Incident"/>" />
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
		<h3>Incident Details</h3>
		<h4>${procedure}</h4>
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
							<form:input path="id" readonly="true" size="8"  disabled="true"/>	
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
							<form:input path="student_name" readonly="true" disabled="true"/>	
					    	<form:hidden path="student_name" />					    			
						</td> 
					</tr>
					<tr>
						<td>
							<form:label path="student_number">
								<spring:message text="Student Number"/>
							</form:label>
						</td>
						<td>
							<form:input path="student_number" readonly="true" disabled="true"/>	
					    	<form:hidden path="student_number" />					    			
						</td>
					</tr>
					<tr>
						<td>
							<form:label path="course_description">
								<spring:message text="Course Description"/>
							</form:label>
						</td>
						<td>
							<form:input path="course_description" readonly="true" disabled="true"/>	
					    	<form:hidden path="course_description" />					    			
						</td> 
					</tr>
					<tr>
					   <td><form:label path="year">Year</form:label></td>
					   <td>
					      <form:select path="year">
					         <%-- <form:option value="NONE" label="Select"/> --%>
					         <form:options items="${yearList}" disabled="true" />
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
							<form:input path="module_code" readonly="true"  disabled="true"/>	
					    	<form:hidden path="module_code" />					    			
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
					         <form:options items="${departmentList}" disabled="true" />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
						<td>
							<form:label path="lecturer">
								<spring:message text="Lecturer"/>
							</form:label>
						</td>
						<td>
							<form:input path="lecturer" readonly="true" disabled="true"/>	
					    	<form:hidden path="lecturer" />					    			
						</td> 
					</tr>
					<tr>
					   <td><form:label path="plagiarism">Plagiarism Suspected</form:label></td>
					   <td>
					      <form:select path="plagiarism">
					          <form:option value="Yes" label="Yes" disabled="true"/>
					         <form:options items="${choiceList}" disabled="true"/>
					      </form:select>     	
					   </td>
					</tr>
					<tr>
				    	<tr>
				         	<td><form:label path="summary">Incident Summary</form:label></td>
				         	<td><form:textarea path="summary" rows="5" cols="30" readonly="true" disabled="true" /></td>				     
						</tr>					
					<tr>
					<tr>
					   <td><form:label path="first_occurrence">First Occurrence</form:label></td>
					   <td>
					      <form:select path="first_occurrence">
					          <form:option value="n/a" label="n/a"/>
					         <form:options items="${choiceList}" />
					      </form:select>     	
					   </td>
					</tr>	
					<tr>
					   <td><form:label path="plagiarism_confirmed">Plagiarism Confirmed</form:label></td>
					   <td>
					      <form:select path="plagiarism_confirmed">
					          <form:option value="n/a" label="n/a"/>
					         <form:options items="${choiceList}" />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
						<td>
							<form:label path="plagiarism_level">
								<spring:message text="Plagiarism Level"/>
							</form:label>
						</td>
					   <td>
					      <form:select path="plagiarism_level">
					         <%-- <form:option value="NONE" label="Select"/> --%>
					         <form:options items="${numberList}" readonly="true" disabled="true"/>
					      </form:select>     	
					   </td>
					</tr>
					<c:choose>
		  			<c:when test="${incident.stage.equals('3e') || incident.stage.equals('3f')}">
				    <tr>
			         	<td><form:label path="sanction">Incident Sanction</form:label></td>
			         	<td><form:textarea path="sanction" rows="5" cols="30" /></td>				     
					</tr>
		  			</c:when>
		  			<c:otherwise>
			    	<tr>
			         	<td><form:label path="sanction">Incident Sanction</form:label></td>
			         	<td><form:textarea path="sanction" rows="5" cols="30" readonly="true" disabled="true" /></td>				     
					</tr>
		  			</c:otherwise>
		  			</c:choose>	
					<tr>
					   <td><form:label path="meeting_attended">Meeting Attended</form:label></td>
					   <td>
					      <form:select path="meeting_attended">
					          <form:option value="n/a" label="n/a" disabled="true" />
					         <form:options items="${choiceList}" disabled="true" />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
					   <td><form:label path="sanction_accepted">Sanction Accepted</form:label></td>
					   <td>
					      <form:select path="sanction_accepted">
					          <form:option value="n/a" label="n/a" disabled="true" />
					         <form:options items="${choiceList}" disabled="true"  />
					      </form:select>     	
					   </td>
					</tr>
					<tr>
					   <td><form:label path="uncooperative">Student Uncooperative</form:label></td>
					   <td>
					      <form:select path="uncooperative">
					          <form:option value="n/a" label="n/a" disabled="true" />
					         <form:options items="${choiceList}" disabled="true" />
					      </form:select>     	
					   </td>
					</tr>
					<c:choose>
		  			<c:when test="${incident.stage.equals('3e') || incident.stage.equals('3f')}">
					<tr>
						<td>
							<form:label path="status">
								<spring:message text="Status"/>
							</form:label>
						</td>
					   <td>
					      <form:select path="status">
					      <%--    <form:option value="NONE" label="Select"/> --%>
					         <form:options items="${statusList}" />
					      </form:select>     	
					   </td>
					</tr>
		  			</c:when>
		  			<c:otherwise>
		  			<tr>
						<td>
							<form:label path="status">
								<spring:message text="Status"/>
							</form:label>
						</td>
					   <td>
					      <form:select path="status">
					      <%--    <form:option value="NONE" label="Select"/> --%>
					         <form:options items="${statusList}" disabled="true"  />
					      </form:select>     	
					   </td>
					</tr>
		  			</c:otherwise>
		  			</c:choose>		
					<tr>
						<td colspan="2">
							<c:if test="${!empty incident.student_name}">
								<input type="submit"
									value="<spring:message text="Update Incident"/>" />
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
		
		<sec:authorize access="hasRole('ROLE_USER')">
			<h3>Material Upload </h3>
		<c:choose>
		  <c:when test="${incident.stage.equals('2a') || incident.stage.equals('2b') || incident.stage.equals('2c') || incident.stage.equals('2d')}">
	  		<form:form action="../upload/${id}?${_csrf.parameterName}=${_csrf.token}" method="POST" modelAttribute="fileBucket" enctype="multipart/form-data">
				<table id="hor-minimalist-b">
					<thead>Please upload *.zip, *.rar or *.7z compressed files ONLY. - 2!!!</thead>
					<tr>
						<td>
							<form:label path="altEmail">
								<spring:message text="Email Address"/>
							</form:label>
						</td>
						<td>
							<form:input path="altEmail"/>	
					    	<form:hidden path="altEmail" />					    			
						</td> 
					</tr>
		    		<tr>					   
		    			<td>
		    				<label for="file">Upload a file</label>
							<form:input type="file" path="file" id="file"/>
							<form:errors path="file"/>
						</td>
						<td>
							<input type="submit" value="Upload">
						</td>
					</tr>
				</table>
			</form:form>
		  </c:when>
		  <c:otherwise>
	  		<form:form action="../upload/${id}?${_csrf.parameterName}=${_csrf.token}" method="POST" modelAttribute="fileBucket" enctype="multipart/form-data">
				<table id="hor-minimalist-b">
					<thead>Please upload *.zip, *.rar or *.7z compressed files ONLY. Alternate email address available for use at level 2 policy level</thead>
					<tr>
						<td>
							<form:label path="altEmail">
								<spring:message text="Alternate Tutor Email Address"/>
							</form:label>
						</td>
						<td>
							<form:input path="altEmail" readonly="true" disabled="true"/>	
					    	<form:hidden path="altEmail" />					    			
						</td> 
					</tr>
		    		<tr>					   
		    			<td>
		    				<label for="file">Upload a file</label>
							<form:input type="file" path="file" id="file"/>
							<form:errors path="file"/>
						</td>
						<td>
							<input type="submit" value="Upload">
						</td>
					</tr>
				</table>
			</form:form>
		  </c:otherwise>
		</c:choose>			
		</sec:authorize>
		
		<sec:authorize access="!hasRole('ROLE_USER')">
		<c:choose>
		  <c:when test="${!incident.stage.equals('1a') && !incident.stage.equals('2a') && !incident.stage.equals('2b') && !incident.stage.equals('2c') && !incident.stage.equals('2d')}">
		  		<br>
				<table id="hor-minimalist-b">
					<thead>Click below to download Incident Material.</thead>
					<tr>					   
						<td>Incident #${id} Material</td>
						<td><a href="<c:url value='/download/${incident.department}/${id}' />">Download</a></td>
					</tr>
				</table>
	  		</c:when>
	  		<c:when test="${incident.stage.equals('2b') || incident.stage.equals('2c') || incident.stage.equals('2d')}">
		  		<br>
				<table id="hor-minimalist-b">
					<thead>Click below to download Incident Material.</thead>
					<tr>					   
						<td>Incident #${id} Material</td>
						<td><a href="<c:url value='/download/External/${id}' />">Download</a></td>
					</tr>
				</table>
	  		</c:when>
	  		<c:otherwise>
	  		</c:otherwise>
	  		</c:choose>
		</sec:authorize>
	</body>
</html>