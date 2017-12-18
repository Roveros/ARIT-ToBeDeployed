<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login Page</title>
	</head>
	
	<body>
	<!-- form for logging in. "home" is the action, POST's on submission -->
		<form action="home" method="post">
		<!-- the variable name is the same as User class variable name -->
			<input type="text" name="userName"><br>
			<input type="submit" value="Login">
		</form>
	</body>
</html>