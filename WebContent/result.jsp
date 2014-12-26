<%@page import="usemysql.MySQLConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>get_page</title>
<script type="text/javascript" src="js/jquery.js">
	
</script>
</head>
<body>
<c:url value="/mypage.jsp" var="completeURL">
 <c:param name="Id" value="736"/>
 <c:param name="user" value="chaitanya"/>
</c:url>
${completeURL}

</body>
<script type="application/javascript">
</script>
</html>