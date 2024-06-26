drop database if exists superKinalIN5CV;

create database if not exists superkinalIN5CV;
 
use superkinalIN5CV;
 
set global time_zone = '-6:00';
 
create table Clientes(
	clienteId int not null auto_increment,
    nombre varchar (40) not null,
    apellido varchar (40) not null,
    telefono varchar (15) not null,
    direccion varchar (150) not null,
    nit varchar (15)not null,
    primary key PK_clienteId(clienteId)
);
 
create table Cargos(
	cargoId int not null auto_increment,
    nombreCargo varchar(30) not null,
    descripcionCargo varchar(100) not null,
    primary key PK_cargoId (cargoId)
);
 
 
create table Empleados(
	empleadoId int not null auto_increment,
    nombreEmpleado varchar(30) not null,
    apellidoEmpleado varchar(30) not null,
    sueldo decimal(10, 2) not null,
    horaEntrada time not null,
    horaSalida time not null,
    cargoId int not null,
    encargadoId int,
    primary key PK_empleadoId (empleadoId),
    Constraint FK_Empleados_Cargos foreign key (cargoId)
		references Cargos (cargoId),
	constraint FK_Empleados_Encargados foreign key (encargadoId)
		references Empleados (empleadoId)
);
 
 
create table Distribuidores(
	distribuidorId int not null auto_increment,
    nombreDistribuidor varchar(30) not null,
    direccionDistribuidor varchar(200) not null,
    nitDistribuidor varchar(15) not null,
    telefonoDistribuidor varchar(15) not null,
    web varchar(50),
    primary key PK_distribuidorid (distribuidorid)
);
 
create table categoriaProductos(
	categoriaProductosId int not null auto_increment,
    nombreCategoria varchar(30) not null,
    descripcionCategoria varchar(100) not null,
    primary key PK_categoriaProductosId (categoriaProductosId)
);
 
create table Productos(
	productoId int not null auto_increment,
    nombreProducto varchar(50) not null,
    descripcionProducto varchar(100) not null,
    cantidadStock int not null,
    precioVentaUnitario decimal(10,2) not null,
    precioVentaMayor decimal(10, 2) not null,
    precioCompra decimal(10, 2) not null,
    imagenProducto longblob,
    distribuidorid int not null,
    categoriaProductosId int not null,
    primary key PK_productoId (productoId),
    constraint FK_Productos_Distribuidores foreign key (distribuidorId)
		references Distribuidores (distribuidorId),
	constraint FK_Productos_categoriaProductos foreign key (categoriaProductosId)
		references categoriaProductos (categoriaProductosId)
);

create table Facturas(
	facturaId int not null auto_increment,
    fecha date not null,
    hora time not null,
    total decimal(10,2),
    clienteId int not null,
    empleadoId int not null,
    productoId int not null,
    primary key PK_facturaId (facturaId),
    constraint FK_Facturas_Clientes foreign key (clienteId)
		references Clientes(clienteId),
	constraint FK_Facturas_Empleados foreign key (empleadoId)
		references Empleados(empleadoId),
	constraint FK_Facturas_Productos foreign key (productoId)
		references Productos(productoId)
);

create table Ticketsoporte(
	ticketSoporteId int not null auto_increment,
    descripcionTicket varchar(250) not null,
    estatus varchar(30) not null,
    clienteId int not null,
    facturaId int,
    primary key PK_ticketSoporteId (ticketSoporteId),
    constraint FK_Ticketsoporte_Clientes foreign key (clienteId)
		references Clientes (clienteId),
	constraint FK_Ticketsoporte_Facturas foreign key (facturaId)
		references Facturas (facturaId)
);
 
create table Compras(
	compraId int not null auto_increment,
    fechaCompra date not null,
    totalCompra decimal(10, 2),
    primary key PK_compraId (compraId)
);
 
create table detalleCompra(
	detalleCompraId int not null auto_increment,
    cantidadCompra int not null,
    productoId int not null,
    compraId int not null,
    primary key PK_detalleCompraId (detalleCompraId),
    constraint FK_detalleCompra_Productos foreign key (productoId)
		references Productos (productoId),
	constraint PK_detalleCompra_Compras foreign key (compraId)
		references Compras (compraId)
);
 
create table Promociones(
	promocionId int not null auto_increment,
    precioPromocion decimal(10, 2) not null,
    descripcionPromocion varchar(100),
    fechaInicio date not null,
    fechaFinalizacion date not null, 
    productoId int not null,
    primary key PK_promocionId (promocionId),
    constraint FK_Promociones_Productos foreign key (productoId)
		references Productos (productoId)
);
 
create table detalleFactura(
	detallefacturaId int not null auto_increment,
    facturaId int not null,
    productoId int not null,
    primary key PK_detallefacturaId (detallefacturaId),
    constraint FK_detalleFactura_Facturas foreign key (facturaId)
		references Facturas (facturaId),
	constraint FK_detalleFactura_Productos foreign key (productoId)
		references Productos (productoId)
);

select * from Clientes;

create table NivelesAcceso(
	nivelAccesoId int not null auto_increment,
    nivelAcceso varchar(40) not null,
    primary key PK_nivelAccesoId(nivelAccesoId)
);

-- Usuarios(usuario, contraseña, nivelAcceso)
create table  Usuario(
	usuarioId int not null auto_increment,
    usuario varchar(30) not null,
    contrasenia varchar(100) not null,
    nivelAccesoId int not null,
    empleadoId int not null,
    primary key PK_usuarioId(usuarioId),
    constraint FK_Usuario_NivelesAcceso foreign key Usuarios(nivelAccesoId)
		references NivelesAcceso(nivelAccesoId),
	constraint FK_Usuario_Empleados foreign key Usuarios(empleadoId)
		references Empleados(empleadoId)
);

-- Nivel de Acceso
