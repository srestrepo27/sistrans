package uniandes.isis2304.superandes.persistencia;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.superandes.negocio.Bodega;
import uniandes.isis2304.superandes.negocio.Carrito;
import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Compra;
import uniandes.isis2304.superandes.negocio.Empresa;
import uniandes.isis2304.superandes.negocio.Estante;
import uniandes.isis2304.superandes.negocio.Pedido;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.Provee;
import uniandes.isis2304.superandes.negocio.Proveedor;
import uniandes.isis2304.superandes.negocio.Sucursal;





public class PersistenciaSuperAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(PersistenciaSuperAndes.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";


	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el �nico objeto de la clase - Patr�n SINGLETON
	 */
	private static PersistenciaSuperAndes instance;

	/**
	 * F�brica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	private List <String> tablas;

	private SQLUtil sqlUtil;

	private SQLBodega sqlBodega;

	private SQLCliente sqlCliente;

	private SQLCarrito sqlCarrito;

	private SQLCompra sqlCompra;

	private SQLEmpresa sqlEmpresa;

	private SQLEstante sqlEstante;

	private SQLPedido sqlPedido;

	private SQLPersona sqlPersona;

	private SQLProducto sqlProducto;

	private SQLPromocionProveedor sqlPromocionProveedor;

	private SQLPromocionSucursal sqlPromocionSucursal;

	private SQLProvee sqlProvee;

	private SQLProveedor sqlProveedor;

	private SQLSucursal sqlSucursal;

	private SQLContiene sqlContiene;


	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlBodega= new SQLBodega(this);
		sqlCliente= new SQLCliente(this);
		sqlCompra= new SQLCompra(this);
		sqlCarrito= new SQLCarrito(this);
		sqlContiene= new SQLContiene(this);
		sqlEmpresa= new SQLEmpresa(this);
		sqlEstante= new SQLEstante(this);
		sqlPedido= new SQLPedido(this);
		sqlPersona= new SQLPersona(this);
		sqlProducto = new SQLProducto(this);
		sqlPromocionProveedor= new SQLPromocionProveedor(this);
		sqlPromocionSucursal= new SQLPromocionSucursal(this);
		sqlProvee= new SQLProvee(this);
		sqlProveedor= new SQLProveedor(this);
		sqlSucursal = new SQLSucursal(this);
	}

	/**
	 * Constructor privado con valores por defecto - Patr�n SINGLETON
	 */
	private PersistenciaSuperAndes()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("SuperAndes");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("SUPERANDES_SEQUENCE");
		tablas.add ("SUPERMERCADO");
		tablas.add ("CLIENTE");
		tablas.add ("PERSONA");
		tablas.add ("EMPRESA");
		tablas.add ("PROVEEDOR");
		tablas.add ("PEDIDO");
		tablas.add ("A_SUCURSAL");
		tablas.add ("PROMOCION_SUCURSAL");
		tablas.add ("ESTANTE");
		tablas.add ("BODEGA");
		tablas.add ("PRODUCTO");
		tablas.add ("COMPRA");
		tablas.add ("PROVEE");
		tablas.add ("PROMOCION_PROVEEDOR");
		tablas.add("CARRITO");
		tablas.add("CONTIENE");
	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patr�n SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaSuperAndes(JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * @return Retorna el �nico objeto PersistenciaSuperAndes existente - Patr�n SINGLETON
	 */
	public static PersistenciaSuperAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperAndes ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el �nico objeto PersistenciaSuperAndes existente - Patr�n SINGLETON
	 */
	public static PersistenciaSuperAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaSuperAndes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexi�n con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	public String darSeqSuperAndes()
	{
		return "SUPERANDES_SEQUENCE";
	}

	public String darTablaSuperMercado()
	{
		return "A_SUPERMERCADO";
	}

	public String darTablaCliente()
	{
		return "A_CLIENTE";
	}

	public String darTablaPersona()
	{
		return "A_PERSONA";
	}

	public String darTablaEmpresa()
	{
		return "A_EMPRESA";
	}

	public String darTablaProveedor()
	{
		return "A_PROVEEDOR";
	}

	public String darTablaPedido()
	{
		return "A_PEDIDO";
	}

	public String darTablaSucursal()
	{
		return "A_SUCURSAL";
	}

	public String darTablaPromocionSucursal()
	{
		return "A_PROMOCION_SUCURSAL";
	}
	public String darTablaEstante()
	{
		return "A_ESTANTES";
	}
	public String darTablaBodega()
	{
		return "A_BODEGA";
	}
	public String darTablaProducto()
	{
		return "A_PRODUCTO";
	}
	public String darTablaCompra()
	{
		return "A_COMPRA";
	}
	public String darTablaProvee()
	{
		return "A_PROVEEDOR";
	}

	public String darTablaPromocionProveedor()
	{
		return "A_PROMOCION_PROVEEDOR";
	}
	public String darTablaCarrito()
	{
		return "a_carrito";
	}

	public String darTablaContiene()
	{
		return "A_CONTIENE";
	}
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle espec�fico del problema encontrado
	 * @param e - La excepci�n que ocurrio
	 * @return El mensaje de la excepci�n JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}


	/* ****************************************************************
	 * 			M�todos para manejar las BODEGAS
	 *****************************************************************/
	public Bodega adicionarBodega(long id, double peso,
			double volumen,String sucursal,String categoria) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idBodega = nextval ();
			long tuplasInsertadas = sqlBodega.adicionarBodega(pm, idBodega, peso, volumen, sucursal, categoria);
			tx.commit();

			log.trace ("Inserci�n bodega: " + idBodega + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Bodega(idBodega, peso, volumen, sucursal, categoria);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public long eliminarBodegaPorId (long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBodega.eliminarBodega(pm, idBebida);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Bodega darBodega (long id)
	{
		return (Bodega) sqlBodega.darBodegaPorId(pmf.getPersistenceManager(), id);
	}
	public List<Bodega> darBodegas ()
	{
		return sqlBodega.darBodegas (pmf.getPersistenceManager());
	}




	/* ****************************************************************
	 * 			REQUERIMIENTOS FUNCIONALES DE CONSULTA
	 *****************************************************************/

	public String ocupacionEstantes()
	{

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		String ret="";
		try
		{
			tx.begin();  
			Query q= pm.newQuery(SQL,"SELECT count(estantesId) FROM PRODUCTO GROUPBY estantesId");
			q.setResultClass(String.class);
			ret= (String) q.execute();
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}

	public String ocupacionBodegas()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		String ret="";
		try
		{
			tx.begin(); 
			Query q= pm.newQuery(SQL,"SELECT count(bodegasId) FROM PRODUCTO GROUPBY bodegasId");
			q.setResultClass(Integer.class);
			ret= (String) q.execute();
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}

	public List<Pedido> RFC5()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Pedido> ret= new ArrayList<Pedido>();
		try
		{
			tx.begin();  

			ret =sqlPedido.RFC5(pm);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}

	public List<Compra> RFC6(long cedula, String fecha1, String fecha2)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Compra> ret= new ArrayList<Compra>();
		try
		{
			tx.begin();  
			ret=sqlCompra.RFC6(pm, cedula, fecha1, fecha2);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}

	public double RFC1(String fechai, String fechaf )
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		double ret=0;
		try
		{
			tx.begin();  
			ret=sqlCompra.RFC1(pm, fechai, fechaf);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}
	public List<String> RFC8()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<String> ret = null;
		try
		{
			tx.begin();  
			ret= sqlCompra.RFC8(pm);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}

	public List<String> RFC7(String producto)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<String> ret = null;
		try
		{
			tx.begin();  
			ret= sqlCompra.RFC7(pm,producto);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}
	public List<String> RFC9()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<String> ret = null;
		try
		{
			tx.begin();  
			ret= sqlCompra.RFC9(pm);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}

	//---------------------------------------------------------------
	//REQUERIMIENTOS TODO
		//-------------------------------------------------
	
	public List<Cliente> RFC10Admin(String producto, String fecha1, String fecha2, String criterio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Cliente> ret= new ArrayList<Cliente>();
		try
		{
			tx.begin();  
			ret= sqlCompra.RFC10Admin(pm, producto, fecha1, fecha2, criterio);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}
	
	public List<Cliente> RFC10Gerente(String producto, String fecha1, String fecha2, String criterio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Cliente> ret= new ArrayList<Cliente>();
		try
		{
			tx.begin();  
			ret= sqlCompra.RFC10Usuario(pm, producto, fecha1, fecha2, criterio);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}
	
	
	public List<Compra> RFC11(String fecha1, String fecha2)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Compra> ret= new ArrayList<Compra>();
		try
		{
			tx.begin();  
			ret= sqlCompra.RFC11(pm, fecha1, fecha2);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}
	public List<Compra> RFC121( String fecha1, String fecha2)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Compra> ret= new ArrayList<Compra>();
		try
		{
			tx.begin();  
			ret= sqlCompra.RFC121(pm, fecha1, fecha2);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}
	
	public List<Compra> RFC122( String fecha1, String fecha2)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Compra> ret= new ArrayList<Compra>();
		try
		{
			tx.begin();  
			ret= sqlCompra.RFC122(pm, fecha1, fecha2);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}
	public List<Proveedor> RFC123( String fecha1, String fecha2)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Proveedor> ret= new ArrayList<Proveedor>();
		try
		{
			tx.begin();  
			ret= sqlCompra.RFC123(pm, fecha1, fecha2);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}
	
	public List<Proveedor> RFC124( String fecha1, String fecha2)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Proveedor> ret= new ArrayList<Proveedor>();
		try
		{
			tx.begin();  
			ret= sqlCompra.RFC124(pm, fecha1, fecha2);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}
	public List<String> RFC131()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<String> ret= new ArrayList<String>();

		try
		{
			tx.begin(); 
			ret= sqlCompra.RFC131(pm);

		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;

	}
	public List<Compra> RFC132()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Compra> ret= new ArrayList<Compra>();

		try
		{
			tx.begin(); 
			ret= sqlCompra.RFC132(pm);

		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;

	}
	
	public List<Compra> RFC133()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Compra> ret= new ArrayList<Compra>();

		try
		{
			tx.begin(); 
			ret= sqlCompra.RFC133(pm);

		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return ret;

	}
	/* ****************************************************************
	 * 			METODOS AUTOMATICOS
	 *****************************************************************/
	public long realizarPedido( String codigoDeBarras, String nombreSucursal)
	{
		long ret=0;

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{

			tx.begin();  

			int nivel= sqlProducto.darNivelReoProducto(pm, codigoDeBarras);

			int cantidad= sqlProducto.darCantidadProducto(pm, codigoDeBarras);

			if(cantidad<nivel)
			{
				Provee provee= sqlProvee.obtenerProveeProducto(pm, codigoDeBarras);
				String proveedor=provee.getProveedor();
				SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd"); 

				LocalDateTime now= LocalDateTime.now();
				now=now.plusDays(3);
				String f=dt.format(now);
				Date fecha=null;
				try {
					fecha = (Date) dt.parse(f);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				long id=nextval();
				List<Cliente> clientes= sqlCliente.darClientes(pm);

				ret=(long) sqlPedido.adicionarPedido(pm, id, fecha,proveedor, nombreSucursal);
				tx.commit();

				log.trace("Pedido realizado de [: "+ codigoDeBarras +"] tuplas insertadas:"+ ret );

			}
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
			return 0;
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

		return ret;
	}


	public void realizarVenta(List<Producto> productos, long cliente)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();  

			Timestamp t= new Timestamp(System.currentTimeMillis());
			int total=0;
			String factura="SUPER ANDES 2018" +"\n";
			LocalDateTime now= LocalDateTime.now();
			SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd"); 
			String f=dt.format(now);
			factura+=f+"\n";
			for(Producto p:productos)
			{
				total+=p.getPrecioVenta();
				sqlProducto.venderProducto(pm, p.getCodigoBarras());
				factura+=p.getNombre() + ".................."+ p.getPrecioVenta()+"\n";

			}
			factura+="TOTAL:.................."+ "$" +total;

			long tuplas=0;
			for(Producto p:productos)
			{
				long id=nextval();
				tuplas=sqlCompra.adicionarCompra(pm, p.getCodigoBarras(), cliente, factura, total,t,id );
			}

			tx.commit();
			log.trace("Venta realizada: "+productos.size()+ cliente + "tuplas insertadas:"+ tuplas);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public List<Object[]> RFC2()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Object[]> lista = null;
		try
		{
			tx.begin(); 
			lista= sqlPromocionSucursal.RFC2(pm);

		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return lista;
	}

	public void devolverProductosPorAbandono(long carritoId)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin(); 
			sqlContiene.devolverTodoPorCarritoAbandonado(pm, carritoId);
		}
		catch(Exception e)
		{
			log.error("Exception: "+ e.getMessage()+ "\n"+ darDetalleException(e));
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	/* ****************************************************************
	 * 			M�todos para manejar los clientes 
	 *****************************************************************/
	public Cliente adicionarcliente ( String pCorreo, String pNombre, int pPuntos,  String nombreSucursal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idcliente = nextval ();
			long tuplasInsertadas = sqlCliente.adicionarCliente(pm,idcliente, pNombre, pCorreo, pPuntos, nombreSucursal);
			tx.commit();

			log.trace ("Inserci�n del cliente: " + idcliente + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Cliente(idcliente, pCorreo, pNombre, pPuntos, nombreSucursal);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public long eliminarClienteId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCliente.eliminarClientePorId(pm, id);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarClientePorNombre(String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCliente.eliminarClientePorNombre(pm, nombre);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Cliente darClienterPorId (long id) 
	{
		return (Cliente) sqlCliente.darClientePorId(pmf.getPersistenceManager(), id);
	}
	public long cambiarCorreoCliente (long id, String correo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCliente.cambiarCorreoCliente(pm, id, correo);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}
	public List<Cliente> darClientes ()
	{
		return sqlCliente.darClientes (pmf.getPersistenceManager());
	}


	/* ****************************************************************
	 * 			M�todos para manejar los compra 
	 *****************************************************************/
	public Compra  adicionarCompra  (String productoCodigo,long clienteId, String factura,double total, Timestamp fecha )
	{	
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long id = nextval ();

			long tuplasInsertadas = sqlCompra.adicionarCompra(pm, productoCodigo, clienteId, factura, total, fecha, id);
			tx.commit();

			log.trace ("Inserci�n de compra: [" + clienteId + ", " + productoCodigo + id+ "]. " + tuplasInsertadas + " tuplas insertadas");

			return new  Compra(productoCodigo, clienteId, factura, total, fecha, id);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public List<Compra> darCompras ()
	{
		return sqlCompra.darCompras (pmf.getPersistenceManager());
	}
	public long eliminarCompraId (String id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCompra.eliminarCompra(pm, id);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/* ****************************************************************
	 * 			M�todos para manejar los proveedores 
	 *****************************************************************/

	public Proveedor adicionarProveedor (String pNombre, String pNit){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlProveedor.adicionarProveedor(pm, pNit, pNombre);
			tx.commit();

			log.trace ("Inserci�n de un proveedor: [" + pNit + ", " + pNombre + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Proveedor(pNombre, pNit);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	/* ****************************************************************
	 * 			M�todos para manejar los productos
	 *****************************************************************/


	public Producto adicionarProducto(String codigoBarras, String unidadMedida, String nombre, String marca, double precioVenta,
			double precioCompra, String presentacion, double precioXunidad, int cantidad, String empaque,
			boolean percedero, String categoria, int nivelReo, long estantesId, long bodegasId, long pedidoId) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlProducto.adicionarProducto(pm, codigoBarras, unidadMedida, nombre, marca, precioVenta, precioCompra, presentacion, precioXunidad, cantidad, empaque,percedero, nivelReo, estantesId, bodegasId, pedidoId);
			tx.commit();

			log.trace ("Inserci�n de producto: ["  +codigoBarras + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Producto(codigoBarras, unidadMedida, nombre, marca, precioVenta, precioCompra, presentacion, precioXunidad, cantidad, empaque, percedero, categoria, nivelReo, estantesId, bodegasId, pedidoId);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarProductoCodigo (String  id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlProducto.eliminarProducto(pm, id);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Producto> darProductoPorNombre(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<Producto> list=null;
		try
		{
			tx.begin(); 
			list= sqlProducto.darProductosPorNombre(pm, nombre);
			tx.commit();

		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		return list;
	}
	/* ****************************************************************
	 * 			M�todos para manejar las sucursales
	 *****************************************************************/
	public Sucursal adicionarSucursal (  String nombre,String direccion,String ciudad)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlSucursal.adicionarSucursal(pm, nombre, direccion, ciudad);
			tx.commit();

			log.trace ("Inserci�n de gustan: [" + nombre + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Sucursal(nombre, direccion, ciudad);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Sucursal obtenerSucursalPorNombre(String nombre)
	{
		PersistenceManager pm= pmf.getPersistenceManager();
		Transaction tx= pm.currentTransaction();
		try
		{
			tx.begin();
			Sucursal resp= sqlSucursal.obtenerSucursalPorNombre(pm, nombre);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			System.out.println("nohay");
			return null;	
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public void terminarCompra(long carritoId,long clienteId)
	{
		PersistenceManager pm= pmf.getPersistenceManager();
		Transaction tx= pm.currentTransaction();
		try
		{
			sqlContiene.terminarCompra(pm, carritoId); 
			sqlCarrito.devolverCarrito(pm, clienteId);
		}
		catch(Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/* ****************************************************************
	 * 			M�todos para manejar los estates 
	 *****************************************************************/

	public Estante adicionarEstante ( double peso, double volumen, String sucursal, String categoria)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long id = nextval ();
			long tuplasInsertadas = sqlEstante.adicionarEstante(pm, id, peso, volumen, sucursal, categoria);
			tx.commit();

			log.trace ("Inserci�n de estante: [" + id + ", " + sucursal + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Estante(id, peso, volumen, sucursal, categoria);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/* ****************************************************************
	 * 			M�todos para manejar los empresa
	 *****************************************************************/


	public Empresa adicionarEmpresa ( String nit ,String  direccion, long clientesCodigo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			long tuplasInsertadas = sqlEmpresa.adicionarEmpresa(pm,nit , direccion, clientesCodigo);
			tx.commit();

			log.trace ("Inserci�n de estante: [" + nit + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Empresa(nit, direccion, clientesCodigo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/* ****************************************************************
	 * 			M�todos para manejar los pedidos 
	 *****************************************************************/
	public Pedido adicionarPedido (Date fecha , String proveedorNit, String nombreSucursal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long id = nextval ();
			long tuplasInsertadas = sqlPedido.adicionarPedido(pm, id, fecha, proveedorNit, nombreSucursal);
			tx.commit();

			log.trace ("Inserci�n de pedido: [" + id + "]. " + tuplasInsertadas + " tuplas insertadas");
			String fe =  fecha.toString(); 
			return new Pedido(id, fe, proveedorNit, nombreSucursal);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/* ****************************************************************
	 * 			M�todos para manejar las personas 
	 *****************************************************************/

	/* ****************************************************************
	 * 			M�todos para manejar los producos 
	 *****************************************************************/


	/* ****************************************************************
	 * 			M�todos para manejar los promocion proveedores 
	 *****************************************************************/

	/* ****************************************************************
	 * 			M�todos para manejar los promocion sucursal 
	 *****************************************************************/

	/* ****************************************************************
	 * 			M�todos para manejar la asociacion provee
	 *****************************************************************/

	/* ****************************************************************
	 * 			M�todos para manejar los carritos
	 *  
	 *****************************************************************/
	public long adicionarProductoAlCarrito(long carritoId, String codigoProducto)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin(); 
			long resp= sqlContiene.adicionarProductoAlCarrito(pm, carritoId, codigoProducto);
			
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return (Long) null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		
	}
	
	public long devolverProductoDelCarrito(long carritoId, String codigoProducto)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin(); 
			long resp= sqlContiene.devolverProducto(pm, codigoProducto, carritoId);
			
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return (Long) null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	public String  asignarCarrito( long idCliente)
	{
		System.out.println("persi");
		PersistenceManager pm = pmf.getPersistenceManager();
		System.out.println("1");
		Transaction tx=pm.currentTransaction();
		System.out.println("2");
		int cantCarritos= sqlCarrito.darCarritosLibres(pm).size();
		System.out.println("3");
		// no se si se va al cath cuando elegido no existe 
		try
		{
			
			System.out.println("4");
			tx.begin(); 
			System.out.println("5");
			String elegido= darCarritosLibres().get(0);
			System.out.println("6");
	
			System.out.println("7");
			long tuplasInsertadas = sqlCarrito.asignarClienteAlCarrito(pm, idCliente,Long.parseLong(elegido) );
			System.out.println("8");
			tx.commit();
			System.out.println("9");

			log.trace ("asignacion de carrito a cliente  : " + idCliente + ": " + tuplasInsertadas + " tuplas insertadas");
			return elegido;
		}
		catch (Exception e)
		
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));

			System.out.println("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}

		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public List<String> darCarritosLibres()
	{

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			System.out.println("5.1");
			
			System.out.println("5.2");
			List<String> ret= sqlCarrito.darCarritosLibres(pm);
			System.out.println("5.3");
			tx.commit();
			System.out.println("5.4");
			System.out.println(ret.size());
			return ret;
		}
		catch(Exception e)
		{
			System.out.println("5.55");
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}
	public long abandonarCarrito (long idCliente)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCarrito.devolverCarrito(pm, idCliente);
			tx.commit();


			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Producto> mostrarProductosEnElCarrito(long carritoId)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			List<Producto> resp= sqlContiene.darProductosEnElCarrito(pm, carritoId);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<String> darCarritosAbandonados()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			List<String> enContiene= sqlContiene.darCarritosEnContiene(pm);
			List<String> libres= sqlCarrito.darCarritosLibres(pm);
			List<String> abandonados= new ArrayList<String>();
			if(!libres.isEmpty() && !enContiene.isEmpty())
			{
				for(String s: enContiene)
				{
					for(String c: libres)
						
						
					{
						if(s.equals(c))
						{
							abandonados.add(s);
						}
					}
				}
			}
			tx.commit();
			return abandonados;
		}
		catch(Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}
	
	
	public void devolverTodoPorAbandono()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			List<String> lista= darCarritosAbandonados();
			if(lista!=null)
			{
				if(!lista.isEmpty())
				{
					for(String s: lista)
					{
						long carritoId= Long.parseLong(s);
						sqlContiene.devolverTodoPorCarritoAbandonado(pm, carritoId);
					}
				}
			}
			tx.commit();
		}
		catch(Exception e)
		{
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));	
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}
	/* ****************************************************************
	 * 			M�todos para manejar los supermercados
	 *  
	 *****************************************************************/



	public long [] limpiarSuperAndes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlUtil.limpiarSuperAndes(pm);
			tx.commit ();
			log.info ("Borrada la base de datos");
			return resp;
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1, -1, -1, -1, -1, -1};
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}












}
