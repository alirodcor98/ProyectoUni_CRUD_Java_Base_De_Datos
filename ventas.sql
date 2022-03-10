-- Creación principal de la base de datos
SET NAMES 'utf8';
DROP DATABASE IF EXISTS ventas;
CREATE DATABASE IF NOT EXISTS ventas DEFAULT CHARACTER SET utf8;
USE ventas;
-- Creando la tabla clientes
CREATE TABLE clientes(
id_clientes					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_clientes				VARCHAR(25) NOT NULL, 
apellido_clientes			VARCHAR(25) NOT NULL
)DEFAULT CHARACTER SET utf8;
-- Insertando registros en la tabla clientes
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente1','Apellido1');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente2','Apellido2');
INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES('Cliente3','Apellido3');

-- Creadno la tabla facturas
CREATE TABLE facturas(
id_facturas					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, 
id_clientes					INTEGER NOT NULL,
referencia_facturas		    VARCHAR(40) NOT NULL,
fecha_facturas				DATE NOT NULL, 
FOREIGN KEY(id_clientes) REFERENCES clientes(id_clientes) ON DELETE CASCADE
)DEFAULT CHARACTER SET utf8;
-- Insertando registros en la tabla facturas
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(1,'FAC1231',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(2,'FAC6645',NOW());
INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) values(3,'FAC0420',NOW());

-- Creando la tabla productos
CREATE TABLE productos(
id_productos					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre_productos				VARCHAR(80) NOT NULL, 
precio_productos				DOUBLE NOT NULL
)DEFAULT CHARACTER SET utf8;
-- Insertando registros en la tabla productos
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto1',10.23);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto2',1.12);
INSERT INTO productos(nombre_productos,precio_productos) VALUES('Producto3',23.30);

-- Creando la tabla facturas_productos
CREATE TABLE facturas_productos(
id_facturas						INTEGER NOT NULL,
id_productos					INTEGER NOT NULL,
cantidad_facturas_productos	DOUBLE NOT NULL,
PRIMARY KEY(id_facturas,id_productos),
FOREIGN KEY(id_facturas)  REFERENCES facturas(id_facturas) ON DELETE CASCADE,
FOREIGN KEY(id_productos) REFERENCES productos(id_productos) ON DELETE CASCADE
)DEFAULT CHARACTER SET utf8;
-- Insertando registros en la tabla facturas_productos
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,1,120);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(1,2,20);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,2,10);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,1,70);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(2,3,7);
INSERT INTO facturas_productos(id_facturas,id_productos,cantidad_facturas_productos) values(3,1,17);

-- Vistas para acceder a los datos de cada tabla de manera más sencilla 
CREATE VIEW todosClientes as select * from clientes; -- Para clientes
CREATE VIEW todosProductos as select * from productos; -- Para productos
CREATE VIEW todasFacturas as select a.id_facturas,a.referencia_facturas,a.fecha_facturas, b.nombre_clientes,b.apellido_clientes,b.id_clientes 
from facturas a,clientes b where a.id_clientes=b.id_clientes ORDER BY a.id_facturas; -- Para facturas

-- Gatilleros para consistencia de datos
DELIMITER $$
CREATE TRIGGER clientes_mayus BEFORE INSERT ON clientes FOR EACH ROW
BEGIN
	SET NEW.nombre_clientes = UPPER(NEW.nombre_clientes);
	SET NEW.apellido_clientes = UPPER(NEW.apellido_clientes);
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER productos_mayus BEFORE INSERT ON productos FOR EACH ROW
BEGIN
	SET NEW.nombre_productos = UPPER(NEW.nombre_productos);  
END $$
DELIMITER ;

-- Procedimientos almacenados para clientes
-- Para listar los registros
DELIMITER $$
CREATE  PROCEDURE todosClientes()
BEGIN
	SELECT * FROM todosClientes;
