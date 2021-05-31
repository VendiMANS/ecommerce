<html>
	<head>
		<title>Index Users</title>
	</head>
	<body>
	
		<h1>Index Users</h1>
		
		<h2>Usuario actual: ${username}</h2>
		
		<form action="register" method="POST">			
			<fieldset>
				<legend>Register</legend>
				Usuario: <input type="text" name="username">
				Pass: <input type="password" name="password">
				<input type="submit" value="Registrar">
			</fieldset>
		</form>
		
		<form action="login" method="POST">			
			<fieldset>
				<legend>Log in</legend>
				Usuario: <input type="text" name="username">
				Pass: <input type="password" name="password">
				<input type="submit" value="Entrar">
			</fieldset>
		</form>
		
		<form action="logout" method="POST">
            <input type="submit" value="Log out">
        </form>
		
		<form action="all" method="GET">
            <input type="submit" value="Ver todos los usuarios">
        </form>
		
		
		
		
		
		<a href="http://localhost:8090/api/view/cart/index">Carrito</a>
	
	
	
	</body>
</html>