<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Error</title>
	</head>
	<body>
		
		<h1>Error</h1>

		<% String monthStr = null; %>
		<% String month = request.getParameter("month"); %>

		<%
			switch(month){
				case "1":
					monthStr = "January";
					break;
				case "2":
					monthStr = "February";
					break;
				case "3":
					monthStr = "March";
					break;
				case "4":
					monthStr = "April";
					break;
				case "5":
					monthStr = "May";
					break;
				case "6":
					monthStr = "June";
					break;
				case "7":
					monthStr = "July";
					break;
				case "8":
					monthStr = "August";
					break;
				case "9":
					monthStr = "September";
					break;
				case "10":
					monthStr = "October";
					break;
				case "11":
					monthStr = "November";
					break;
				case "12":
					monthStr = "December";
					break;
			}
		%>

		<p>
			There are no sales registered for <%= monthStr%>.
		</p>
		
	</body>
</html>