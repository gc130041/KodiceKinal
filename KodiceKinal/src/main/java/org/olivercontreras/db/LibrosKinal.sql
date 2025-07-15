drop database if exists DBlibroskinal;
create database DBlibroskinal;

use DBlibroskinal;

-- Tabla Clientes
create table Clientes(
	idCliente int auto_increment,
    nombre varchar(50) not null,
    apellido varchar(50) not null,
    telefono varchar(20) not null,
    direccion varchar(225) not null,
    email varchar(225) not null,
    fechaIngreso date not null,
    constraint pk_idCliente primary key (idCliente)
);

-- Tabla Productos
create table Productos(
	idLibro int auto_increment,
    titulo varchar(50) not null,
    autor varchar(50) not null,
    fechaPublicacion date not null,
    precio double not null,
    disponibilidad enum('disponible', 'agotado') not null,
    idCliente int not null,
    constraint pk_idLibro primary key (idLibro),
    constraint fk_idCliente foreign key (idCliente)
		references Clientes(idCliente)
);

-- Agregar Cliente
delimiter //
create procedure sp_agregarCliente(
	in p_nombre varchar(50),
    in p_apellido varchar(50),
    in p_telefono varchar(20),
    in p_direccion varchar(225),
    in p_email varchar(225),
    in p_fechaIngreso date)
begin
    insert into Clientes(nombre, apellido, telefono, direccion, email, fechaIngreso)
		values(p_nombre, p_apellido, p_telefono, p_direccion, p_email, p_fechaIngreso);
end;//
delimiter ; 

-- Listar Cliente
delimiter //
create procedure sp_listarCliente()
begin
    select
        C.idCliente as id,
        C.nombre as nombre,
        C.apellido as apellido,
        C.telefono as telefono,
        C.direccion as direccion,
        C.email as email,
        C.fechaIngreso as fechaIngreso
    from Clientes C;
end;//
delimiter ;

-- Buscar Cliente
delimiter //
create procedure sp_buscarCliente(in p_id int)
begin
	select * from Clientes where idCliente = p_id;
end;//
delimiter ;

-- Editar Cliente
delimiter //
create procedure sp_editarCliente(
    in p_id int,
    in p_nombre varchar(50),
    in p_apellido varchar(50),
    in p_telefono varchar(20),
    in p_direccion varchar(255),
    in p_email varchar(255),
    in p_fechaIngreso date
)
begin
    update Clientes
    set idCliente = p_id,
		nombre = p_nombre,
        apellido = p_apellido,
        telefono = p_telefono,
        direccion = p_direccion,
        email = p_email,
        fechaIngreso = p_fechaIngreso
    where idCliente = p_id;
end;//
delimiter ;

-- Eliminar Cliente
delimiter //
create procedure sp_eliminarCliente(in p_id int)
begin
    delete from Clientes where idCliente = p_id;
end;//
delimiter ;

-- Agregar Producto
delimiter //
create procedure sp_agregarProducto(
	in p_titulo varchar(50),
    in p_autor varchar(50),
    in p_fechaPublicacion date,
    in p_precio double,
    in p_disponibilidad enum('disponible', 'agotado'),
    in p_idCliente int
)
begin
	insert into Productos(titulo, autor, fechaPublicacion, precio, disponibilidad, idCliente)
    values(p_titulo, p_autor, p_fechaPublicacion, p_precio, p_disponibilidad, p_idCliente);
end;//
delimiter ;

-- Listar Producto
delimiter //
create procedure sp_listarProducto()
begin
	select
		P.idLibro as id,
        P.titulo as titulo,
        P.autor as autor,
        P.fechaPublicacion as fechaPublicacion,
        P.precio as precio,
        P.disponibilidad as disponibilidad,
        P.idCliente as idCliente
    from Productos P;
end;//
delimiter ; 

-- Buscar Producto
delimiter //
create procedure sp_buscarProducto(in p_id int)
begin
	select * from Productos where idLibro = p_id;
end;//
delimiter ;

-- Editar Producto
delimiter //
create procedure sp_editarProducto(
    in p_id int,
    in p_titulo varchar(50),
    in p_autor varchar(50),
    in p_fechaPublicacion date,
    in p_precio double,
    in p_disponibilidad enum('disponible', 'agotado'),
    in p_idCliente int
)
begin
    update Productos
    set titulo = p_titulo,
        autor = p_autor,
        fechaPublicacion = p_fechaPublicacion,
        precio = p_precio,
        disponibilidad = p_disponibilidad,
        idCliente = p_idCliente
    where idLibro = p_id;
end;//
delimiter ;

-- Eliminar Producto
delimiter //
create procedure sp_eliminarProducto(in p_id int)
begin
    delete from Productos where idLibro = p_id;
end;//
delimiter ;

