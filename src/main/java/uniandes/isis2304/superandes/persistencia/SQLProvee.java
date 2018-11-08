package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Provee;


class SQLProvee
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
		public SQLProvee(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarProvee(PersistenceManager pm, String proveedor, String producto)
		{
			Query q= pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProvee() + " (proveedor,producto) values (?,?)");
			q.setParameters(proveedor,producto); 
			return (long) q.executeUnique();
		}
		
		public long eliminarProvee(PersistenceManager pm, String proveedor, String producto)
		{
			Query q= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProvee() + " WHERE proveedor=? AND producto=?");
			q.setParameters(proveedor,producto);
			return (long) q.executeUnique();
		}
		
		public Provee obtenerProvee(PersistenceManager pm, String proveedor, String producto)
		{
			Query q= pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProvee() + " WHERE proveedor=? AND producto=?");
			q.setResultClass(Provee.class);
			q.setParameters(proveedor,producto);
			return (Provee) q.executeUnique();
		}
		
		public List<Provee> obtenerProveeProveedor(PersistenceManager pm, String proveedor)
		{
			Query q= pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProvee() + " WHERE proveedor=? ");
			q.setResultClass(Provee.class);
			q.setParameters(proveedor);
			return (List<Provee>) q.execute();
		}
		
		public Provee obtenerProveeProducto(PersistenceManager pm, String producto)
		{
			Query q= pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProvee() + " WHERE producto=?");
			q.setResultClass(Provee.class);
			q.setParameters(producto);
			return (Provee) q.executeUnique();
		}
		
		
}
