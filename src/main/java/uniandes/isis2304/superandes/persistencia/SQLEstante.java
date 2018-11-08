package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Estante;

class SQLEstante 
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
		public SQLEstante(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarEstante(PersistenceManager pm, long id, double capacidadPeso, double capacidadVolumen, String nombreSucursal,String categoria)
		{
			Query q= pm.newQuery(SQL,"INSERT INTO " + pp.darTablaEstante() + " (id,capacidadPeso,capacidadVolumen,sucursalNombre,categoria) values (?,?,?,?,?)");
			q.setParameters(id,capacidadPeso,capacidadVolumen,nombreSucursal,categoria);
			return (long) q.executeUnique();
			
		}
		
		public long eliminarEstante(PersistenceManager pm, long id)
		{
			Query q= pm.newQuery(SQL,"DELET FROM " + pp.darTablaEstante() + " WHERE id = ?");
			q.setParameters(id);
			return (long) q.executeUnique();
		}
		
		public Estante darEstantePorId(PersistenceManager pm, long id)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM " + pp.darTablaEstante() + " WHERE id = ?");
			q.setResultClass(Estante.class);
			q.setParameters(id);
			return (Estante) q.executeUnique();
		}
}
