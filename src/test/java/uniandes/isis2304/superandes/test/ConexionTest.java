package uniandes.isis2304.superandes.test;

import static org.junit.Assert.fail;

import java.io.FileReader;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.superandes.negocio.SuperAndes;



public class ConexionTest
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(ConexionTest.class.getName());
	
	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD tambi�n
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 
	
	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia existe, pero el esquema de la BD no se ha creado
	 */
	private static final String CONFIG_TABLAS_B = "./src/main/resources/config/TablasBD_B.json"; 
	
	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia NO existe
	 */
	private static final String CONFIG_TABLAS_ERR_DS = "./src/main/resources/config/TablasBD_ErrorDataStore.json"; 
	
	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia NO existe
	 */
	private static final String CONFIG_TABLAS_ERR_USER = "./src/main/resources/config/TablasBD_ErrorInvalidUser.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
	/**
	 * La clase que se quiere probar
	 */
    private SuperAndes superAndes;
	
	/* ****************************************************************
	 * 			M�todos de prueba de acceso a la BD
	 *****************************************************************/  
    /**
     * M�todo de prueba para acceso correcto a una base de datos
     */
    @Test
    public void normalAccessTest ()
  	{
  	  	try
		{
			log.info ("Probando el acceso a la base de datos con datos v�lidos (BD, credenciales, esquema");
			superAndes = new SuperAndes (openConfig (CONFIG_TABLAS_A));
			log.info ("Conexi�n realizada correstamente");
			log.info ("Cerrando la conexi�n");
			
			superAndes.cerrarUnidadPersistencia ();
			log.info ("Conexi�n cerrada");
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de acceso normal FALL� !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de acceso normal a la base de datos fall� !! Revise persistence.xml.\n";
			msg += "Revise el log de superAndes y el de datanucleus para conocer el detalle de la excepci�n";
//			System.out.println (msg);
			fail (msg);
		}
  	}
  	  
    /**
     * M�todo que prueba el intento de acceso a una base de datos inaccesible, por alguna de las siguientes causas:
     * 1. No existe la unidad de persistencia
     * 2. La unidad de persistencia est� caida
     */
    @Test
    public void baseDatosInaccesible ()
    {
		try
		{
	    	log.info ("Probando el acceso a la base de datos con una base de datos que no existe");
			superAndes = new SuperAndes (openConfig (CONFIG_TABLAS_ERR_DS));
			fail ("Deber�a fallar. La base de datos no existe !!");
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba realizada exitosamente. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de base de datos inaccesible correcta.\n";
			msg += "Revise el log de superAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
		}
    }
    
    /**
     * M�todo que prueba el intento de acceso a una base de datos inaccesible, por causa:
     * 1. Credenciales de usuario inv�lidas (nombre de usuario / contrase�a)
     */
    @Test
    public void usuarioInvalidoTest ()
    {
		try
		{
	    	log.info ("Probando el acceso a la base de datos con datos de usuario incorrectos");
			superAndes = new SuperAndes (openConfig (CONFIG_TABLAS_ERR_USER));
			fail ("Deber�a fallar. Las credenciales del usuario no son v�lidas");
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba realizada exitosamente. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de credenciales incorrectas correcta.\n";
			msg += "Revise el log de superAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
		}
    }

    /**
     * M�todo que prueba el intento de acceso a una base de datos inaccesible, por causa:
     * 1. El esquema no ha sido creado o es err�neo - Intentar acceder a una tabla inexistente
     */
    @Test
    public void tablaInexistenteTest ()
    {
    	// Probar primero la conexi�n a la base de datos
		try
		{
	    	log.info ("Probando el acceso a la base de datos con datos de usuario correctos, pero sin crear el esquema");
			superAndes = new SuperAndes (openConfig (CONFIG_TABLAS_B));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de tabla inexistente incompleta. No se pudo conectar a la base de datos !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de tabla inexistente incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de superAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se puede probar si la tabla existe o no...
		try
		{
			superAndes.darClientes();
			fail ("Deber�a fallar. La tabla consultada no existe en la BD");
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de tabla inexistente correcta. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de tabla inexistente correcta.\n";
			msg += "Revise el log de superAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
		}
		finally
		{
			superAndes.limpiarsuperAndes ();
    		superAndes.cerrarUnidadPersistencia ();    		
		}
    }

	/* ****************************************************************
	 * 			M�todos de configuraci�n
	 *****************************************************************/
    /**
     * Lee datos de configuraci�n para la aplicaci�n, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuraci�n deseada
     * @param archConfig - Archivo Json que contiene la configuraci�n
     * @return Un objeto JSON con la configuraci�n del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontr� un archivo de configuraci�n de tablas v�lido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontr� un archivo de configuraci�n v�lido");			
			JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de tablas v�lido: ", "ClienteTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
