use superkinalIN5CV;

-- ----------------------- CLIENTES ----------------------------

-- Agregar
delimiter $$
create procedure sp_agregarCliente(in nom varchar(40),in ape varchar(40),in tel varchar(15),in dir varchar(150),in nit varchar(15))
	begin
		insert into Clientes (nombre, apellido, telefono, direccion, nit) values
			(nom, ape, tel, dir, nit);
    end $$
delimiter ;


-- listar
delimiter $$
create procedure sp_listarCliente()
	begin
		select * from Clientes;
    end $$
delimiter ;


-- buscar
delimiter $$
create procedure sp_buscarCliente(in cliId int)
	begin
		select * from Clientes
			where clienteId = cliId;
    end $$
delimiter ;


-- eliminar
delimiter $$
create procedure sp_eliminarCliente(in cliId int)
	begin
		delete from Clientes
        where clienteId = cliId;
    end $$
delimiter ;

-- editar
delimiter $$
create procedure sp_editarCliente(in cliId int, in nom varchar(40),in ape varchar(40),in tel varchar(15),in dir varchar(150),in nit varchar(15))
	begin
		update Clientes
			set 
            nombre = nom,
            apellido = ape,
            telefono = tel,
            direccion = dir, 
            nit = nit
            where clienteId = cliId;
    end $$
delimiter ;



-- ---------------------- CARGOS -----------------------------
-- Agregar
delimiter $$
create procedure sp_agregarCargos(in nomCar varchar(30),in descCar varchar(100))
	begin
		insert into Cargos (nombreCargo, descripcionCargo) values
			(nomCar, descCar);
    end $$
delimiter ;

call sp_agregarCargos('Supervisor', 'Velar el que los trabajadores cumplan con lo requerido');

-- listar
delimiter $$
create procedure sp_listarCargos()
	begin
		select * from Cargos;
    end $$
delimiter ;


-- buscar
delimiter $$
create procedure sp_buscarCargos(in carId int)
	begin
		select * from Cargos
			where cargoId = carId;
    end $$
delimiter ;


-- eliminar
delimiter $$
create procedure sp_eliminarCargos(in carId int)
	begin
		delete 
			from Cargos
				where cargoId = carId;
    end $$
delimiter ;

-- editar
delimiter $$
create procedure sp_editarCargo(in carId int, in nomCar varchar(30),in descCar varchar(100))
	begin
		update Cargos
			set 
            nombreCargo = nomCar,
            descripcionCargo = descCar
            where cargoId = carId;
    end $$
delimiter ;

-- --------------------------------- Empleados --------------------------------------

-- Agregar
delimiter $$
create procedure sp_agregarEmpleados(in nomEmp varchar(30),in apeEmp varchar(30), in sue decimal(10, 2), in hoEn time, in hoSa time, in carId int, in encarId int)
	begin
		insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId) values
			(nomEmp, apeEmp, sue, hoEn, hoSa, carid, encarId);
    end $$
delimiter ;

call sp_agregarEmpleados('Saul', 'De Leon', 8745.09, '09:00:00', '16:00:00', 1, null);

-- listar
delimiter $$
create procedure sp_listarEmpleados()
	begin
		select E1.empleadoId, E1.nombreEmpleado, E1.apellidoEmpleado, E1.sueldo, E1.horaEntrada, E1.horaSalida,
        C.nombreCargo,
        E2.nombreEmpleado as 'Encargado'from Empleados E1
        join Cargos C on C.cargoId = E1.cargoId
        left join Empleados E2 on E1.encargadoId = E2.empleadoId;
    end $$
delimiter ;

delimiter $$
create procedure sp_listarEncargados()
	begin
		select E1.empleadoId, E1.nombreEmpleado,
			E2.nombreEmpleado from Empleados E1
			left join Empleados E2 on E1.encargadoId = E2.empleadoId where E1.encargadoId is null;
    end$$
delimiter ;

-- select * from Empleados;

-- call sp_listarEmpleados();

-- buscar
DELIMITER $$

