<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Sale by Id</title>
	</head>
	<body>
		
		<h1>Sale by Id</h1>

		<table cellspacing="4" cellpadding="3" border="1">
            <thead>
                <tr>
                    <th align="center">Id</th>
                    <th align="center">Product Name</th>
                    <th align="center">Date</th>
					<th align="center">Amount</th>
                </tr>
            </thead>
            <tbody>
				<tr>
					<td>${sale.id}</td>
					<td>${sale.productName}</td>
					<td>${sale.date}</td>
					<td>${sale.amount}</td>
				</tr>
            </tbody>
        </table>
		
	</body>
</html>