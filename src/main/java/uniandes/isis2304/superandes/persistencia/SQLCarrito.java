package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Carrito;

public class SQLCarrito 
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
	public SQLCarrito(PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarCarrito(PersistenceManager pm, long carritoId)
	{
		Query q= pm.newQuery(SQL," SET AUTOCOMMIT 0 INSERT INTO " + pp.darTablaCarrito() + " (Id,cliente) values (?,null)");	
		q.setParameters(carritoId);
		return(long) q.executeUnique();
	}
	
	public long asignarClienteAlCarrito(PersistenceManager pm,long clienteId,long carritoId )
	{
		System.out.println("sql ");
		Query q= pm.newQuery(SQL," SET AUTOCOMMIT 0 UPDATE " + pp.darTablaCarrito() + " SET cliente= ? WHERE Id= ?");
		q.setParameters(clienteId);
		return (long) q.executeUnique();
	
	}
	
	public List<Carrito> darCarritosLibres(PersistenceManager pm)
	{

		Query q= pm.newQuery(SQL," SELECT * FROM " + pp.darTablaCarrito() + " WHERE cliente IS NULL");
		q.setResultClass(Carrito.class);
		return (List<Carrito>) q.execute();
	}
	//NO SE COMO LLAMARLO, DEVOLVER O ABANDONAR 
	public long devolverCarrito(PersistenceManager pm, long clienteId)
	{
		Query q= pm.newQuery(SQL," SET AUTOCOMMIT 0 UPDATE " + pp.darTablaCarrito() + " SET cliente= null WHERE cliente= ? ");
		q.setParameters(clienteId);
		return (long) q.executeUnique();
	}
	
	public long eliminarCarrito(PersistenceManager pm, long carritoId)
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 DELETE * FROM " + pp.darTablaCarrito() + " WHERE Id= ? ");
		q.setParameters(carritoId);
		return (long) q.execute();
	}
	
}