CREATE PROCEDURE sp_buscarEmpleados(IN empId INT)
BEGIN
    SELECT E1.empleadoId, E1.nombreEmpleado, E1.apellidoEmpleado, E1.sueldo, E1.horaEntrada, E1.horaSalida,
           C.nombreCargo,
           E2.nombreEmpleado AS Encargado
    FROM Empleados E1
    JOIN Cargos C ON C.cargoId = E1.cargoId
    LEFT JOIN Empleados E2 ON E1.encargadoId = E2.empleadoId
    WHERE E1.empleadoId = empId;
END $$

DELIMITER ;


call sp_buscarEmpleados(1);


-- eliminar
delimiter $$
create procedure sp_eliminarEmpleados(in empId int)
	begin
		delete 
			from Empleados
				where empleadoId = empId;
    end $$
delimiter ;

-- editar
delimiter $$
create procedure sp_editarEmpleados(in empId int, in nomEmp varchar(30), in apeEmp varchar(30), in sue decimal(10, 2), in hoEn time, in hoSa time, in carId int, encarId int)
	begin
		update Empleados
			set 
            nombreEmpleado = nomEmp,
            apellidoEmpleado = apeEmp,
            sueldo = sue,
            horaEntrada = hoEn,
            horaSalida = hoSa,
            cargoId = carId,
            encargadoId = encarId
            where empleadoId = empId;
    end $$
delimiter ;

select * from Empleados;
call sp_editarEmpleados(4, 'Sebastian', 'Lopez', 2043.26, '04:00:00', '20:00:00', 1, 2);

-- ----------------- FACTURAS -------------------------------------

-- Agregar
delimiter $$
create procedure sp_agregarFacturas(in fe date, in ho time, in tot decimal(10, 2), in cliId int, in empId int, in proId int)
begin
    declare facturaIdOld int;
 
    insert into Facturas (fecha, hora, total, clienteId, empleadoId, productoId) 
    values (fe, ho, tot, cliId, empId, proId);
 
    set facturaIdOld = last_insert_id();
 
    insert into detalleFactura (facturaId, productoId) 
    values (facturaIdOld, proId);
 
    call sp_asignarTotal(fn_totalFactura(facturaIdOld), facturaIdOld);
end $$
 
delimiter ;
 
delimiter $$
 
create procedure sp_agregarProductoAFactura(in facId int, in proId int)
begin
 
    insert into detalleFactura (facturaId, productoId) 
    values (facId, proId);
 
    call sp_asignarTotal(fn_totalFactura(facturaId), facturaId);
end $$
 
delimiter ;

-- call sp_agregarFacturas('2021-04-20', '13:23:55', null, 1, 3);


-- listar
delimiter $$
create procedure sp_listarFacturas()
	begin
		select F.facturaId, F.fecha, F.hora, F.total,
        C.nombre,
        E.nombreEmpleado,
        P.nombreProducto from Facturas F
        join Clientes C on C.clienteId = F.clienteId
        join Empleados E on E.empleadoId = F.empleadoId
        join Productos P on P.productoId = F.productoId;
    end $$
delimiter ;


-- buscar
delimiter $$
create procedure sp_buscarFacturas(in facId int)
	begin
		select * from Facturas
			where facturaId = facId;
    end $$
delimiter ;


-- eliminar
delimiter $$
create procedure sp_eliminarFacturas(in facId int)
	begin
		delete 
			from Facturas
				where facturaId = facId;
    end $$
delimiter ;

-- editar
delimiter $$
create procedure sp_editarFacturas(in facId int, in fe date, in ho time, in tot decimal(10, 2), in cliId int, in empId int)
	begin
		update Facturas
			set 
            fecha = fe,
            hora = ho,
            total = tot,
            clienteId = cliId,
            empleadoId = empId
            where facturaId = facId;
    end $$
delimiter ;

-- --------------------- Promociones ------------------------------------------

-- Agregar
delimiter $$
create procedure sp_agregarPromociones(in prePro decimal(10, 2), in descPro varchar(100), in feIni date, in feFina date, in proId int)
	begin
		insert into Promociones (precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId) values
			(prePro, descPro, feIni, feFina, proId);
    end $$
delimiter ;


-- listar
delimiter $$
create procedure sp_listarPromociones()
	begin
		select PS.promocionId, PS.precioPromocion, PS.descripcionPromocion, PS.fechaInicio, PS.fechaFinalizacion,  
			P.nombreProducto as 'producto' from Promociones PS
            join Productos P on PS.productoId = P.productoId;
    end $$
