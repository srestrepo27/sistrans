package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Carrito;
import uniandes.isis2304.superandes.negocio.Producto;

public class SQLContiene 
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
	public SQLContiene(PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarProductoAlCarrito(PersistenceManager pm, long carritoId, String codigoProducto)
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 INSERT INTO" + pp.darTablaContiene() + "(carritoId,codigoProducto) values (?,?)");	
		q.setParameters(carritoId,codigoProducto);
		return (long) q.execute();
	}
	
	public List<Producto> darProductosEnElCarrito(PersistenceManager pm,long carritoId)
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 SELECT codigoProducto FROM " + pp.darTablaContiene() + "WHERE idCarrito= ?");	
		q.setParameters(carritoId);
		q.setResultClass(Producto.class);
		return (List<Producto>) q.execute();
	}
	
	public long devolverProducto(PersistenceManager pm, String codigoProducto, long carritoId)
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 DELETE * FROM " + pp.darTablaContiene() + " WHERE codigoProducto= ? AND carritoId= ?");	
		q.setParameters(codigoProducto,carritoId);
		return (long) q.executeUnique();
	}
	
	public long terminarCompra(PersistenceManager pm, long carritoId)
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 DELETE * FROM " + pp.darTablaContiene() + " WHERE carritoId= ?");	
		q.setParameters(carritoId);
		return (long) q.executeUnique();
	}
	
	public long devolverTodoPorCarritoAbandonado(PersistenceManager pm, long carritoId)
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 DELETE * FROM " + pp.darTablaContiene() + " WHERE carritoId= ?");	
		q.setParameters(carritoId);
		return (long) q.executeUnique();
	}
	
	//SI EL CARRITO TIENE PRODUCTOS Y ESTA ABANDONADO SE DEVUELVE TOdO
	
	public List<String> darCarritosEnContiene(PersistenceManager pm)
	{
		Query q= pm.newQuery(SQL," SET AUTOCOMMIT 0 SELECT idcarrito FROM "+ pp.darTablaContiene());
		q.setResultClass(String.class);
		return (List<String>) q.execute();
	}
}
