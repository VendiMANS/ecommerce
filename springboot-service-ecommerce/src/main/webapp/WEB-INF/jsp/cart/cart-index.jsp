<html>
	<head>
		<title>Index Cart</title>
	</head>
	<body>
	
		<h1>Index Cart</h1>
		
		<form action="allSales" method="GET">
            <input type="submit" value="Ver todas las ventas">
        </form>
		
		<form action="saleById" method="GET">
			Ver una venta: <input type="text" name="id" placeholder="ponga un id de venta">
			<input type="submit" value="Buscar">
		</form>
		
		<form action="allSalesByMonth" method="GET">
			Ver ventas de un mes:
			
			<select name="month">
				<option selected>1</option>
				<option>2</option>
				<option>3</option>
				<option>4</option>
				<option>5</option>
				<option>6</option>
				<option>7</option>
				<option>8</option>
				<option>9</option>
				<option>10</option>
				<option>11</option>
				<option>12</option>
			</select>
			
			<input type="submit" value="Buscar">
		</form>
		
		<form action="deleteById" method="GET">
			Borrar una venta: <input type="text" name="id" placeholder="ponga un id de venta">
			<input type="submit" value="Buscar">
		</form>
		
		<form action="deleteAll" method="GET">
            <input type="submit" value="Borrar todas las ventas">
        </form>
	
	
	
	</body>
</html>