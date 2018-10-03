package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.PromocionProveedor;

class SQLPromocionProveedor 
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
		public SQLPromocionProveedor(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarPromocionProveedor(PersistenceManager pm, long id, String producto, String proveedor)
		{
			Query q= pm.newQuery(SQL,"INSERT INTO " + pp.darTablaPromocionProveedor() + " (id,producto,proveedor) values (?,?,?)" );
			q.setParameters(id,producto,proveedor);
			return(long) q.executeUnique();
		}
		
		public long eliminarPromocionProveedorPorId(PersistenceManager pm, long id)
		{
			Query q= pm.newQuery(SQL,"DELETE FROM " + pp.darTablaPromocionProveedor() + " WHERE id=?" );
			q.setParameters(id);
			return(long) q.executeUnique();
		}
		
		public long eliminarPromocionesProveedor(PersistenceManager pm, String proveedor)
		{
			Query q= pm.newQuery(SQL,"DELETE FROM " + pp.darTablaPromocionProveedor() + " WHERE proveedor=?" );
			q.setParameters(proveedor);
			return(long) q.executeUnique();
		}
		
		public List<PromocionProveedor> darPromocionesProveedor(PersistenceManager pm, String proveedor)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM " + pp.darTablaPromocionProveedor() + " WHERE proveedor=?" );
			q.setResultClass(PromocionProveedor.class);
			q.setParameters(proveedor);
			return (List<PromocionProveedor>) q.execute();
		}
		
		public PromocionProveedor darPromocionPorId(PersistenceManager pm, long id)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM " + pp.darTablaPromocionProveedor() + " WHERE id=?" );
			q.setResultClass(PromocionProveedor.class);
			q.setParameters(id);
			return (PromocionProveedor) q.executeUnique();
		}
}
