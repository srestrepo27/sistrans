package uniandes.isis2304.superandes.interfazDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;


import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.SuperAndes;
import uniandes.isis2304.superandes.negocio.VOCliente;
import uniandes.isis2304.superandes.negocio.VOCompra;
import uniandes.isis2304.superandes.negocio.VOProducto;


public class InterfazSuperAndesDemo extends JFrame implements ActionListener
	{
		/* ****************************************************************
		 * 			Constantes
		 *****************************************************************/
		/**
		 * Logger para escribir la traza de la ejecuci�n
		 */
		private static Logger log = Logger.getLogger(InterfazSuperAndesDemo.class.getName());
		
		/**
		 * Ruta al archivo de configuraci�n de la interfaz
		 */
		private final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigDemo.json"; 
		
		/**
		 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos
		 */
		private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json";

		/* ****************************************************************
		 * 			Atributos
		 *****************************************************************/
	
		 /**
	     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
	     */
	    private JsonObject tableConfig;
	    
	    /**
	     * Asociaci�n a la clase principal del negocio.
	     */
	    private SuperAndes superAndes;
	    
		/* ****************************************************************
		 * 			Atributos de interfaz
		 *****************************************************************/
	    /**
	     * Objeto JSON con la configuraci�n de interfaz de la app.
	     */
	    private JsonObject guiConfig;
	    
	    /**
	     * Panel de despliegue de interacci�n para los requerimientos
	     */
	    private PanelDatos panelDatos;
	    
	    /**
	     * Men� de la aplicaci�n
	     */
	    private JMenuBar menu;
	    
		
		/* ****************************************************************
		 * 			M�todos
		 *****************************************************************/
	    /**
	     * Construye la ventana principal de la aplicaci�n. <br>
	     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	     */
	    public InterfazSuperAndesDemo( )
	    {
	        // Carga la configuraci�n de la interfaz desde un archivo JSON
	        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
	        
	        // Configura la apariencia del frame que contiene la interfaz gr�fica
	        configurarFrame ( );
	        if (guiConfig != null) 	   
	        {
	     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
	        }
	        
	        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
	        superAndes = new SuperAndes (tableConfig);
	        
	    	String path = guiConfig.get("bannerPath").getAsString();
	        panelDatos = new PanelDatos ( );

	        setLayout (new BorderLayout());
	        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
	        add( panelDatos, BorderLayout.CENTER );        
	    }
	    
		/* ****************************************************************
		 * 			M�todos para la configuraci�n de la interfaz
		 *****************************************************************/
	    /**
	     * Lee datos de configuraci�n para la aplicaci�n, a partir de un archivo JSON o con valores por defecto si hay errores.
	     * @param tipo - El tipo de configuraci�n deseada
	     * @param archConfig - Archivo Json que contiene la configuraci�n
	     * @return Un objeto JSON con la configuraci�n del tipo especificado
	     * 			NULL si hay un error en el archivo.
	     */
	    private JsonObject openConfig (String tipo, String archConfig)
	    {
	    	JsonObject config = null;
			try 
			{
				Gson gson = new Gson( );
				FileReader file = new FileReader (archConfig);
				JsonReader reader = new JsonReader ( file );
				config = gson.fromJson(reader, JsonObject.class);
				log.info ("Se encontr� un archivo de configuraci�n v�lido: " + tipo);
			} 
			catch (Exception e)
			{
//				e.printStackTrace ();
				log.info ("NO se encontr� un archivo de configuraci�n v�lido");			
				JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de interfaz v�lido: " + tipo, "SuperAndes App", JOptionPane.ERROR_MESSAGE);
			}	
	        return config;
	    }
	    
	    /**
	     * M�todo para configurar el frame principal de la aplicaci�n
	     */
	    private void configurarFrame(  )
	    {
	    	int alto = 0;
	    	int ancho = 0;
	    	String titulo = "";	
	    	
	    	if ( guiConfig == null )
	    	{
	    		log.info ( "Se aplica configuraci�n por defecto" );			
				titulo = "Parranderos APP Default";
				alto = 300;
				ancho = 500;
	    	}
	    	else
	    	{
				log.info ( "Se aplica configuraci�n indicada en el archivo de configuraci�n" );
	    		titulo = guiConfig.get("title").getAsString();
				alto= guiConfig.get("frameH").getAsInt();
				ancho = guiConfig.get("frameW").getAsInt();
	    	}
	    	
	        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	        setLocation (50,50);
	        setResizable( true );
	        setBackground( Color.WHITE );

	        setTitle( titulo );
			setSize ( ancho, alto);        
	    }

	    /**
	     * M�todo para crear el men� de la aplicaci�n con base em el objeto JSON le�do
	     * Genera una barra de men� y los men�s con sus respectivas opciones
	     * @param jsonMenu - Arreglo Json con los men�s deseados
	     */
	    private void crearMenu(  JsonArray jsonMenu )
	    {    	
	    	// Creaci�n de la barra de men�s
	        menu = new JMenuBar();       
	        for (JsonElement men : jsonMenu)
	        {
	        	// Creaci�n de cada uno de los men�s
	        	JsonObject jom = men.getAsJsonObject(); 

	        	String menuTitle = jom.get("menuTitle").getAsString();        	
	        	JsonArray opciones = jom.getAsJsonArray("options");
	        	
	        	JMenu menu = new JMenu( menuTitle);
	        	
	        	for (JsonElement op : opciones)
	        	{       	
	        		// Creaci�n de cada una de las opciones del men�
	        		JsonObject jo = op.getAsJsonObject(); 
	        		String lb =   jo.get("label").getAsString();
	        		String event = jo.get("event").getAsString();
	        		
	        		JMenuItem mItem = new JMenuItem( lb );
	        		mItem.addActionListener( this );
	        		mItem.setActionCommand(event);
	        		
	        		menu.add(mItem);
	        	}       
	        	menu.add( menu );
	        }        
	        setJMenuBar ( menu );	
	    }
	    /* ****************************************************************
		 * 			Demos de cliente
		 *****************************************************************/
	    public void demoCliente()
	    {
	    	try
	    	{
	    		long codigo=1;
	  			String nombre="John";
	  			String correo="john@gmail.com";
	  			int puntos=0;
	  			long s=0;
	  			boolean error=false;
	  			
	  			VOCliente cliente= superAndes.adicionarCliente(codigo, correo, nombre, puntos, s);
	  			
	  			if(cliente==null)
	  			{
	  				error=true;
	  				cliente=superAndes.darClietePorId(codigo);
	  			}
	  			
	  			List<VOCliente> lista= superAndes.darVOCLiente();
	  			long tbEliminados= superAndes.eliminarClienteId(codigo);
	  			
	  			String resultado= "Demo de creacion y listado de Cliente \n";
	  			resultado+="\n\n *************Generando datos de prueba *********** \n";
	  			if(error)
	  			{
	  				resultado+= "*** Exception creando cliente \n";
	  				resultado+= "*** Es probable que el cliente ya exista\n";
	  				resultado+= "*** Revise el log de superandes para mas detalles";
	  			}
	  			resultado+="Adicionando el cliente con codigo: " + nombre + "\n";
	  			resultado+= "\n\n *******Ejecutando la demo ******* \n";
	  			resultado+= "\n" + listarClientes(lista);
	  			resultado+="\n\n **********Limpiando base de datos*********\n";
	  			resultado+= tbEliminados + "Clientes eliminados\n";
	  			resultado+= "\n Demo terminada";
	  			
	  			panelDatos.actualizarInterfaz(resultado);
	    	}
	    	catch(Exception e)
	    	{
	    		String resultado= generarMensajeError(e);
	    		panelDatos.actualizarInterfaz(resultado);
	    	}
	    	
	    	
	    	
	    	
	    	
	    }
	    
	    /* ****************************************************************
	  		 * 			Demos de venta
	  		 *****************************************************************/
	    public void demoVender()
	    {
	    	try
	    	{
	    		boolean error=false;
	    		VOCliente cliente= superAndes.adicionarCliente(1, "correo@gmail.com", "juan", 0, 0);
	    		if(cliente==null)
	    		{
	    			error=true;
	  				cliente=superAndes.darClietePorId(1);
	    		}
	    		List<VOCliente> listaCliente= superAndes.darVOCLiente();
	    		long id=10;
	    		VOProducto producto1= superAndes.adicionarproducto("HHH", "cm", "galleta", "oreo", 44.4, 30.4, "paquete", 2.3, 5, "paquete", false, "empaquetados", 5, id, id, id);
	    		List<Producto> lista= new ArrayList<Producto>();
	    		lista.add((Producto) producto1);
	    		List<Producto> listaProductos=superAndes.darProductoPorNombre("galleta");
	    		superAndes.realizarVenta(lista, cliente.getCodigo());
	    		List<VOCompra> listaCompra= superAndes.darVOCompra();
	    		
	    		long productosEliminados= superAndes.eliminarProducto();
	    		long clientesEliminados=superAndes.eliminarClienteId(1);
	    		long comprasEliminadas= superAndes.eliminarCompra();
	    		String resultado= "Demo de creacion y listado de Compra \n";
	  			resultado+="\n\n *************Generando datos de prueba *********** \n";
	    		if(error)
	  			{
	  				resultado+= "*** Exception creando compra \n";
	  				resultado+= "*** Es probable que el cliente ya exista\n";
	  				resultado+= "*** Revise el log de superandes para mas detalles";
	  			}
	    		resultado+="\n" + listarClientes(listaCliente);
	    		resultado+="\n" +listarProductos(listaProductos);
	    		resultado+="\n"+ listarCompras(listaCompra);
	    		
	    		resultado+= "\n\n *******Ejecutando la demo ******* \n";
	  			resultado+="\n\n **********Limpiando base de datos*********\n";
	  			resultado+= clientesEliminados + "Clientes eliminados\n";
	  			resultado+= comprasEliminadas + "Compras eliminadas\n";

	  			resultado+= productosEliminados + "Productos eliminados\n";

	  			resultado+= "\n Demo terminada";
	  			panelDatos.actualizarInterfaz(resultado);
	    	}
	    	catch (Exception e) 
			{
//				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
	    }
	    
	    private String listarClientes(List<VOCliente> lista) 
	    {
	    	String resp = "Los clientes son:\n";
	    	int i = 1;
	        for (VOCliente tb : lista)
	        {
	        	resp += i++ + ". " + tb.toString() + "\n";
	        }
	        return resp;
		}
	    
	    private String listarProductos(List<Producto> lista) 
	    {
	    	String resp = "Los productos son:\n";
	    	int i = 1;
	        for (Producto tb : lista)
	        {
	        	resp += i++ + ". " + tb.toString() + "\n";
	        }
	        return resp;
		}
	    
	    private String listarCompras(List<VOCompra> lista) 
	    {
	    	String resp = "Los productos son:\n";
	    	int i = 1;
	        for (VOCompra tb : lista)
	        {
	        	resp += i++ + ". " + tb.toString() + "\n";
	        }
	        return resp;
		}
	    
	    private String generarMensajeError(Exception e) 
		{
			String resultado = "************ Error en la ejecuci�n\n";
			resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
			resultado += "\n\nRevise datanucleus.log y parranderos.log para m�s detalles";
			return resultado;
		}
	    
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
	    
		private boolean limpiarArchivo(String nombreArchivo) 
		{
			BufferedWriter bw;
			try 
			{
				bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
				bw.write ("");
				bw.close ();
				return true;
			} 
			catch (IOException e) 
			{
//				e.printStackTrace();
				return false;
			}
		}
		
		private void mostrarArchivo (String nombreArchivo)
		{
			try
			{
				Desktop.getDesktop().open(new File(nombreArchivo));
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* ****************************************************************
		 * 			M�todos de la Interacci�n
		 *****************************************************************/
	    /**
	     * M�todo para la ejecuci�n de los eventos que enlazan el men� con los m�todos de negocio
	     * Invoca al m�todo correspondiente seg�n el evento recibido
	     * @param pEvento - El evento del usuario
	     */
	    @Override
		public void actionPerformed(ActionEvent pEvento)
		{
			String evento = pEvento.getActionCommand( );		
	        try 
	        {
				Method req = InterfazSuperAndesDemo.class.getMethod ( evento );			
				req.invoke ( this );
			} 
	        catch (Exception e) 
	        {
				e.printStackTrace();
			} 
		}
		
		/* ****************************************************************
		 * 			Programa principal
		 *****************************************************************/
	    /**
	     * Este m�todo ejecuta la aplicaci�n, creando una nueva interfaz
	     * @param args Arreglo de argumentos que se recibe por l�nea de comandos
	     */
	    public static void main( String[] args )
	    {
	        try
	        {
	        	
	            // Unifica la interfaz para Mac y para Windows.
	            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
	            InterfazSuperAndesDemo interfaz = new InterfazSuperAndesDemo( );
	            interfaz.setVisible( true );
	        }
	        catch( Exception e )
	        {
	            e.printStackTrace( );
	        }
	    }

	}
