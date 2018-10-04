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
	 * 			Métodos para manejar las Bodegas
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
        Log.info ("Dar información de una bodega por id: " + id);
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
	 * 			Métodos para manejar los clientes 
	 *****************************************************************/
	public Cliente  adicionarCliente (long pCodigo, String pCorreo, String pNombre, int pPuntos, long id)
	{
		Log.info("Adicionando cliente "+ id);
		Cliente cliente = ps.adicionarcliente( pCorreo, pNombre, pPuntos, id);
		Log.info("Adicionando cliente "+ id);
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
        Log.info ("Dar información de un cliente por id: " + id);
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
	 * 			Métodos para manejar los estantes  
	 *****************************************************************/
	public Estante adicionarEstante ( double peso, double volumen, String sucursal, String categoria)
	{
        Log.info ("Adicionando estante: " + peso+ volumen+sucursal);
        Estante estante = ps.adicionarEstante(peso, volumen, sucursal, categoria);
        Log.info ("Adicionando estnte: " + peso+ volumen+sucursal);
        return estante;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las sucursales  
	 *****************************************************************/
	
	
	public Sucursal adicionarsucursal ( String nombre,String  direccion,String  ciudad)
	{
        Log.info ("Adicionando sucursal: " + nombre);
        Sucursal sucursal = ps.adicionarSucursal(nombre, direccion, ciudad);
        Log.info ("Adicionando sucursal: " + nombre );
        return sucursal;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los productos
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
	 * 			Métodos para manejar los proveedor
	 *****************************************************************/
	
	public Proveedor adicionarproveedor  ( String pNombre, String pNit)
	{
        Log.info ("Adicionando proveedor : " + pNit);
        Proveedor proveedor = ps.adicionarProveedor(pNombre, pNit);
        Log.info ("Adicionando proveedor: " + pNit );
        return proveedor ;
	
	}
	/* ****************************************************************
	 * 			Métodos para manejar los compra 
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
	 * 			Métodos para manejar las empresas  
	 *****************************************************************/
	public Empresa adicionarempresa  (String nit , String direccion, long clientesCodigo)
	{
        Log.info ("Adicionando empresa: " + nit);
       Empresa empresa = ps.adicionarEmpresa(nit, direccion, clientesCodigo);
        Log.info ("Adicionando compra: " + nit );
        return empresa;
	}
	/* ****************************************************************
	 * 			Métodos para manejar los estantes
	 *****************************************************************/
	public Estante adicionarestante  (double peso, double volumen,String sucursal, String categoria)
	{
        Log.info ("Adicionando estante a la sucursal: " + sucursal);
       Estante estante = ps.adicionarEstante(peso, volumen, sucursal, categoria);
        Log.info ("Adicionando  estante a la sucursal: " + sucursal );
        return estante;
	}
	/* ****************************************************************
	 * 			Métodos para manejar los pedidos 
	 *****************************************************************/
	public Pedido adicionarpedido  (Date fecha,String  proveedorNit,Long  superMercadoId)
	{
        Log.info ("Adicionando al proveedor : " + proveedorNit);
        Pedido pedidio =ps.adicionarPedido(fecha, proveedorNit, superMercadoId);
        Log.info ("Adicionando  al proveedor : " + proveedorNit );
        return pedidio ;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las personas 
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			Métodos para manejar los producos 
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
	
	public void realizarPedido(String codigoBarras)
	{
		Log.info("realizando verificacion si se necesita pedido");
		ps.realizarPedido(codigoBarras);
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
	 * 			Métodos para manejar los promocion proveedores 
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			Métodos para manejar los promocion sucursal 
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			Métodos para manejar la asociacion provee
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			Métodos para manejar los proveedores
	 *  
	 *****************************************************************/
	
	
	/* ****************************************************************
	 * 			Métodos para manejar los supermercados
	 *  
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			REQUERIMIENTOS DE CONSULTA
	 *  
	 *****************************************************************/
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

		

	
}

	
