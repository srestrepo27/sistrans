package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLContiene 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra ac� para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaSuperAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicaci�n
	 */
	private PersistenciaSuperAndes pp;
	
	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicaci�n
	 */
	public SQLContiene(PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarProductoAlCarrito(PersistenceManager pm, long carritoId, String codigoProducto)
	{
		Query q= pm.newQuery(SQL,"INSERT INTO" + pp.darTablaContiene() + "(carritoId,codigoProducto) values (?,?)");	
		q.setParameters(carritoId,codigoProducto);
		return (long) q.execute();
	}
	
	public List<String> darProductosEnElCarrito(PersistenceManager pm,long carritoId)
	{
		Query q= pm.newQuery(SQL,"SELECT codigoProducto FROM " + pp.darTablaContiene() + "WHERE idCarrito= ?");	
		q.setParameters(carritoId);
		q.setResultClass(String.class);
		return (List<String>) q.execute();
	}
	
	public long devolverProducto(PersistenceManager pm, String codigoProducto)
	{
		Query q= pm.newQuery(SQL,"DELETE * FROM " + pp.darTablaContiene() + " WHERE codigoProducto= ?");	
		q.setParameters(codigoProducto);
		return (long) q.executeUnique();
	}
	
	public long terminarCompra(PersistenceManager pm, long carritoId)
	{
		Query q= pm.newQuery(SQL,"DELETE * FROM " + pp.darTablaContiene() + " WHERE carritoId= ?");	
		q.setParameters(carritoId);
		return (long) q.executeUnique();
	}
	
	public long devolverTodoPorCarritoAbandonado(PersistenceManager pm, long carritoId)
	{
		Query q= pm.newQuery(SQL,"DELETE  codigoProducto FROM " + pp.darTablaContiene() + " WHERE carritoId= ?");	
		q.setParameters(carritoId);
		return (long) q.executeUnique();
	}
	
	//SI EL CARRITO TIENE PRODUCTOS Y ESTA ABANDONADO SE DEVUELVE TOdO
}