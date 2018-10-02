package uniandes.isis2304.superandes.persistencia;

import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.PromocionSucursal;

class SQLPromocionSucursal
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
		public SQLPromocionSucursal(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarPromocionSucursal(PersistenceManager pm, long id, Date fechaLimite,String sucursalNombre,String productoCodigo)
		{
			Query q= pm.newQuery(SQL,"INSERT INTO " + pp.darTablaPromocionSucursal() + " (id,fechaLimite,sucursalNombre,productoCodigo) values (?,?,?,?)" );
			q.setParameters(id,fechaLimite,sucursalNombre,productoCodigo);
			return(long) q.executeUnique();
		}
		
		public long eliminarPromocionSucursalPorId(PersistenceManager pm, long id)
		{
			Query q= pm.newQuery(SQL,"DELETE FROM " + pp.darTablaPromocionSucursal() + " WHERE id=?" );
			q.setParameters(id);
			return(long) q.executeUnique();
		}
		public long eliminarPromocionSucursalPorSucursal(PersistenceManager pm, String sucursalNombre)
		{
			Query q= pm.newQuery(SQL,"DELETE FROM " + pp.darTablaPromocionSucursal() + " WHERE sucursalNombre=?" );
			q.setParameters(sucursalNombre);
			return(long) q.executeUnique();
		}
		
		public PromocionSucursal darPromocionSucursalPorId(PersistenceManager pm, long id)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM " + pp.darTablaPromocionSucursal() + " WHERE id=?" );
			q.setResultClass(PromocionSucursal.class);
			q.setParameters(id);
			return(PromocionSucursal) q.executeUnique();
		}
		
		public List<PromocionSucursal> darPromocionesSucursal(PersistenceManager pm, String sucursalNombre)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM " + pp.darTablaPromocionSucursal() + " WHERE sucursalNombre=?" );
			q.setResultClass(PromocionSucursal.class);
			q.setParameters(sucursalNombre);
			return(List<PromocionSucursal>) q.execute();
		}
}