-- call clientes --
call sp_agregarCliente('Juan', 'Perez', '555-1234', 'Calle Falsa 123, Ciudad de México', 'juan.perez@example.com', '2023-01-15');
call sp_agregarCliente('Maria', 'Gomez', '555-5678', 'Avenida Siempre Viva 456, Guadalajara', 'maria.gomez@example.com', '2022-11-20');
call sp_agregarCliente('Carlos', 'Rodriguez', '555-9012', 'Bulevar de los Sueños 789, Monterrey', 'carlos.rodriguez@example.com', '2024-03-01');
call sp_agregarCliente('Ana', 'Martinez', '555-3456', 'Plaza Central 101, Puebla', 'ana.martinez@example.com', '2023-07-05');
call sp_agregarCliente('Luis', 'Hernandez', '555-7890', 'Camino Real 202, Tijuana', 'luis.hernandez@example.com', '2022-09-10');
call sp_agregarCliente('Sofia', 'Diaz', '555-2345', 'Ruta del Sol 303, Cancún', 'sofia.diaz@example.com', '2024-01-25');
call sp_agregarCliente('Pedro', 'Sanchez', '555-6789', 'Callejón Secreto 404, Mérida', 'pedro.sanchez@example.com', '2023-04-18');
call sp_agregarCliente('Laura', 'Ramirez', '555-0123', 'Paseo de la Reforma 505, CDMX', 'laura.ramirez@example.com', '2022-12-01');
call sp_agregarCliente('Diego', 'Torres', '555-4567', 'Avenida Insurgentes 606, Querétaro', 'diego.torres@example.com', '2024-02-14');
call sp_agregarCliente('Valeria', 'Flores', '555-8901', 'Calle del Árbol 707, Oaxaca', 'valeria.flores@example.com', '2023-06-30');
call sp_agregarCliente('Jorge', 'Morales', '555-3210', 'Boulevard Costero 808, Veracruz', 'jorge.morales@example.com', '2022-10-08');
call sp_agregarCliente('Fernanda', 'Ruiz', '555-7654', 'Camino Antiguo 909, San Luis Potosí', 'fernanda.ruiz@example.com', '2024-04-22');
call sp_agregarCliente('Ricardo', 'Garcia', '555-1098', 'Calle Nueva 111, Aguascalientes', 'ricardo.garcia@example.com', '2023-03-12');
call sp_agregarCliente('Gabriela', 'Ortiz', '555-5432', 'Avenida Principal 222, León', 'gabriela.ortiz@example.com', '2022-08-28');
call sp_agregarCliente('Hector', 'Castillo', '555-9876', 'Privada de las Flores 333, Toluca', 'hector.castillo@example.com', '2024-05-01');
call sp_listarCliente();
call sp_editarCliente(5, 'Luis', 'Hernandez', '555-7890', 'Camino Real 202, Tijuana', 'luis.hernandez@example.com', '2022-09-10');
call sp_eliminarCliente(15);

-- call productos
call sp_agregarProducto('Cien años de soledad', 'Gabriel García Márquez', '1967-05-30', 15.99, 'disponible', 1);
call sp_agregarProducto('Don Quijote de la Mancha', 'Miguel de Cervantes', '1605-01-16', 22.50, 'disponible', 2);
call sp_agregarProducto('1984', 'George Orwell', '1949-06-08', 12.00, 'agotado', 3);
call sp_agregarProducto('Orgullo y prejuicio', 'Jane Austen', '1813-01-28', 10.75, 'disponible', 4);
call sp_agregarProducto('El principito', 'Antoine de Saint-Exupéry', '1943-04-06', 8.99, 'disponible', 5);
call sp_agregarProducto('Crimen y castigo', 'Fiódor Dostoievski', '1866-12-01', 18.25, 'disponible', 6);
call sp_agregarProducto('El Gran Gatsby', 'F. Scott Fitzgerald', '1925-04-10', 14.50, 'agotado', 7);
call sp_agregarProducto('Matar a un ruiseñor', 'Harper Lee', '1960-07-11', 11.99, 'disponible', 8);
call sp_agregarProducto('Ulises', 'James Joyce', '1922-02-02', 25.00, 'disponible', 9);
call sp_agregarProducto('En el camino', 'Jack Kerouac', '1957-09-05', 13.00, 'disponible', 10);
call sp_agregarProducto('Moby Dick', 'Herman Melville', '1851-10-18', 19.75, 'agotado', 11);
call sp_agregarProducto('Guerra y paz', 'León Tolstói', '1869-01-01', 28.00, 'disponible', 12);
call sp_agregarProducto('El señor de los anillos', 'J.R.R. Tolkien', '1954-07-29', 20.99, 'disponible', 13);
call sp_agregarProducto('Las aventuras de Huckleberry Finn', 'Mark Twain', '1884-12-10', 9.50, 'disponible', 14);
call sp_listarProducto();
call sp_editarProducto(5, 'El principito', 'Antoine de Saint-Exupéry', '1943-04-06', 8.99, 'disponible', 5);
call sp_eliminarProducto(14);