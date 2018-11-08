package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Producto;

class SQLProducto 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaSuperAndes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLProducto(PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarProducto(PersistenceManager pm,String codigoDeBarras, String unidadMedida,String nombre, String marca, double precioVenta, double precioCompra,String presentacion,double precioXunidad,int cantidad, String empaque,boolean perecedero,int nivelReo, long estantesId,long bodegasId, long pedidoId)
	{
		Query q= pm.newQuery(SQL,"INSERT INTO " + pp.darTablaProducto()+ " ( codigoDeBarras,  unidadMedida, nombre,  marca,  precioVenta,  precioCompra, presentacion, precioXunidad, cantidad,empaque,perecedero, nivelReo,  estantesId, bodegasId,  pedidoId) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		q.setParameters(codigoDeBarras,  unidadMedida, nombre,  marca,  precioVenta,  precioCompra, presentacion, precioXunidad, cantidad,empaque,perecedero, nivelReo,  estantesId, bodegasId,  pedidoId);
		return (long) q.executeUnique();
	}
	
	public long eliminarProducto(PersistenceManager pm,String codigoDeBarras)
	{
		Query q= pm.newQuery(SQL,"DELET FROM " +pp.darTablaProducto()+ " WHERE codigoDeBarras=?");
			q.setParameters(codigoDeBarras);
		return (long) q.executeUnique();
	}
	
	public long venderProducto(PersistenceManager pm,String codigoDeBarras)
	{
		Query q= pm.newQuery(SQL,"UPDATE " +pp.darTablaProducto()+ " SET cantidad=cantidad-1 WHERE codigoDeBarras=?");
		q.setParameters(codigoDeBarras);
		return (long) q.executeUnique();

	}
	
	public Producto darProducto(PersistenceManager pm,String codigoDeBarras)
	{
		Query q= pm.newQuery(SQL,"SELECT * FROM " +pp.darTablaProducto()+ " WHERE codigoDeBarras=?");
		q.setResultClass(Producto.class);
		q.setParameters(codigoDeBarras);
		return (Producto) q.executeUnique();
	}
		
	public List<Producto> darProductosPorNombre(PersistenceManager pm,String nombre)
	{
		Query q= pm.newQuery(SQL,"SELECT * FROM " +pp.darTablaProducto()+ " WHERE nombre=?");
		q.setResultClass(Producto.class);
		q.setParameters(nombre);
		return ( List<Producto>) q.execute();
	}
	
	public List<Producto> darProductos(PersistenceManager pm)
	{
		Query q= pm.newQuery(SQL,"SELECT * FROM " +pp.darTablaProducto());
		q.setResultClass(Producto.class);
		return ( List<Producto>) q.execute();
	}
	
	public int darCantidadProducto(PersistenceManager pm, String codigoDeBarras)
	{
		Query q= pm.newQuery(SQL,"SELECT cantidad FROM " +pp.darTablaProducto()+ " WHERE codigoDeBarras=?");
		q.setParameters(codigoDeBarras);
		q.setResultClass(Integer.class);
		return ( int) q.executeUnique();
	}
	
	public int darNivelReoProducto(PersistenceManager pm, String codigoDeBarras)
	{
		Query q= pm.newQuery(SQL,"SELECT nivelReo FROM " +pp.darTablaProducto()+ " WHERE codigoDeBarras=?");
		q.setParameters(codigoDeBarras);
		q.setResultClass(Integer.class);
		return ( int) q.executeUnique();
	}
	
	
	
}
