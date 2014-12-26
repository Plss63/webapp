<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File server</title>
<script type="text/javascript" src="js/jquery.js">
</script>
</head>
<body>

	
<table border="1">
</table>
 

</body>
<script type="application/javascript">
$(document).ready(function(){
		$.getJSON("outputServlet", function(data){
			$.each(data.records, function(){
				
				$("table").append("<tr><td>" + this['ID'] + "</td><td> localame: " + this['localName'] + "</td></tr>");
				
			});
			//console.log(obj);
		//$("#localName").append(obj.records[].localName);	
		
		});

});
setInterval (function(){}, 5000);
</script>
</html>