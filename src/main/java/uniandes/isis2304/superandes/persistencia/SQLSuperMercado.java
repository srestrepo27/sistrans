package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.SuperMercado;

class SQLSuperMercado 
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
		public SQLSuperMercado(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarSuperMercado(PersistenceManager pm,long id)
		{
			Query q= pm.newQuery(SQL, "INSERT INTO" + pp.darTablaSucursal() + "(id) values (?)");
			q.setParameters(id);
			return (long) q.executeUnique();
		}
		
		public SuperMercado obtenerSuperMercado(PersistenceManager pm,long id)
		{
			Query q= pm.newQuery(SQL, "INSERT INTO" + pp.darTablaSucursal() + "WHERE id=?");
			q.setResultClass(SuperMercado.class);
			q.setParameters(id);
			return (SuperMercado) q.executeUnique();
		}
		
}
