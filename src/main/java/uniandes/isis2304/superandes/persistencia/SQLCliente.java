package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Cliente;

class SQLCliente
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
	public SQLCliente(PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarCliente(PersistenceManager pm,long id, String nombre, String correo, int puntos, long supermercadoId)
	{
		Query q= pm.newQuery(SQL,"INSERT INTO" + pp.darTablaCliente()+"(id, nombre, correo, puntos, supermercadoId) values (?,?,?,?,?)");
		q.setParameters(id,nombre,correo,puntos,supermercadoId);
		return (long) q.executeUnique();
	}
	
	public long eliminarClientePorId(PersistenceManager pm,long id)
	{
		Query q= pm.newQuery(SQL,"DELETE FROM" + pp.darTablaCliente() + "WHERE id= ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}
	
	public long eliminarClientePorNombre(PersistenceManager pm,String nombre)
	{
		Query q= pm.newQuery(SQL,"DELETE FROM" + pp.darTablaCliente() + "WHERE nombre= ?");
		q.setParameters(nombre);
		return (long) q.executeUnique();
	}
	
	public Cliente darClientePorId(PersistenceManager pm,long id)
	{

		Query q= pm.newQuery(SQL,"SELECT * FROM" + pp.darTablaCliente() + "WHERE id= ?");
		q.setResultClass(Cliente.class);
		q.setParameters(id);
		return (Cliente) q.executeUnique();
	}
	
	public List<Cliente> darClientesPorNombre(PersistenceManager pm,String nombre)
	{

		Query q= pm.newQuery(SQL,"SELECT * FROM" + pp.darTablaCliente() + "WHERE nombre= ?");
		q.setResultClass(Cliente.class);
		q.setParameters(nombre);
		return (List<Cliente>) q.execute();
	}
	
	public List<Cliente> darClientes(PersistenceManager pm)
	{
		Query q= pm.newQuery(SQL,"SELECT * FROM" + pp.darTablaCliente());
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.execute();
	}
	
	public long cambiarCorreoCliente(PersistenceManager pm, long id, String correo)
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaCliente() + " SET correo = ? WHERE id = ?");
		 q.setParameters(id,correo);
		 return(long)q.executeUnique();

	}
}
