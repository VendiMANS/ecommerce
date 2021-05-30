<html>
	<head>
		<title>Index Products</title>
	</head>
	<body>
	
		<h1>Index Products</h1>
		
		<form action="all" method="GET">
            <input type="submit" value="Ver todos los productos">
        </form>
		
		<form action="onSale" method="GET">
            <input type="submit" value="Ver todos los productos en oferta">
        </form>
		
		<form action="byId" method="GET">
			Ver un prod: <input type="text" name="id" placeholder="ponga un id de producto">
			<input type="submit" value="Buscar">
		</form>
		
		
		<form action="save" method="POST">			
			<fieldset>
				<legend>Crear Producto</legend>
				Nombre: <input type="text" name="name">
				Precio: <input type="text" name="price">
				Stock inicial: <input type="text" name="stock">
				En oferta? <input type="text" name="onSale">
				<input type="submit" value="Crear">
			</fieldset>
		</form>
		
		<form action="edit/price" method="POST">			
			<fieldset>
				<legend>Editar Precio Producto</legend>
				Id: <input type="text" name="id">
				Precio: <input type="text" name="price">
				<input type="submit" value="Editar">
			</fieldset>
		</form>
		
		<form action="edit/onSale/put" method="POST">			
			<fieldset>
				<legend>Poner Producto en oferta</legend>
				Id: <input type="text" name="id">
				<input type="submit" value="Editar">
			</fieldset>
		</form>
		
		<form action="edit/onSale/remove" method="POST">			
			<fieldset>
				<legend>Sacar Producto en oferta</legend>
				Id: <input type="text" name="id">
				<input type="submit" value="Editar">
			</fieldset>
		</form>
		
		<form action="edit/stock/add" method="POST">			
			<fieldset>
				<legend>Restockear Producto</legend>
				Id: <input type="text" name="id">
				Stock: <input type="text" name="stock">
				<input type="submit" value="Editar">
			</fieldset>
		</form>
		
		
		
		
		
		<form action="delete/byId" method="GET">
			Borrar un producto: <input type="text" name="id" placeholder="ponga un id de producto">
			<input type="submit" value="Borrar">
		</form>
		
		<form action="delete/all" method="GET">
            <input type="submit" value="Borrar todos los productos">
        </form>
		
		<form action="resetIdCounter" method="GET">
            <input type="submit" value="Reiniciar contador id">
        </form>
	
	
	
	</body>
</html>