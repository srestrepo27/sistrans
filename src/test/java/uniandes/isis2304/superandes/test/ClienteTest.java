package uniandes.isis2304.superandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.SuperAndes;
import uniandes.isis2304.superandes.negocio.VOCliente;


public class ClienteTest 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Cliente.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    private SuperAndes superandes;
    
    @Test
  	public void CRDClienteTest() 
  	{
      	// Probar primero la conexión a la base de datos
  		try
  		{
  			log.info("Probando las operaciones sobre cliente");
  			superandes= new SuperAndes(openConfig(CONFIG_TABLAS_A));
  		}
  		catch(Exception e)
  		{
  			log.info("Prueba de CRD de cliente incompleta. No se pudo conectar a la base de datos. La excepcion generada es:_"+e.getClass().getName());
  			log.info("La causa es: "+ e.getCause().toString());
  			
  			String msg="Prueba de CRD de cliente incompleta. No se pudo conectar a la base de datos. \n";
  			msg+= "Revise el log de superAndes y el de datanucleus para conocer el detalle de la excepcion";
  			System.out.println(msg);
  			fail(msg);
  		}
  		
  		//operaciones
  		try
  		{
  			List<VOCliente> lista= superandes.darVOCLiente();
  			assertEquals("No debe haber clientes creados",0, lista.size());
  			
  			
  			//Lectura de los clientes con un cliente adicional
  			long codigo=1;
  			String nombre="John";
  			String correo="john@gmail.com";
  			int puntos=0;
  			String s="uniandes";
  			VOCliente cliente1= superandes.adicionarCliente(codigo, correo, nombre, puntos, s);
  			lista= superandes.darVOCLiente();
  			assertEquals("Debe haber un cliente creado",1,lista.size());
  			assertEquals("El objeto creado y el traido deben ser iguales",cliente1,lista.get(0));
  			
  			//Lectura de dos clientes
  			long codigo2=2;
  			String nombre2= "Juan";
  			String correo2= "juan@gmail.com";
  			VOCliente cliente2= superandes.adicionarCliente(codigo2, correo2, nombre2, puntos, s);
  			lista=superandes.darVOCLiente();
  			assertEquals("Debe haber dos clientes",2,lista.size());
  			assertTrue("El primer cliente adicionado debe existir",cliente1.equals(lista.get(0)) || cliente1.equals(lista.get(1)));
  			assertTrue("El segundo cliente adicionado debe existir",cliente2.equals(lista.get(0)) || cliente2.equals(lista.get(1)));
  			//Prueba de eliminacion de un cliente
  			
  			long tbEliminados= superandes.eliminarClienteId(codigo);
  			lista=superandes.darVOCLiente();
  			
  			assertEquals("Debe haberse eliminado un cliente",1,lista.size());
  			assertFalse("El primer cliente NO debe estar en la tabla ",cliente1.equals(lista.get(0)));
  			assertTrue("El segundo cliente debe estar en la tabla",cliente2.equals(lista.get(0)));
  			
  			
  		}
  		catch(Exception e)
  		{
  			String msg= "Error en la ejecucion de las pruebas de operaciones sobre la tabla Cliente. \n ";
  			msg+= "Revise el log de super andes y el datanucleus para conocer el detalle de la excepcion";
  			System.out.println(msg);
  			
  			fail("Error en las pruebas sobre la tabla cliente");
  			
  		}
  		finally 
  		{
  			superandes.limpiarsuperAndes();
  			superandes.cerrarUnidadPersistencia();
  		}
  	}
    
    @Test
	public void unicidadClienteTest() 
	{
    	try
    	{
    		log.info("Probando la restriccion de UNICIDAD del codigo del cliente");
    		superandes= new SuperAndes(openConfig(CONFIG_TABLAS_A));
    	}
    	catch(Exception e)
    	{
    		log.info("Pruebas de UNICIDAD de Cliente incompleta");
    		log.info("La causa es: "+ e.getCause().toString());
    		
    		String msg= "Prueba de UNICIDAD de Cliente incompleta";
    		msg+="Revise el log de super andes y el de datanucleus para conocer el detalle de la excepcion";
    		System.out.println(msg);
    		fail(msg);
    	}
    	
    	
    	try
    	{
    		List<VOCliente> lista= superandes.darVOCLiente();
  			assertEquals("No debe haber clientes creados",0, lista.size());
  			long codigo=1;
  			String nombre="John";
  			String correo="john@gmail.com";
  			int puntos=0;
  			String s="uniandes";
  			VOCliente cliente1= superandes.adicionarCliente(codigo, correo, nombre, puntos, s);
  			lista= superandes.darVOCLiente();
  			
  			assertEquals("Debe haber un cliente creado",1,lista.size());
  			VOCliente cliente2= superandes.adicionarCliente(codigo, correo, nombre, puntos, s);

  			assertNull("No puede adicionar dos clientes con el mismo codigo",cliente2);
    	}
    	catch(Exception e)
    	{
    		String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla Cliente.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla Cliente");
    	}
    	finally
    	{
    		superandes.limpiarsuperAndes();
    		superandes.cerrarUnidadPersistencia();
    	}
	}
    private JsonObject openConfig (String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración de tablas válido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "ClienteTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
