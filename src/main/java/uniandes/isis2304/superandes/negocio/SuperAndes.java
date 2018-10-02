package uniandes.isis2304.superandes.negocio;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

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
}

	
