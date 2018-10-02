package uniandes.isis2304.superandes.persistencia;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import uniandes.isis2304.parranderos.negocio.Bebedor;
import uniandes.isis2304.superandes.negocio.Bodega;
import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Compra;
import uniandes.isis2304.superandes.negocio.Pedido;
import uniandes.isis2304.superandes.negocio.Provee;





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
	public Bodega adicionarBodega(long id, double peso, double volumen,String sucursal,String categoria) 
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
	//REQUERIMIENTOS FUNCIONALES DE CONSULTA
	public int ocupacionEstantes(PersistenceManager pm)
	{
		//INCOMPLETO
		Query q= pm.newQuery(SQL,"SELECT count(estantesId) FROM PRODUCTO GROUPBY estantesId");
		q.setResultClass(Integer.class);
		int cantidad= (int) q.executeUnique();
		return cantidad;
	}
	public int ocupacionBodegas(PersistenceManager pm)
	{
		//INCOMPLETO
		Query q= pm.newQuery(SQL,"SELECT count(bodegasId) FROM PRODUCTO GROUPBY bodegasId");
		q.setResultClass(Integer.class);
		int cantidad= (int) q.executeUnique();
		return cantidad;
	}
	public List<Pedido> RFC5(PersistenceManager pm)
	{
	return sqlPedido.RFC5(pm);
	}
	public List<Compra> RFC6(PersistenceManager pm,long cedula, String fecha1, String fecha2)
	{
		return sqlCompra.RFC6(pm, cedula, fecha1, fecha2);
	}
	
	/* ****************************************************************
	 * 			M�todos para manejar los clientes 
	 *****************************************************************/
	public Cliente adicionarcliente ( String pCorreo, String pNombre, int pPuntos, long id)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();            
            long idcliente = nextval ();
            long tuplasInsertadas = sqlCliente.adicionarCliente(pm,idcliente, pNombre, pCorreo, pPuntos, id);
            tx.commit();
            
            log.trace ("Inserci�n del cliente: " + idcliente + ": " + tuplasInsertadas + " tuplas insertadas");
            return new Cliente(idcliente, pCorreo, pNombre, pPuntos, id);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 
	

}
