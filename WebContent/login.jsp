<%@ page import="com.Login" %>
<%@ page import="com.Login" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login - GadgetBadget Company</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/login.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Login</h1>
				<form id="formLogin">
					User name:
					<input id="txtUsername" name="txtUsername" type="text" class="form-control form-control-sm">
					Password:
					<input id="txtPassword" name="txtPassword" type="password" class="form-control form-control-sm">
					<br>
					<input id="btnLogin" name="btnLogin" type="button" value="Login" class="btn btn-primary">
					<br><br>
					<div id="alertError" class="alert alert-danger"></div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>