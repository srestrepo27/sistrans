package uniandes.isis2304.superandes.persistencia;

import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Pedido;

class SQLPedido 
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
		public SQLPedido(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarPedido(PersistenceManager pm, long id, Date fecha, String proveedorNit, long superMercadoId)
		{
			Query q= pm.newQuery(SQL,"INSERT INTO "+ pp.darTablaPedido()+ " (id,fecha,proveedorNit,superMercadoId) values (?,?,?,?)");
			q.setParameters(id,fecha,proveedorNit,superMercadoId);
			
			return (long) q.executeUnique();
		}
		
		public long eliminarPedidoPorId(PersistenceManager pm, long id)
		{
			Query q= pm.newQuery(SQL,"DELETE FROM "+ pp.darTablaPedido()+ " WHERE id=?");
			q.setParameters(id);
			return (long) q.executeUnique();
		}
		
		public long eliminarPedidoProveedor(PersistenceManager pm, String proveedorNit)
		{
			Query q= pm.newQuery(SQL,"DELETE FROM "+ pp.darTablaPedido()+ " WHERE proveedorNit=?");
			q.setParameters(proveedorNit);
			return (long) q.executeUnique();
		}
		
		public List<Pedido> darPedidosSuperMercado(PersistenceManager pm, long superMercadoId)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM "+ pp.darTablaPedido()+ " WHERE superMercadoId=?");
			q.setParameters(superMercadoId);
			q.setResultClass(Pedido.class);
			return (List<Pedido>) q.executeUnique();
		}
		

		public List<Pedido> darPedidosProveedor(PersistenceManager pm, String proveedorNit)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM "+ pp.darTablaPedido()+ " WHERE proveedorNit=?");
			q.setParameters(proveedorNit);
			q.setResultClass(Pedido.class);
			return (List<Pedido>) q.executeUnique();
		} 
		
		public List<Pedido> RFC5(PersistenceManager pm)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM PEDIDO GROUP BY proveedorNit");
			q.setResultClass(Pedido.class);
			return(List<Pedido>) q.executeUnique();
		}
}
