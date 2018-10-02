package uniandes.isis2304.superandes.persistencia;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Compra;

class SQLCompra 
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
		public SQLCompra(PersistenciaSuperAndes pp)
		{
			this.pp = pp;
		}
		
		public long adicionarCompra(PersistenceManager pm, String productoCodigo,long clienteId, String factura,double total)
		{
			Query q= pm.newQuery(SQL, "INSERT INTO" + pp.darTablaCompra() + "(productoCodigo,clienteId,factura,total) values (?,?,?,?)");
			q.setParameters(productoCodigo,clienteId,factura,total);
			return (long) q.executeUnique();
		}
		
		public long eliminarCompra(PersistenceManager pm, String productoCodigo,long clienteId )
		{
			Query q= pm.newQuery(SQL,"DELET FROM" + pp.darTablaCompra()+ "WHERE productoCodigo= ? AND clienteId=?)" );
			q.setParameters(productoCodigo,clienteId);
			return (long) q.executeUnique();
		
		}
		public Compra darCompra(PersistenceManager pm, String factura)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM" +pp.darTablaCompra()+"WHERE factura=?");
			q.setParameters(factura);
			q.setResultClass(Compra.class);
			return(Compra)q.executeUnique();
		}
		public long eliminarCompraPorClienteId(PersistenceManager pm, long clienteId)
		{
			Query q= pm.newQuery(SQL,"DELET FROM" + pp.darTablaCompra()+ "WHERE clienteId=?)" );
			q.setParameters(clienteId);
			return (long) q.executeUnique();	
		}
		
		public List<Compra> darCompras(PersistenceManager pm)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM" + pp.darTablaCompra());
			q.setResultClass(Compra.class);
			return (List<Compra>) q.execute();
		}
		
		public List<Compra> darComprasProducto(PersistenceManager pm, String productoCodigo)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM" + pp.darTablaCompra()+ "WHERE productoCodigo= ?)");
			q.setParameters(productoCodigo);
			q.setResultClass(Compra.class);
			return (List<Compra>) q.execute();
		}
		
		public List<Compra> darComprasCliente(PersistenceManager pm, long clienteId)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM" + pp.darTablaCompra()+ "WHERE clienteId= ?)");
			q.setParameters(clienteId);
			q.setResultClass(Compra.class);
			return (List<Compra>) q.execute();
		}
		
		public List<Compra> RFC6(PersistenceManager pm,long cedula, String fecha1, String fecha2)
		{
			Query q= pm.newQuery(SQL,"SELECT * FROM COMPRA WHERE clienteId=? AND fecha BETWEEN ? AND ? ");
			SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd"); 
			Date date1=null;
			try {
				 date1 = (Date) dt.parse(fecha1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			Date date2=null;
			try {
				 date2 = (Date) dt.parse(fecha2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			q.setParameters(cedula,date1,date2);
			q.setResultClass(Compra.class);
			return(List<Compra>) q.execute();
		}
		
}
