<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>All Users</title>
	</head>
	<body>
		
		<h1>All Users</h1>

		<table cellspacing="4" cellpadding="3" border="1">
            <thead>
                <tr>
                    <th align="center">Id</th>
                    <th align="center">Username</th>
                    <th align="center">Password</th>
					<th align="center">Permisos</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
						<td>${user.password}</td>
                        <td>${user.permisos}</td>
						
                    </tr>
                </c:forEach>
            </tbody>
        </table>
	</body>
</html>