END $$
DELIMITER ;
-- Para insertar nuevos registros
DELIMITER $$
CREATE  PROCEDURE insertarClientes(IN nombre varchar(25), IN apellido varchar(25))
BEGIN
	INSERT INTO clientes(nombre_clientes,apellido_clientes) VALUES(nombre,apellido);
END $$
DELIMITER ;
-- Para eliminar registros
DELIMITER $$
CREATE  PROCEDURE eliminarClientes(in id int)
BEGIN
	DELETE from clientes WHERE id_clientes=id;
END $$
DELIMITER ;
-- Para modificar registros
DELIMITER $$
CREATE  PROCEDURE modificarClientes(IN id int, IN nombre varchar(25), IN apellido varchar(25))
BEGIN
	UPDATE clientes SET nombre_clientes=nombre,apellido_clientes=apellido WHERE id_clientes=id;
END $$
DELIMITER ;
-- Para buscar registros
DELIMITER $$
CREATE  PROCEDURE buscarClientes(IN patron varchar(15))
BEGIN
	SELECT * FROM clientes WHERE nombre_clientes LIKE CONCAT('%',patron,'%');
END $$
DELIMITER ;

-- Procedimientos almacenados para facturas
-- Para listar los registros
DELIMITER $$
CREATE  PROCEDURE todasFacturas()
BEGIN
	SELECT * FROM todasFacturas;
END $$
DELIMITER ;
-- Para insertar los registros
DELIMITER $$
CREATE  PROCEDURE insertarFacturas(IN idCliente int, IN referencia varchar(40), IN fecha DATE)
BEGIN
	INSERT INTO facturas(id_clientes,referencia_facturas,fecha_facturas) VALUES(idCliente, referencia, fecha);
END $$
DELIMITER ;
-- Para modificar los registros
DELIMITER $$
CREATE  PROCEDURE modificarFacturas(IN id int, IN idCliente int, IN referencia varchar(40), IN fecha DATE)
BEGIN
	UPDATE facturas SET id_clientes=idCliente,referencia_facturas=referencia, fecha_facturas=fecha WHERE id_facturas=id;
END $$
DELIMITER ;
-- Para eliminar registros
DELIMITER $$
CREATE  PROCEDURE eliminarFacturas(in id int)
BEGIN
	DELETE from facturas WHERE id_facturas=id;
END $$
DELIMITER ;
-- Para buscar registros
DELIMITER $$
CREATE  PROCEDURE buscarFacturas()
BEGIN
	SELECT a.id_facturas,a.referencia_facturas,a.fecha_facturas,b.id_clientes,b.nombre_clientes,b.apellido_clientes FROM facturas a,clientes b WHERE a.id_clientes=b.id_clientes;
END $$ 
DELIMITER ;

-- Procedimientos almacenados para productos
-- Para listar los registros
DELIMITER $$
CREATE  PROCEDURE todosProductos()
BEGIN
	SELECT * FROM todosProductos;
END $$
DELIMITER ;
-- Para insertar nuevos registros
DELIMITER $$
CREATE  PROCEDURE insertarProductos(IN nombre varchar(25), IN precio int)
BEGIN
	INSERT INTO productos(nombre_productos,precio_productos) VALUES(nombre,precio);
END $$
DELIMITER ;
-- Para eliminar registros
DELIMITER $$
CREATE  PROCEDURE eliminarProductos(in id int)
BEGIN
	DELETE from productos WHERE id_productos=id;
END $$
DELIMITER ;
-- Para modificar registros
DELIMITER $$
CREATE  PROCEDURE modificarProductos(IN id int, IN nombre varchar(25), IN precio int)
BEGIN
	UPDATE productos SET nombre_productos=nombre,precio_productos=precio WHERE id_productos=id;
END $$
DELIMITER ;
-- Para buscar registros
DELIMITER $$
CREATE  PROCEDURE buscarProductos(IN patron varchar(15))
BEGIN
	SELECT * FROM productos WHERE nombre_productos LIKE CONCAT('%',patron,'%');
END $$
DELIMITER ;