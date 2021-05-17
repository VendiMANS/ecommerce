<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>View Sales</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Product Name</th>
                    <th>Date</th>
					<th>Amount</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sales}" var="sale">
                    <tr>
                        <td>${sale.id}</td>
                        <td>${sale.productName}</td>
                        <td>${sale.date}</td>
						<td>${sale.amount}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>