delimiter ;


select * from Promociones;


-- buscar
delimiter $$
create procedure sp_buscarPromociones(in promoId int)
	begin
		select * from Promociones
			where promocionId = promoId;
    end $$
delimiter ;


-- eliminar
delimiter $$
create procedure sp_eliminarPromociones(in promoId int)
	begin
		delete 
			from Promociones
				where promocionId = promoId;
    end $$
delimiter ;

-- call sp_eliminarPromociones(1);

-- editar
delimiter $$
create procedure sp_editarPromociones(in promoId int, in prePro decimal(10, 2), in descPro varchar(100), in feIni date, in feFina date, in proId int)
	begin
		update Promociones
			set 
            precioPromocion = prePro,
            descripcionPromocion = descPro,
            fechaInicio = feIni,
            fechaFinalizacion = feFina,
            productoId = proId
            where promocionId = promoId;
    end $$
delimiter ;

-- ------------------- Ticket Soporte ------------------------------
DELIMITER $$
create procedure sp_AgregarTicketSoporte(in des varchar(250), in cliId int, in facId int)
begin
	insert into TicketSoporte(descripcionTicket,estatus,clienteId,facturaId) values
		(des,'Recien Creado',cliId,facId);
end $$
DELIMITER ;
 
-- call sp_AgregarTicketSoporte('Problema de wi-fi', 1, null);
 
 
DELIMITER $$
create procedure sp_ListarTicketSoporte()
begin
		select TS.ticketSoporteId, TS.descripcionTicket, TS.estatus,
				concat('Id: ', C.clienteId, ' - ', C.nombre, ' ', C.apellido) as 'Cliente',
                TS.facturaId from TicketSoporte TS
		join Clientes C on TS.clienteId = C.clienteId;
			
end $$
DELIMITER ;
 
-- select * from TicketSoporte;
 
DELIMITER $$
create procedure sp_EliminarTicketSoporte(in tikId int)
begin
	delete
		from TicketSoporte
			where ticketSoporteId = tikId;
end$$
DELIMITER ;
 
DELIMITER $$
create procedure sp_BuscarTicketSoporte(in tikiId int)
begin 
	select
		TicketSoporte.ticketSoporteId,
        TicketSoporte.descripcionTicket,
        TicketSoporte.estatus,
        TicketSoporte.clienteId,
        TicketSoporte.facturaId
			from TicketSoporte
			where ticketSoporteId = tikId;
end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_EditarTicketSoporte(in tikId int,in des varchar(250), in est varchar(30), in cliId int, in facId int )
begin
	update TicketSoporte
		set 
			descripcionTicket = des,
            estatus = est,
            clienteId = cliId,
            facturaId = facId
				where ticketSoporteId = tikId;
end $$
DELIMITER ;

-- ------------------ Detalle Factura ---------------------------
DELIMITER $$
create procedure sp_AgregarDetalleFactura(in factId int, in prodId int)
begin
	insert into DetalleFactura(facturaId, productoId) values
		(factId, prodId);
end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_ListarDetalleFactura()
begin
	select 
		DetalleFactura.detalleFacturaId,
        DetalleFactura.facturaId,
        DetalleFactura.productoId
			from DetalleFactura;
end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_EliminarDetalleFactura(in detId int)
begin
	delete
		from DetalleFactura
			where detalleFacturaId = detId;
end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_BuscarDetalleFactura(in detId int)
begin
	select 
		DetalleFactura.detalleFacturaId,
        DetalleFactura.facturaId,
        DetalleFactura.productoId
			from DetalleFactura
			where detalleFacturaId = detId;
end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_EditarDetalleFactura(in detId int, in factId int, in prodId int)
begin
	update DetalleFactura
		set 
			facturaId = factId,
            productoId = prodId
            where detalleFacturaId = detId;
end $$
DELIMITER ;

-- ------------ Distribuidor -------------------------- 
-- Agregar
DELIMITER $$
create procedure sp_agregarDistribuidor(nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15),web varchar(50))
	begin
		insert into Distribuidores(nombreDistribuidor,direccionDistribuidor,nitDistribuidor,telefonoDistribuidor,web) values
			(nomDis,dirDis,nitDis,telDis,web);
    end$$
