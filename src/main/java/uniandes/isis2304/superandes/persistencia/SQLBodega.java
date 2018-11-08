package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Bodega;

class SQLBodega 
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
	public SQLBodega(PersistenciaSuperAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarBodega(PersistenceManager pm, long id, double capacidadPeso, double capacidadVolumen, String nombreSucursal,String categoria)
	{
		Query q= pm.newQuery(SQL," INSERT INTO " + pp.darTablaBodega() + " (id,capacidadPeso,capacidadVolumen,sucursalNombre,categoria) values (?,?,?,?,?)");
		q.setParameters(id,capacidadPeso,capacidadVolumen,nombreSucursal,categoria);
		return (long) q.executeUnique();
		
	}
	
	public long eliminarBodega(PersistenceManager pm, long id)
	{
		Query q= pm.newQuery(SQL," DELET FROM " + pp.darTablaBodega() + " WHERE id = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}
	
	public Bodega darBodegaPorId(PersistenceManager pm, long id)
	{
		Query q= pm.newQuery(SQL," SELECT * FROM " + pp.darTablaBodega() + " WHERE id = ? ");
		q.setResultClass(Bodega.class);
		q.setParameters(id);
		return (Bodega) q.executeUnique();
	}
	public List<Bodega> darBodegas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, " SELECT * FROM " + pp.darTablaBodega ());
		q.setResultClass(Bodega.class);
		return (List<Bodega>) q.executeList();
	}
	
}
