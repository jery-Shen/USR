<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
if(session.getAttribute("user") == null){
	response.sendRedirect("/USR/login.jsp");
}else{
	response.sendRedirect("/USR/userManage.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>USR</title>

</head>
<body>

</body>
</html>