<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>All Products</title>
	</head>
	<body>
		
		<h1>All Products</h1>

		<table cellspacing="4" cellpadding="3" border="1">
            <thead>
                <tr>
                    <th align="center">Id</th>
                    <th align="center">Name</th>
                    <th align="center">Price</th>
					<th align="center">Stock</th>
					<th align="center">Is on Sale?</th>
					<th align="center">Add to cart</th>
					<th align="center">Remove from cart</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="prod" items="${prods}">
                    <tr>
                        <td>${prod.id}</td>
                        <td>${prod.name}</td>
						<td>$${prod.price}</td>
                        <td>${prod.stock}</td>
						<td>${prod.onSale}</td>
						
						<td>
							<form action="cart/add" method="POST">
								<input type="hidden" name="id" value="${prod.id}">
								<input type="hidden" name="name" value="${prod.name}">
								<input type="text" name="amount">
								<input type="submit" value="Agregar al carrito">
							</form>
						</td>
						
						<td>
							<form action="cart/remove" method="POST">
								<input type="hidden" name="id" value="${prod.id}">
								<input type="hidden" name="name" value="${prod.name}">
								<input type="text" name="amount">
								<input type="submit" value="Sacar del carrito">
							</form>
						</td>
						
						
                    </tr>
                </c:forEach>
            </tbody>
        </table>
	</body>
</html>