DELIMITER ;

call sp_agregarDistribuidor('Dereck', 'Zona 2', '23529034-2', '6666-6666', null);

-- Listar

DELIMITER $$
create procedure sp_listarDistribuidores()
	begin
		select * from Distribuidores;
    end$$
DELIMITER ;
 
 -- Buscar
 
DELIMITER $$
create procedure sp_buscarDistribuidor(disId int)
	begin 
		select * from Distribuidores D 
        where disId = D.distribuidorId;
    end$$
DELIMITER ;
 
 -- eliminar
 
DELIMITER $$
create procedure sp_eliminarDistribuidor(dirId int)
	begin
		delete from Distribuidores 
        where dirId = distribuidorId;
    end$$
DELIMITER ;
 
 -- editar
 
DELIMITER $$
create procedure sp_editarDistribuidor(dirId int, nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15),web varchar(50))
	begin
		update Distribuidores D set
        D.nombreDistribuidor = nomDis,
        D.direccionDistribuidor = dirDis,
        D.nitDistribuidor = nitDis,
        D.telefonoDistribuidor = telDis,
        D.web = web
        where dirId = D.distribuidorId;
    end$$
DELIMITER ;

-- ----------------- Categoria Productos --------------------------
-- Agregar
DELIMITER $$
create procedure sp_agregarCategoriaProductos(nomCat varchar(30),desCat varchar(100))
	begin 
		insert into CategoriaProductos(nombreCategoria,descripcionCategoria) values
			(nomCat,desCat);
    end$$
DELIMITER ;

-- listar
 
DELIMITER $$
create procedure sp_listarCategoriaProductos()
	begin
		select * from CategoriaProductos;
    end$$
DELIMITER ;

-- Buscar
 
DELIMITER $$
create procedure sp_buscarCategoriaProductos(catProId int)
	begin
		select * from CategoriaProductos CP
        where catProId = CP.categoriaProductoId;
    end$$
DELIMITER ;

-- Eliminar
 
DELIMITER $$
create procedure sp_eliminarCategoriaProductos(in catProId int)
	begin 
		delete from CategoriaProductos
        where categoriaProductosId = catProId;
    end $$
DELIMITER ;


select * from CategoriaProductos;
-- Editar
 
DELIMITER $$
create procedure sp_editarCategoriaProductos(catProId int,nomCat varchar(30),desCat varchar(100) )
	begin
		update CategoriaProductos CP set
		CP.nombreCategoria = nomCat,
        CP.descripcionCategoria = desCat
        where catProId = CP.categoriaProductosId;
    end$$
DELIMITER ;

-- -- --------------------------------------------------------Compras-------------------------------------------------------------------- 

-- agregar
delimiter $$
create procedure sp_agregarCompra(in fecCom date, in totCom decimal (10,2))
	begin 
		insert into Compras (fechaCompra, totalCompra) values
			(fecCom, totCom);
    end $$
delimiter ;

-- listar
delimiter $$
create procedure sp_listarCompra()
	begin
		select * from Compras;
    end $$
delimiter ;

-- buscar
delimiter $$
create procedure sp_buscarCompra(in comId int)
	begin	
		select * from Compras 
			where compraId = comId;
    end $$
delimiter ;

-- eliminar 
delimiter $$
create procedure sp_eliminarCompra(in comId int)
	begin 
		delete from Compras
        where compraId = comId;
    end $$
delimiter ;

-- editar
delimiter $$
create procedure sp_editarCompra(in comId int,in fecCom date,in totCom decimal (10,2))
	begin 
		update Compras
			set 
				fechaCompra = fecCom,
                totalCompra = totCom
                where compraId = comId;
    end $$
delimiter ;


-- ------------------------------------------------------Productos-------------------------------------------------------------------

-- agregar
delimiter $$
create procedure sp_agregarProducto(in nom varchar(50),in des varchar(100),in can int, in preU decimal(10,2),in preM decimal(10,2),in preC decimal(10,2), in ima longblob, in disId int, in catId int)
	begin
		insert into Productos(nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductosId ) values
			(nom, des, can, preU, preM, preC, ima, disId, catId);
	end $$
delimiter ;

