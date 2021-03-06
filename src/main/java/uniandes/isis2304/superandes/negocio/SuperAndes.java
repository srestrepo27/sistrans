package uniandes.isis2304.superandes.negocio;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.superandes.persistencia.PersistenciaSuperAndes;
public class SuperAndes 
{
	private static Logger Log = Logger.getLogger(SuperAndes.class.getName());
	
	private PersistenciaSuperAndes ps;
	
	
	
	public SuperAndes ()
	{
		ps=PersistenciaSuperAndes.getInstance();
		
	}
	public SuperAndes (JsonObject tableCofig)
	{
		ps= PersistenciaSuperAndes.getInstance(tableCofig);
		
	}
	public void cerrarUnidadPersistencia ()
	{
		ps.cerrarUnidadPersistencia();
	}
	/* ****************************************************************
	 * 			M�todos para manejar las Bodegas
	 *****************************************************************/
	public Bodega adicionarBodega (long id, double peso, double volumen, String sucursal, String categoria)
	{
		Log.info("Adicionando Bodega "+ id);
		Bodega bodega = ps.adicionarBodega(id, peso, volumen, sucursal, categoria);
        Log.info ("Adicionando bodega: " + id);
        return bodega;
	}
	public long eliminarBodegaPorId (long id)
	{
        Log.info ("Eliminando bodega por id: " + id);
        long resp = ps.eliminarBodegaPorId(id);
        Log.info ("Eliminando bodega por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	public Bodega darBodegaPorId (long id)
	{
        Log.info ("Dar informaci�n de una bodega por id: " + id);
        Bodega bodega = ps.darBodega(id);
        Log.info ("Buscando bebedor por Id: " + bodega != null ? bodega : "NO EXISTE");
        return bodega ;
	}
	public List<VOBodega> darVOTBoegas ()
	{
		Log.info ("Generando las VO bodegas");        
        List<VOBodega> vobodegas = new LinkedList<VOBodega> ();
        for (Bodega tb : ps.darBodegas ())
        {
        	vobodegas.add (tb);
        }
        Log.info ("Generando los VO de Bodega: " + vobodegas.size() + " existentes");
        return vobodegas;
	}

	/* ****************************************************************
	 * 			M�todos para manejar los clientes 
	 *****************************************************************/
	public Cliente  adicionarCliente (long pCodigo, String pCorreo, String pNombre, int pPuntos, String nombreSucursal)
	{
		Log.info("Adicionando cliente a la sucursal"+ nombreSucursal);
		Cliente cliente = ps.adicionarcliente( pCorreo, pNombre, pPuntos, nombreSucursal);
		Log.info("Adicionando cliente a la sucursal"+ nombreSucursal);
        return cliente ;
	}
	public long eliminarClienteId (long id)
	{
        Log.info ("Eliminando cliente por id: " + id);
        long resp = ps.eliminarClienteId(id);
        Log.info ("Eliminando cliente por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	public long eliminarClientePorNombre (String nombre)
	{
	        Log.info ("Eliminando cliente por nombre: " + nombre);
	        long resp = ps.eliminarClientePorNombre(nombre);
	        Log.info ("Eliminando cliente por nombre: " + resp + " tuplas eliminadas");
	        return resp;
	}
	public Cliente darClietePorId (long id)
	{
        Log.info ("Dar informaci�n de un cliente por id: " + id);
        Cliente cliente = ps.darClienterPorId(id);
        Log.info ("Buscando cliente por Id: " + cliente != null ? cliente : "NO EXISTE");
        return cliente;
	}
	public List<Cliente> darClientes ()
	{
		Log.info ("Listando Clientes");
        List<Cliente> clientes = ps.darClientes ();	
        Log.info ("Listando clientes: " + clientes.size() + " clientes existentes");
        return clientes;	
     }
	public List<VOCliente> darVOCLiente ()
	{
		Log.info ("Generando los VO de cliente");        
        List<VOCliente> voTipos = new LinkedList<VOCliente> ();
        for (Cliente tb : ps.darClientes ())
        {
        	voTipos.add (tb);
        }
        Log.info ("Generando los VO de Tipos de bebida: " + voTipos.size() + " existentes");
        return voTipos;
	}
	/* ****************************************************************
	 * 			M�todos para manejar los estantes  
	 *****************************************************************/
	public Estante adicionarEstante ( double peso, double volumen, String sucursal, String categoria)
	{
        Log.info ("Adicionando estante: " + peso+ volumen+sucursal);
        Estante estante = ps.adicionarEstante(peso, volumen, sucursal, categoria);
        Log.info ("Adicionando estnte: " + peso+ volumen+sucursal);
        return estante;
	}
	
	/* ****************************************************************
	 * 			M�todos para manejar las sucursales  
	 *****************************************************************/
	
	
	public Sucursal adicionarsucursal ( String nombre,String  direccion,String  ciudad)
	{
        Log.info ("Adicionando sucursal: " + nombre);
        Sucursal sucursal = ps.adicionarSucursal(nombre, direccion, ciudad);
        Log.info ("Adicionando sucursal: " + nombre );
        return sucursal;
	}

	/* ****************************************************************
	 * 			M�todos para manejar los productos
	 *****************************************************************/
	
	public Producto adicionarproducto ( String codigoBarras, String unidadMedida, String nombre, String marca, double precioVenta,
			double precioCompra, String presentacion, double precioXunidad, int cantidad, String empaque,
			boolean percedero, String categoria, int nivelReo, long estantesId, long bodegasId, long pedidoId)
	{
        Log.info ("Adicionando producto: " + nombre);
        Producto producto  = ps.adicionarProducto(codigoBarras, unidadMedida, nombre, marca, precioVenta, precioCompra, presentacion, precioXunidad, cantidad, empaque, percedero, categoria, nivelReo, estantesId, bodegasId, pedidoId);
        Log.info ("Adicionando producto: " + nombre );
        return producto;
	}
	public long eliminarProductoCodigoB (String id)
	{
        Log.info ("Eliminando cliente por id: " + id);
        long resp = ps.eliminarProductoCodigo(id);
        Log.info ("Eliminando cliente por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/* ****************************************************************
	 * 			M�todos para manejar los proveedor
	 *****************************************************************/
	
	public Proveedor adicionarproveedor  ( String pNombre, String pNit)
	{
        Log.info ("Adicionando proveedor : " + pNit);
        Proveedor proveedor = ps.adicionarProveedor(pNombre, pNit);
        Log.info ("Adicionando proveedor: " + pNit );
        return proveedor ;
	
	}
	/* ****************************************************************
	 * 			M�todos para manejar los compra 
	 *****************************************************************/
	
	public Compra adicionarcompra ( String productoCodigo,long clienteId, String factura,double total, Timestamp fecha )
	{
        Log.info ("Adicionando compra: " + productoCodigo+ clienteId);
       Compra compra = ps.adicionarCompra(productoCodigo, clienteId, factura, total, fecha);
        Log.info ("Adicionando compra: " + productoCodigo+ clienteId );
        return compra ;
	}
	public List<VOCompra> darVOCompra ()
	{
		Log.info ("Generando los VO de compras");        
        List<VOCompra> voTipos = new LinkedList<VOCompra> ();
        for (Compra tb : ps.darCompras ())
        {
        	voTipos.add (tb);
        }
        Log.info ("Generando los VO de compras: " + voTipos.size() + " existentes");
        return voTipos;
	}
	public long eliminarCompraId (String id)
	{
        Log.info ("Eliminando cliente por id: " + id);
        long resp = ps.eliminarCompraId(id);
        Log.info ("Eliminando cliente por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	
	/* ****************************************************************
	 * 			M�todos para manejar las empresas  
	 *****************************************************************/
	public Empresa adicionarempresa  (String nit , String direccion, long clientesCodigo)
	{
        Log.info ("Adicionando empresa: " + nit);
       Empresa empresa = ps.adicionarEmpresa(nit, direccion, clientesCodigo);
        Log.info ("Adicionando compra: " + nit );
        return empresa;
	}
	/* ****************************************************************
	 * 			M�todos para manejar los estantes
	 *****************************************************************/
	public Estante adicionarestante  (double peso, double volumen,String sucursal, String categoria)
	{
        Log.info ("Adicionando estante a la sucursal: " + sucursal);
       Estante estante = ps.adicionarEstante(peso, volumen, sucursal, categoria);
        Log.info ("Adicionando  estante a la sucursal: " + sucursal );
        return estante;
	}
	/* ****************************************************************
	 * 			M�todos para manejar los pedidos 
	 *****************************************************************/
	public Pedido adicionarpedido  (Date fecha,String  proveedorNit,String  nombreSucursal)
	{
        Log.info ("Adicionando al proveedor : " + proveedorNit);
        Pedido pedidio =ps.adicionarPedido(fecha, proveedorNit, nombreSucursal);
        Log.info ("Adicionando  al proveedor : " + proveedorNit );
        return pedidio ;
	}
	
	/* ****************************************************************
	 * 			M�todos para manejar las personas 
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			M�todos para manejar los producos 
	 *****************************************************************/
	public List<Producto> darProductoPorNombre(String nombre)
	{
		Log.info("buscando producto");
		
		List<Producto> ls= ps.darProductoPorNombre(nombre);
		
		Log.info("se encontraron " + ls.size()+ " productos");
		
		return ls;
	}
	public void realizarVenta(List<Producto> productos, long cliente)
	{
		
		Log.info("realizando venta de "+ productos.size() +"al cliente" +cliente );
		ps.realizarVenta( productos, cliente);
		Log.info("realizando venta de " +productos.size() +"al cliente" + cliente);
	}
	
	public void realizarPedido(String codigoBarras, String nombreSucursal)
	{
		Log.info("realizando verificacion si se necesita pedido");
		ps.realizarPedido(codigoBarras, nombreSucursal);
		Log.info("realizando pedido de"+ codigoBarras);
		
	}
	public long[] limpiarsuperAndes() 
	{
		Log.info ("Limpiando la BD de SuperAndes");
        long [] borrados = ps.limpiarSuperAndes();	
        Log.info ("Limpiando la BD de SuperAndes: Listo!");
        return borrados;
	}
	
	/* ****************************************************************
	 * 			M�todos para manejar los promocion proveedores 
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			M�todos para manejar los promocion sucursal 
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			M�todos para manejar la asociacion provee
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			M�todos para manejar los proveedores
	 *  
	 *****************************************************************/
	
	
	/* ****************************************************************
	 * 			M�todos para manejar los supermercados
	 *  
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			REQUERIMIENTOS DE CONSULTA
	 *  
	 *****************************************************************/
	public double RFC1(String fechai, String fechaf )
	{
		return ps.RFC1(fechai, fechaf);
	}
	public List<Object[]> RFC2()
	{
		return ps.RFC2();
	}
	public List<Pedido> RFC5()
	{
		return ps.RFC5();
	}
	
	
	public List<Compra> RFC6(long cedula, String fecha1, String fecha2)
	{
		return ps.RFC6(cedula, fecha1, fecha2);
	}
	
	public String RFC3()
	{
		return "Estantes:"+ "\n"+ ps.ocupacionEstantes() + "\n"+ "Bodegas: " + "\n"+ ps.ocupacionBodegas();
	}
	
	public List<String> RFC8()
	{
		Log.info("ejecutando requerimiento de consulta 8");
		return ps.RFC8();
	}
	public List<String> RFC7(String producto)
	{
		Log.info("ejecutando requerimiento de consulta 7");
		return ps.RFC7(producto);
	}
	public List<String> RFC9()
	{
		Log.info("ejecutando requerimiento de consulta 9");
		return ps.RFC9();
	}
	
	
	// ITERACION 2
	
	//Carrito y sus metodos
	public long adicionarProductoAlCarrito(long carritoId, String codigoProducto)
	{
		Log.info("Adicionando el producto: " + codigoProducto +"en el carrito: "+ carritoId);
		return ps.adicionarProductoAlCarrito(carritoId, codigoProducto);
	}
	public long devolverProductoDelCarrito(long carritoId, String codigoProducto)
	{
		Log.info("Devolviendo el producto: " + codigoProducto +"en el carrito: "+ carritoId);
		return ps.devolverProductoDelCarrito(carritoId, codigoProducto);
	}
	public String asignarCarrito(long idCliente)
	{
		System.out.println("superndes");
		Log.info("Asignando carrito al cliente con id: "+ idCliente);
		 return ps.asignarCarrito(idCliente);
	}
	
	public void abandonarCarrito(long idCliente)
	{
		Log.info("Abandonando carrito del cliente" + idCliente);
		ps.abandonarCarrito(idCliente);
	}
	
	public Sucursal obtenerSucursalPorNombre(String nombre)
	{
		Log.info("Obteniendo sucursal con nombre: "+ nombre);
		Sucursal resp=ps.obtenerSucursalPorNombre(nombre);
		
		Log.info("Obteniendo sucursal con nombre: "+ nombre);
		return resp;
	}
	
	public List<Producto> productosEnElCarrito(long carritoId)
	{
		Log.info("Mostrando productos en el carrito del cliente " + carritoId);
		List<Producto> resp= ps.mostrarProductosEnElCarrito(carritoId);
		return resp;
	}
	
	
	public void terminarCompra(long carritoId, long clienteId)
	{
		Log.info("Terminando compra del cliente "+ carritoId);
		ps.terminarCompra(carritoId, clienteId);
	}
	
	public void devolverPorAbandono()
	{
		Log.info("Devolviendo productos de carritos abandonados");
		ps.devolverTodoPorAbandono();
	}
	
	//ITERACION 3
	
	public List<Cliente> RFC10admin(String producto, String fecha1, String fecha2, String criterio)
	{
		Log.info("Retornando la informacion del producto" + producto+ "en las fechas" + fecha1 +fecha2+ "bajo el criterio:" + criterio		);
		
		List<Cliente> ret =ps.RFC10Admin(producto, fecha1, fecha2, criterio);
		return ret;
	}
	
	public List<Cliente> RFC10gerente(String producto, String fecha1, String fecha2, String criterio)
	{
		Log.info("Retornando la informacion del producto" + producto+ "en las fechas" + fecha1 +fecha2+ "bajo el criterio:" + criterio		);
		
		List<Cliente> ret =ps.RFC10Gerente(producto, fecha1, fecha2, criterio);
		return ret;
	}
	
	public List<Compra> RFC11(String fecha1, String fecha2)
	{
		Log.info("Obteniendo las compras en las fechas" + fecha1 +"y" +fecha2);
		List<Compra> ret= ps.RFC11(fecha1, fecha2);
		return ret;
	}
	
	public List<Compra> RCF121(String fecha1, String fecha2)
	{
		Log.info("Obteniendo los productos mas vendidos entre las fechas" + fecha1 +"y" +fecha2);
		List<Compra> ret= ps.RFC121(fecha1, fecha2);
		return ret;
	}
	
	public List<Compra> RCF122(String fecha1, String fecha2)
	{
		Log.info("Obteniendo los productos menos vendidos entre las fechas" + fecha1 +"y" +fecha2);
		List<Compra> ret= ps.RFC122(fecha1, fecha2);
		return ret;
	}
	
	public List<Proveedor> RFC123(String fecha1,String fecha2)
	{
		Log.info("Mostrando los proveedores menos solicitados en las fechas:"+fecha1 +"y" +fecha2);
		List<Proveedor> ret= ps.RFC123(fecha1, fecha2);
		return ret;
	}
	
	public List<Proveedor> RFC124(String fecha1,String fecha2)
	{
		Log.info("Mostrando los proveedores mas solicitados en las fechas:"+fecha1 +"y" +fecha2);
		List<Proveedor> ret= ps.RFC124(fecha1, fecha2);
		return ret;
	}
	
	public List<String> RFC131()
	{
		Log.info("Clientes que compran cada mes");
		List<String> ret= ps.RFC131();
		return ret;
	}
	
	public List<Compra> RFC132()
	{
		Log.info("Compras de un producto con precio alto");
		List<Compra> ret= ps.RFC132();
		return ret;
	}
	
	public List<Compra> RFC133()
	{
		Log.info("Compras de un producto de tecnologia o herramientas");
		List<Compra> ret= ps.RFC133();
		return ret;
	}
	
	
	
	
	
	

		

	
}

	
