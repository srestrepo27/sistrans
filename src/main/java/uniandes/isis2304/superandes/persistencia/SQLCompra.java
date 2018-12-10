package uniandes.isis2304.superandes.persistencia;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Compra;
import uniandes.isis2304.superandes.negocio.Proveedor;

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

	public long adicionarCompra(PersistenceManager pm, String productoCodigo,long clienteId, String factura,double total, Timestamp fecha,long id )
	{
		Query q= pm.newQuery(SQL, "SET AUTOCOMMIT 0 INSERT INTO " + pp.darTablaCompra() + " (productoCodigo,clienteId,factura,total) values (?,?,?,?)");
		q.setParameters(productoCodigo,clienteId,factura,total,fecha,id);
		return (long) q.executeUnique();
	}

	public long eliminarCompra(PersistenceManager pm, String id )
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 DELET FROM " + pp.darTablaCompra()+ " WHERE productoCodigo= ? AND clienteId=?)" );
		q.setParameters( id);
		return (long) q.executeUnique();

	}
	public Compra darCompra(PersistenceManager pm, String id)
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 SELECT * FROM " +pp.darTablaCompra()+" WHERE factura=?");
		q.setParameters(id);
		q.setResultClass(Compra.class);
		return(Compra)q.executeUnique();
	}
	public long eliminarCompraPorClienteId(PersistenceManager pm, long clienteId)
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 DELET FROM " + pp.darTablaCompra()+ " WHERE clienteId=?)" );
		q.setParameters(clienteId);
		return (long) q.executeUnique();	
	}

	public List<Compra> darCompras(PersistenceManager pm)
	{
		Query q= pm.newQuery(SQL," SET AUTOCOMMIT 0 SELECT * FROM " + pp.darTablaCompra());
		q.setResultClass(Compra.class);
		return (List<Compra>) q.execute();
	}

	public List<Compra> darComprasProducto(PersistenceManager pm, String productoCodigo)
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 SELECT * FROM " + pp.darTablaCompra()+ " WHERE productoCodigo= ?)");
		q.setParameters(productoCodigo);
		q.setResultClass(Compra.class);
		return (List<Compra>) q.execute();
	}

	public List<Compra> darComprasCliente(PersistenceManager pm, long clienteId)
	{
		Query q= pm.newQuery(SQL,"SET AUTOCOMMIT 0 SELECT * FROM " + pp.darTablaCompra()+  " WHERE clienteId= ?)");
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

	public double RFC1(PersistenceManager pm, String fechai, String fechaf  )

	{
		String sql = "SELECT SUM ( total ) FROM "+ pp.darTablaCompra()+ " WHERE fecha BETWEEN ? AND ? "; 
		Query q= pm.newQuery(SQL,sql);
		q.setParameters(fechai, fechaf );
		return  (double) q.executeUnique();
	}


	public List<String> RFC8(PersistenceManager pm)
	{
		String sql= "SELECT idCliente FROM " + pp.darTablaCompra()+ " WHERE ( SELECT idCliente, count(clienteId) FROM " + pp.darTablaCompra()+ " WHERE EXTRACT(MONTH FROM fecha) GROUPBY idCliente >= 2)" ;
		Query q= pm.newQuery(SQL,sql);
		q.setResultClass(String.class);

		return (List<String>) q.executeList();

	}
	public List<String> RFC7(PersistenceManager pm, String producto)
	{
		String sql= "SELECT cliente, count (cliente) FROM " + pp.darTablaContiene()+" inner join "+pp.darTablaCompra() +" on "+ pp.darTablaContiene()+"." ;
		Query q= pm.newQuery(SQL,sql);
		q.setResultClass(String.class);

		return (List<String>) q.executeList();

	}
	public List<String> RFC9(PersistenceManager pm)
	{
		String sql="SELECT nombre FROM ( SELECT Producto_CodigoDeBarras FROM (((SELECT Proveedor_nit from "+ pp.darTablaPedido()+" WHERE EXTRACT(month from fecha)>2) natural join (SELECT nit as Proveedor_nit FROM " + pp.darTablaProveedor()+" )) NATURAL JOIN (select proveedor_nit, Producto_CodigoDeBarras FROM "+pp.darTablaProvee()+" )) INNER JOIN (SELECT codigoDeBarras, nombre from "+pp.darTablaProducto()+") ON Producto_CodigoDeBarras= codigoDeBarras)";
		Query q= pm.newQuery(SQL,sql) ;
		q.setResultClass(String.class);

		return (List<String>) q.executeList();
	}
	public List<Cliente> RFC10Admin(PersistenceManager pm,String producto, String fecha1, String fecha2, String criterio)
	{
		Query q= pm.newQuery(SQL,"SELECT * FROM A_COMPRA WHERE PRODUCTOCODIGO ==? AND fecha BETWEEN ? AND ? AND A_CLIENTE.CODIGO= A_COMPRA.CLIENTEID " );
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
		q.setParameters(producto,date1,date2);
		q.setResultClass(Compra.class);
		return(List<Cliente>) q.execute();
	}
	public List<Cliente> RFC10Usuario(PersistenceManager pm,String producto, String fecha1, String fecha2, String criterio)
	{
		Query q= pm.newQuery(SQL,"SELECT * FROM A_COMPRA WHERE PRODUCTOCODIGO ==? AND fecha BETWEEN ? AND ?AND AND A_CLIENTE.CODIGO= A_COMPRA.CLIENTEID " );
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
		q.setParameters(producto,date1,date2);
		q.setResultClass(Compra.class);
		return(List<Cliente>) q.execute();
	}
	public List<Compra> RFC11(PersistenceManager pm, String fecha1, String fecha2)
	{
		Query q= pm.newQuery(SQL,"SELECT * FROM A_CLIENTE, A_COMPRA WHERE FECHA <= ? AND FECHA > ?;" );
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
		q.setParameters(date1,date2);
		q.setResultClass(Compra.class);
		return(List<Compra>) q.execute();
	}
	public List<Compra> RFC121(PersistenceManager pm, String fecha1, String fecha2)
	{
		Query q= pm.newQuery(SQL,"SELECT * FROM A_CLIENTE, A_COMPRA WHERE FECHA <= ? AND FECHA > ? ; SELECT * FROM ( ( SELECT PRODUCTOCODIGO , COUNT ( PRODUCTOCODIGO ) FROM A_COMPRAGROUP BY PRODUCTOCODIGO ) NATURAL INNER JOIN (SELECT MIN ( CONT ) FROM ( SELECT PRODUCTOCODIGO , COUNT (PRODUCTOCODIGO) AS CONT FROM A_COMPRA GROUP BY PRODUCTOCODIGO) GROUP BY(CONT)));" );
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
		q.setParameters(date1,date2);
		q.setResultClass(Compra.class);
		return(List<Compra>) q.execute();
	}
	public List<Compra> RFC122(PersistenceManager pm, String fecha1, String fecha2)
	{
		Query q= pm.newQuery(SQL,"SELECT * FROM A_CLIENTE, A_COMPRA WHERE FECHA <= ? AND FECHA > ? ; SELECT * FROM ( ( SELECT PRODUCTOCODIGO , COUNT ( PRODUCTOCODIGO ) FROM A_COMPRAGROUP BY PRODUCTOCODIGO ) NATURAL INNER JOIN (SELECT MAX ( CONT ) FROM ( SELECT PRODUCTOCODIGO , COUNT (PRODUCTOCODIGO) AS CONT FROM A_COMPRA GROUP BY PRODUCTOCODIGO) GROUP BY(CONT))) ;" );
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
		q.setParameters(date1,date2);
		q.setResultClass(Compra.class);
		return(List<Compra>) q.execute();
	}



	public List<Proveedor> RFC123(PersistenceManager pm, String fecha1, String fecha2)
	{
		Query q= pm.newQuery(SQL, "SELECT * FROM ((SELECT PROVEEDORNIT, COUNT(PROVEEDORNIT) FROM A_PEDIDO GROUP BY PROVEEDORNIT)NATURAL INNER JOIN (SELECT Min(CONT)FROM (SELECT PROVEEDORNIT, COUNT (PROVEEDORNIT) AS CONT FROM A_PEDIDO GROUP BY PROVEEDORNIT) GROUP BY(CONT)))");
		q.setResultClass(Proveedor.class);
		return(List<Proveedor>) q.execute();
	}

	public List<Proveedor> RFC124(PersistenceManager pm, String fecha1, String fecha2)
	{
		Query q= pm.newQuery(SQL, "SELECT * FROM ((SELECT PROVEEDORNIT, COUNT(PROVEEDORNIT) FROM A_PEDIDO GROUP BY PROVEEDORNIT)NATURAL INNER JOIN (SELECT MAX(CONT)FROM (SELECT PROVEEDORNIT, COUNT (PROVEEDORNIT) AS CONT FROM A_PEDIDO GROUP BY PROVEEDORNIT) GROUP BY(CONT)))");
		q.setResultClass(Proveedor.class);
		return(List<Proveedor>) q.execute();
	}
	public List<String> RFC131(PersistenceManager pm)
	{
		Query q= pm.newQuery(SQL, "select distinct clienteid, extract (month from fecha ) ,count( * )from a_compra  group by  clienteid , extract (month from fecha ) having count (*)>=1");
	q.setResultClass(String.class);
	return (List<String>) q.execute();
	}
	
	public List<Compra> RFC132(PersistenceManager pm)
	{
		Query q= pm.newQuery(SQL, "SELECT * FROM A_CLIENTE, A_COMPRA WHERE CLIENTEID=CODIGO AND PRODUCTOCODIGO IN (SELECT PRODUCTOCODIGO FROM A_COMPRA, A_PRODUCTO WHERE PRODUCTOCODIGO=CODIGODEBARRAS AND PRECIOVENTA >50000)");
	q.setResultClass(Compra.class);
	return (List<Compra>) q.execute();
	}
	
	public List<Compra> RFC133(PersistenceManager pm)
	{
		Query q= pm.newQuery(SQL, "SELECT * FROM A_CLIENTE, A_COMPRA WHERE CLIENTEID=CODIGO AND PRODUCTOCODIGO IN (SELECT PRODUCTOCODIGO FROM A_COMPRA, A_PRODUCTO WHERE PRODUCTOCODIGO=CODIGODEBARRAS AND CATEGORIA ='HERRAMIENTAS' OR CATEGORIA='TECNOLOGIA') ");
	q.setResultClass(Compra.class);
	return (List<Compra>) q.execute();
	}
}