-- listar
delimiter $$
create procedure sp_listarProducto()
	begin 
		select P.productoId, P.nombreProducto, P.descripcionProducto, P.cantidadStock, P.precioVentaUnitario, P.precioVentaMayor, P.precioCompra, P.imagenProducto,
			D.nombreDistribuidor as 'distribuidor', 
            CP.nombreCategoria as 'categoria' from Productos P
            join Distribuidores D on P.distribuidorId =  D.distribuidorId
            join CategoriaProductos CP on P.categoriaProductosId = CP.categoriaProductosId;
    end $$
delimiter ;


-- buscar
delimiter $$
create procedure sp_buscarProducto(in proId int)
	begin 
		select P.productoId, P.nombreProducto, P.descripcionProducto, P.cantidadStock, P.precioVentaUnitario, P.precioVentaMayor, P.precioCompra, P.imagenProducto,
			D.nombreDistribuidor as 'distribuidor',
            CP.nombreCategoria as 'categoria' from Productos P
            join Distribuidores D on P.distribuidorId =  D.distribuidorId
            join CategoriaProductos CP on P.categoriaProductosId = CP.categoriaProductosId
				where P.productoId = proId;
    end $$
delimiter ;

call sp_buscarProducto(2);

-- eliminar 
delimiter $$
create procedure sp_eliminarProducto(in proId int)
	begin
		delete from Productos
			where productoId = proId;
    end $$
delimiter ;

-- editar
delimiter $$
create procedure sp_editarProducto(in proId int, in nom varchar(50),in des varchar(100),in can int, in preU decimal(10,2),in preM decimal(10,2),in preC decimal(10,2), in ima blob, in disId int, in catId int )
	begin
		update Productos	
			set 
            nombreProducto = nom,
            descripcionProduto = des,
            cantidadStock = can,
            precioVentaUnitario = preU,
            precioVentaMayor = preM,
            precioCompra = preC,
            imagenProducto = ima,
            distribuidorId = disId,
            categoriaProductosId = catId
            where productoId = proId;
    end $$
delimiter ;


-- ----------------------------------------------------DetalleCompra------------------------------------------------------------------

-- agregar
delimiter $$
create procedure sp_agregarDetalleCompra(in canC int, in proId int,in comId int)
	begin 
		insert into DetalleCompra(cantidadCompra, productoId, compraId)values
			(canC, proId, comId);
    end $$
delimiter ;

-- listar
delimiter $$
create procedure sp_ListarDetalleCompra()
	begin 
		select * from DetalleCompra;
    end $$
delimiter ;

-- buscar
delimiter $$
create procedure sp_buscarDetalleCompra(in detCId int)
	begin 
		select * from DetalleCompra
			where detalleCompraId = detCId;
    end $$
delimiter ;

-- eliminar 
delimiter $$
create procedure sp_eliminarDetalleCompra(in detCId int)
	begin 
    delete from DetalleCompra 
			where detalleCompraId = detCId;
    end $$
delimiter ;

-- editar
delimiter $$
create procedure sp_editarDetalleCompra(in detCId int, in canC int, in proId int,in comId int)
	begin 
		update DetalleCompra
			set 
				cantidadCompra = canC,
                productoId = proId,
                compraId = comId
                where detalleCompraId = detCId;
    end $$
delimiter ;

-- Usuario
delimiter $$
create procedure sp_agregarUsuario(us varchar(30), con varchar(100), nivAccId int, empId int)
begin
	insert into Usuario(usuario, contrasenia, nivelAccesoId, empleadoId) values
		(us, con, nivAccId, empId);
end $$
delimiter ;

call sp_agregarUsuario('Hfigueroa', '1234', 1, 1);

select * from Empleados;

delimiter $$
create procedure sp_buscarUsuario(us varchar(30))
begin
	select * from Usuario
		where usuario = us;
end$$
delimiter ;

delimiter $$
create procedure sp_listarNivelesAcceso()
begin
	select * from NivelesAcceso;
end$$
delimiter ;

-- Niveles de acceso
select * from Productos;

select * from DetalleFactura DF
join Productos P on DF.productoId = P.productoId
join Facturas F on DF.facturaId = F.facturaId
join Clientes C on F.clienteId = C.clienteId
where F.facturaId = 1;