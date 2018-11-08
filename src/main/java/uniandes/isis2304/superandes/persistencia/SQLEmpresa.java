package uniandes.isis2304.superandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Empresa;

class SQLEmpresa 
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
		public SQLEmpresa(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarEmpresa(PersistenceManager pm,String nit, String direccion,long clientesCodigo)
		{
			Query q= pm.newQuery(SQL,"INSERT INTO " + pp.darTablaEmpresa()+ " (nit,direccion,clientesCodigo) values (?,?,?)");
			q.setParameters(nit,direccion,clientesCodigo);
			return (long) q.executeUnique();
		}
		
		public long eliminarEmpresaPorClienteCodigo(PersistenceManager pm, long clienteCodigo)
		{
			Query q= pm.newQuery(SQL,"DELETE FROM " + pp.darTablaEmpresa() +" WHERE clientesCodigo=?");
			q.setParameters(clienteCodigo);
			return (long) q.executeUnique();
		}
		
		public long eliminarEmpresaPorClienteCodigo(PersistenceManager pm, String nit)
		{
			Query q= pm.newQuery(SQL,"DELETE FROM " + pp.darTablaEmpresa() +" WHERE nit=?");
			q.setParameters(nit);
			return (long) q.executeUnique();
		}
		
		public long cambiarDireccionEmpresa(PersistenceManager pm, String direccion)
		{
			Query q= pm.newQuery(SQL,"UPDATE " + pp.darTablaEmpresa() + " SET direccion=?");
			q.setParameters(direccion);
			return (long) q.executeUnique();
		}
		
		public Empresa darEmpresaPorNit(PersistenceManager pm, String nit)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM " + pp.darTablaEmpresa() + " WHERE nit= ?");
			q.setResultClass(Empresa.class);
			q.setParameters(nit);
			return (Empresa) q.executeUnique();
		}
}
