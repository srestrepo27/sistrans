package uniandes.isis2304.superandes.persistencia;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;





public class PersistenciaSuperAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
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
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaSuperAndes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	private List <String> tablas;
	
	private SQLBodega sqlBodega;
	
	private SQLCliente sqlCliente;
	
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
	
	private SQLSuperMercado sqlSuperMercado;
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlBodega= new SQLBodega(this);
		sqlCliente= new SQLCliente(this);
		sqlCompra= new SQLCompra(this);
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
		sqlSuperMercado = new  SQLSuperMercado(this);
	}

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
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
		tablas.add ("SUCURSAL");
		
		tablas.add ("PROMOCION_SUCURSAL");
		tablas.add ("ESTANTE");
		tablas.add ("BODEGA");
		tablas.add ("PRODUCTO");
		tablas.add ("COMPRA");
		tablas.add ("PROVEE");
		tablas.add ("PROMOCION_PROVEEDOR");
	}
	
	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
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
	 * @return Retorna el único objeto PersistenciaSuperAndes existente - Patrón SINGLETON
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
	 * @return Retorna el único objeto PersistenciaSuperAndes existente - Patrón SINGLETON
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
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	public String darSeqSuperAndes()
	{
		return tablas.get(0);
	}
	
	public String darTablaSuperMercado()
	{
		return tablas.get(1);
	}
	
	public String darTablaCliente()
	{
		return tablas.get(2);
	}

	public String darTablaPersona()
	{
		return tablas.get(3);
	}
	
	public String darTablaEmpresa()
	{
		return tablas.get(4);
	}
	
	public String darTablaProveedor()
	{
		return tablas.get(5);
	}
	
	public String darTablaPedido()
	{
		return tablas.get(6);
	}
	
	public String darTablaSucursal()
	{
		return tablas.get(7);
	}
	
	public String darTablaPromocionSucursal()
	{
		return tablas.get(8);
	}
	public String darTablaEstante()
	{
		return tablas.get(9);
	}
	public String darTablaBodega()
	{
		return tablas.get(10);
	}
	public String darTablaProducto()
	{
		return tablas.get(11);
	}
	public String darTablaCompra()
	{
		return tablas.get(12);
	}
	public String darTablaProvee()
	{
		return tablas.get(13);
	}
	
	public String darTablaPromocionProveedor()
	{
		return tablas.get(14);
	}
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
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
}
