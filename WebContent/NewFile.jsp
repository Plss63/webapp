<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File server</title>
<script type="text/javascript" src="js/jquery.js">
</script>
</head>
<body>
	<form action="fileServlet" enctype="multipart/form-data" method="post">
		File: <input type="file" style="width: 100%" name="sendfile"><br>
		<input type=submit value="upload">

	</form>
	Count of pages:
	<div id="num">
	</div>
	Show by:
	<div id="show">
	</div>
	
<table border="1"></table>
<div id="numpage">
</div>

</body>
<script type="application/javascript">
//по умолчанию страница 1
var pageone = 1;
//вывод страниц по
var showpage = 5;
$("#show").append(showpage);
//запрос при открытии
$(document).ready(
		requestpage(pageone)
		);
//добавление ссылки
function addlink(d){
	var node = document.getElementById('numpage');
	var e = document.createElement('a');
	e.href = "${pageContext.request.contextPath}/?page=" + d;
	e.title = 'page ' + d;
		e.appendChild(document.createTextNode(d));
		node.appendChild(e);
		$(e).click(function(t){
			if(t)t.preventDefault();
			requestpage(d);
		});
	
}
//вывод ссылок
function returnlink(d){
	$("#numpage").empty();
	for(var i=1; i<=d;i++){
		$("#numpage").append(addlink(i));
		$("#numpage").append("  ");
		}	
}
//запрос серверу pageone - страница
function requestpage(pageone){
	$.get("${pageContext.request.contextPath}/outputServlet",{page:pageone}, responsepage);
}
//ответ от сервера
function responsepage(data){
	$("table").empty();
		$.each(data.records, function(){	
			
			$("table").append("<tr><td>" + this['ID'] + "</td><td> localame: " + this['localName'] + "</td></tr>");
			
		});
		var d = (data.page);
		$("#num").empty();
		$("#num").append(d);
		returnlink(d)
	
}

</script>

</html>