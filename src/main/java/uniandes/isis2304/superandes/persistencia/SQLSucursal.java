package uniandes.isis2304.superandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Sucursal;

class SQLSucursal 
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
		public SQLSucursal(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarSucursal(PersistenceManager pm,  String nombre,String direccion,String ciudad)
		{
			Query q= pm.newQuery(SQL, "INSERT INTO" + pp.darTablaSucursal() + "(nombre,direccion,ciudad) values (?,?,?)");
			q.setParameters(nombre,direccion,ciudad);
			return (long) q.executeUnique();
		}
		public long eliminarSucursalPorNombre(PersistenceManager pm,  String nombre)
		{
			Query q= pm.newQuery(SQL, "DELETE FROM" + pp.darTablaSucursal() + "WHERE nombre=?");
			q.setParameters(nombre);
			return (long) q.executeUnique();
		}
		
		public long eliminarSucursalPorNombreCiudad(PersistenceManager pm,  String nombre,String ciudad)
		{
			Query q= pm.newQuery(SQL, "DELETE FROM" + pp.darTablaSucursal() + "WHERE nombre=? AND ciudad=?");
			q.setParameters(nombre,ciudad);
			return (long) q.executeUnique();
		}
		
		public Sucursal obtenerSucursalPorNombre(PersistenceManager pm,  String nombre)
		{
			Query q= pm.newQuery(SQL, "SELECT * FROM" + pp.darTablaSucursal() + "WHERE nombre=?");
			q.setResultClass(Sucursal.class);
			q.setParameters(nombre);
			return (Sucursal) q.executeUnique();
		}
		
		public List<Sucursal> obtenerSucursalesPorCiudad(PersistenceManager pm,  String ciudad)
		{
			Query q= pm.newQuery(SQL, "SELECT * FROM" + pp.darTablaSucursal() + "WHERE ciudad=?");
			q.setResultClass(Sucursal.class);
			q.setParameters(ciudad);
			return (List<Sucursal>) q.executeUnique();
		}
		
		public long cambiarDireccionSucursal(PersistenceManager pm,  String nombre, String direccion)
		{
			Query q= pm.newQuery(SQL, "UPDATE" + pp.darTablaSucursal() + "SET direccion =? WHERE nombre=?");
			q.setParameters(direccion,nombre);
			return (long) q.executeUnique();
		}
}
