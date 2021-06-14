<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Thank you</title>
	</head>
	<body>
		
		<h1>Thank you for your purchase!</h1>
		
		<h2>You purchased:</h2>
		
		<table cellspacing="4" cellpadding="3" border="1">
            <thead>
                <tr>
                    <th align="center">Name</th>
                    <th align="center">Price</th>
					<th align="center">Is on Sale?</th>
					
					<th align="center">Amount</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${items}">
                    <tr>
                        <td>${item.product.name}</td>
						<td>$${item.product.price}</td>
						<td>${item.product.onSale}</td>
						
						<td>${item.amount}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
		
	</body>
</html>