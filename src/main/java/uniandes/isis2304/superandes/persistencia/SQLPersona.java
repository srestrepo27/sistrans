package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Cliente;


class SQLPersona 
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
		public SQLPersona(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarPersona(PersistenceManager pm,long cedula,long clientesCodigo)
		{
			Query q= pm.newQuery(SQL,"INSERT INTO " + pp.darTablaPersona()+ " (cedula,clientesCodigo) values (?,?)");
			q.setParameters(cedula,clientesCodigo);
			return (long) q.executeUnique();
		}
		
		public long eliminarClientePorCedula(PersistenceManager pm,long cedula)
		{
			Query q= pm.newQuery(SQL,"DELETE FROM " + pp.darTablaPersona()+ "WHERE cedula=?");
			q.setParameters(cedula);
			return (long) q.executeUnique();

		}
		
		public long eliminarClientePorCodigo(PersistenceManager pm,long clientesCodigo)
		{
			Query q= pm.newQuery(SQL,"DELETE FROM " + pp.darTablaPersona()+ " WHERE clientesCodigo=?");
			q.setParameters(clientesCodigo);
			return (long) q.executeUnique();

		}
		
		public Cliente darClientePorCedula(PersistenceManager pm,long cedula)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM " + pp.darTablaPersona() + "WHERE cedula= ?");
			q.setResultClass(Cliente.class);
			q.setParameters(cedula);
			return (Cliente) q.executeUnique();
		}
		
		
}
