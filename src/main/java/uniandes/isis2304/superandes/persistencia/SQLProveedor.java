package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Proveedor;

class SQLProveedor 
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
		public SQLProveedor(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarProveedor(PersistenceManager pm, String nit, String nombre)
		{
			Query q= pm.newQuery(SQL, "INSERT INTO" + pp.darTablaProveedor() + "(nit,nombre) values (?,?)");
			q.setParameters(nit,nombre);
			return (long) q.executeUnique();
		}
		
		public long eliminarProveedorPorNit(PersistenceManager pm, String nit)
		{
			Query q= pm.newQuery(SQL, "DELETE FROM" + pp.darTablaProveedor() + "WHERE nit=?");
			q.setParameters(nit);
			return (long) q.executeUnique();
		}
		
		public Proveedor obtenerProveedorPorNit(PersistenceManager pm, String nit)
		{
			Query q= pm.newQuery(SQL, "SELECT * FROM" + pp.darTablaProveedor() + "WHERE nit=?");
			q.setResultClass(Proveedor.class);
			q.setParameters(nit);
			return (Proveedor) q.executeUnique();
		}
		
		public List<Proveedor> obtenerProveedoresPorNombre(PersistenceManager pm, String nombre)
		{
			Query q= pm.newQuery(SQL, "SELECT * FROM" + pp.darTablaProveedor() + "WHERE nombre=?");
			q.setResultClass(Proveedor.class);

			q.setParameters(nombre);
			return (List<Proveedor>) q.executeUnique();
		}
}
