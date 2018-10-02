package uniandes.isis2304.superandes.negocio;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Bebedor;
import uniandes.isis2304.superandes.persistencia.PersistenciaSuperAndes;
public class SuperAndes 
{
	private static Logger Log = Logger.getLogger(SuperAndes.class.getName());
	
	private PersistenciaSuperAndes ps;
	
	
	
	public SuperAndes ()
	{
		ps=PersistenciaSuperAndes.getInstance();
		
	}
	public SuperAndes (JsonObject tableCofig)
	{
		ps= PersistenciaSuperAndes.getInstance(tableCofig);
		
	}
	public void cerrarUnidadPersistencia ()
	{
		ps.cerrarUnidadPersistencia();
	}
	/* ****************************************************************
	 * 			Métodos para manejar las Bodegas
	 *****************************************************************/
	public Bodega adicionarBodega (long id, double peso, double volumen, String sucursal, String categoria)
	{
		Log.info("Adicionando Bodega "+ id);
		Bodega bodega = ps.adicionarBodega(id, peso, volumen, sucursal, categoria);
        Log.info ("Adicionando bodega: " + id);
        return bodega;
	}
	public long eliminarBodegaPorId (long id)
	{
        Log.info ("Eliminando bodega por id: " + id);
        long resp = ps.eliminarBodegaPorId(id);
        Log.info ("Eliminando bodega por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	public Bodega darBodegaPorId (long id)
	{
        Log.info ("Dar información de una bodega por id: " + id);
        Bodega bodega = ps.darBodega(id);
        Log.info ("Buscando bebedor por Id: " + bodega != null ? bodega : "NO EXISTE");
        return bodega ;
	}
	/* ****************************************************************
	 * 			Métodos para manejar los clientes 
	 *****************************************************************/
	public Cliente  adicionarCliente (long pCodigo, String pCorreo, String pNombre, int pPuntos, long id)
	{
		Log.info("Adicionando cliente "+ id);
		Cliente cliente = ps.adicionarcliente(pCodigo, pCorreo, pNombre, pPuntos, id);
		Log.info("Adicionando cliente "+ id);
        return cliente ;
	}
	public long eliminarClienteId (long id)
	{
        Log.info ("Eliminando cliente por id: " + id);
        long resp = ps.eliminarClienteId(id);
        Log.info ("Eliminando cliente por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	public long eliminarClientePorNombre (String nombre)
	{
	        Log.info ("Eliminando cliente por nombre: " + nombre);
	        long resp = ps.eliminarClientePorNombre(nombre);
	        Log.info ("Eliminando cliente por nombre: " + resp + " tuplas eliminadas");
	        return resp;
	}
	public Cliente darClietePorId (long id)
	{
        Log.info ("Dar información de un cliente por id: " + id);
        Cliente cliente = ps.darClienterPorId(id);
        Log.info ("Buscando cliente por Id: " + cliente != null ? cliente : "NO EXISTE");
        return cliente;
	}
	public List<Cliente> darClientes ()
	{
		Log.info ("Listando Clientes");
        List<Cliente> clientes = ps.darClientes ();	
        Log.info ("Listando clientes: " + clientes.size() + " clientes existentes");
        return clientes;	
     }
 

		

	
}

	
