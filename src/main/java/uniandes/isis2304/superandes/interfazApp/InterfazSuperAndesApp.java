package uniandes.isis2304.superandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
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

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Empresa;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.SuperAndes;
import uniandes.isis2304.superandes.negocio.VOCliente;


public class InterfazSuperAndesApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazSuperAndesApp.class.getName());

	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
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
	 * Asociación a la clase principal del negocio.
	 */
	private SuperAndes superAndes;



	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/

	/**
	 * Objeto JSON con la configuración de interfaz de la app.
	 */
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacción para los requerimientos
	 */
	private PanelDatos panelDatos;

	/**
	 * Menú de la aplicación
	 */
	private JMenuBar menu;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicación. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazSuperAndesApp( )
	{
		// Carga la configuración de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
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
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuración deseada
	 * @param archConfig - Archivo Json que contiene la configuración
	 * @return Un objeto JSON con la configuración del tipo especificado
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
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
			//				e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "SuperAndes App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * Método para configurar el frame principal de la aplicación
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
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
	 * Método para crear el menú de la aplicación con base em el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menùs deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creación de la barra de menús
		menu = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creación de cada uno de los menús
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creación de cada una de las opciones del menú
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
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String evento = e.getActionCommand( );		
		try 
		{
			Method req = InterfazSuperAndesApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

	}

	/* ****************************************************************
	 * 			CRUD de Clientes
	 *****************************************************************/

	public void adicionarCliente()
	{
		try 
		{
			String pOe = JOptionPane.showInputDialog (this, "Empresa o Persona?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			long id=superAndes.darClientes().get(0).getSuperMercadoId();
			if(pOe.equals("empresa"))
			{
				String nitEmpresa=JOptionPane.showInputDialog (this, "nit de la empresa?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
				String direccionEmpresa=JOptionPane.showInputDialog (this, "direccion de la empresa?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
				String nombreCliente = JOptionPane.showInputDialog (this, "Nombre del cliente?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
				String correoCliente = JOptionPane.showInputDialog (this, "correo del cliente?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
				String codigoCliente = JOptionPane.showInputDialog (this, "codigo del cliente?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
				if (nombreCliente != null && correoCliente != null && codigoCliente != null && direccionEmpresa != null && nitEmpresa !=null )
				{
					Empresa em= superAndes.adicionarempresa(nitEmpresa, direccionEmpresa, Long.parseLong(codigoCliente));
					VOCliente cl= superAndes.adicionarCliente( Long.parseLong(codigoCliente), correoCliente, nombreCliente, 0, id);
				}
				else
				{
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
				}
			}
			else if(pOe.equals("persona"))
			{
				
				String cedulaPersona= JOptionPane.showInputDialog (this, "cedula del cliente?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
				String nombreCliente = JOptionPane.showInputDialog (this, "Nombre del cliente?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
				String correoCliente = JOptionPane.showInputDialog (this, "Nombre del cliente?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
				String codigoCliente = JOptionPane.showInputDialog (this, "Nombre del cliente?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
				if (nombreCliente != null && correoCliente != null && codigoCliente != null && cedulaPersona!=null)
				{
					VOCliente cl= superAndes.adicionarCliente( Long.parseLong(codigoCliente), correoCliente, nombreCliente, 0, id);
				}
				else
				{
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
				}
			}
			else
			{
				panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
			}
		}
		catch(Exception e)
		{
			String resultado= generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	
		
	}
	
	
	public void realizarVenta()
	{
		try 
		{
			String c=JOptionPane.showInputDialog (this, "id cliente?", "Realizar venta", JOptionPane.QUESTION_MESSAGE);
			Cliente cliente= superAndes.darClietePorId(Long.parseLong(c));
			long id= cliente.getCodigo();
			String numero = JOptionPane.showInputDialog (this, "Cuantos productos va a comprar?", "Realizar venta", JOptionPane.QUESTION_MESSAGE);
			int num= Integer.parseInt(numero);
			List<Producto> lista= new ArrayList<Producto>();
			for(int i=0;i<num;i++)
			{
				String producto= JOptionPane.showInputDialog (this, "Nombre del producto?", "Realizar venta", JOptionPane.QUESTION_MESSAGE);
				
				Producto p=	superAndes.darProductoPorNombre(producto).get(0);
				
				lista.add(p);
			}
			
			for(int i=0;i<lista.size();i++)
			{
				Producto p= lista.get(i);
				
				if(p.getCantidad()-1<0)
				{
					throw new Exception("unidades insuficientes");
				}
			}
			
			superAndes.realizarVenta(lista, id);
		}
		catch(Exception e)
		{
			String resultado= generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/* ****************************************************************
	 * 			CRUD de producto
	 *****************************************************************/
	
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
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
}
