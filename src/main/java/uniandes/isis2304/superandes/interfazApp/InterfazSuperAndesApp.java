package uniandes.isis2304.superandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.*;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.superandes.negocio.Cliente;
import uniandes.isis2304.superandes.negocio.Compra;
import uniandes.isis2304.superandes.negocio.Empresa;
import uniandes.isis2304.superandes.negocio.Pedido;
import uniandes.isis2304.superandes.negocio.Producto;
import uniandes.isis2304.superandes.negocio.SuperAndes;
import uniandes.isis2304.superandes.negocio.VOCliente;


@SuppressWarnings("serial")
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

	private String nombreSucursal;

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/

	/**
	 * Objeto JSON con la configuración de interfaz de la app.
	 */
	private JsonObject geyConfig;

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
		menu = new JMenuBar(); 

		// Carga lx configuración de lx interfaz desde un archivo JSON
		geyConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
		configurarFrame ( );
		if (geyConfig != null) 	   
		{
			crearMenu( geyConfig.getAsJsonArray("menuBar") );
		}

		setJMenuBar ( menu );

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		superAndes = new SuperAndes (tableConfig);

		String path = geyConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );  
		iniciar();
	}
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
			//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "SuperAndes App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/** Método para configurar el frame principal de la aplicación */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( geyConfig == null )
		{
			log.info ( "Se aplica configuración por defecto" );			
			titulo = "Superandes APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
			titulo = geyConfig.get("title").getAsString();
			alto= geyConfig.get("frameH").getAsInt();
			ancho = geyConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * Método para crear el menú de la aplicación con base en el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menús deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creación de la barra de menú      
		for (JsonElement men : jsonMenu)
		{
			// Creación de cada uno de los menús

			JsonObject jom = men.getAsJsonObject(); 
			System.out.println(jom);       
			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("opciones");

			JMenu menu2 = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creación de cada una de las opciones del menú
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu2.add(mItem);
			}       
			menu.add( menu2 );
		}        	
	}

	public void iniciar()
	{
		String n=JOptionPane.showInputDialog (this, "ingrese el nombre de la sucursal", " ¿Sucursal?", JOptionPane.QUESTION_MESSAGE);
		nombreSucursal=n;
	}
	/* ****************************************************************
	 * 			CRUD de Clientes
	 *****************************************************************/

	public void adicionarCliente()
	{
		try 
		{

			String pOe = JOptionPane.showInputDialog (this, "Empresa o Persona?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
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
					VOCliente cl= superAndes.adicionarCliente( Long.parseLong(codigoCliente), correoCliente, nombreCliente, 0, nombreSucursal);
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
					VOCliente cl= superAndes.adicionarCliente( Long.parseLong(codigoCliente), correoCliente, nombreCliente, 0, nombreSucursal);
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



	/* ****************************************************************
	 * 			VENDER
	 *****************************************************************/
	public void vender()
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
				superAndes.realizarPedido(p.getCodigoBarras(),nombreSucursal);
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

	public void adicionarProducto()
	{
		try
		{
			String codigo= JOptionPane.showInputDialog (this, "id producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String unidad=JOptionPane.showInputDialog (this, "unidad producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String nombre=JOptionPane.showInputDialog (this, "nombre producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);

			String marca=JOptionPane.showInputDialog (this, "marca producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String precioVenta=JOptionPane.showInputDialog (this, "precio venta producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String precioCompra=JOptionPane.showInputDialog (this, "precio Compra producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String presentacion=JOptionPane.showInputDialog (this, "unidad producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String precioXunidad=JOptionPane.showInputDialog (this, "precio por unidad producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String cantidad=JOptionPane.showInputDialog (this, "cantidad producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String empaque=JOptionPane.showInputDialog (this, "empaque producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String perecedero=JOptionPane.showInputDialog (this, "perecedero producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String categoria=JOptionPane.showInputDialog (this, "categoria producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String nivelReo=JOptionPane.showInputDialog (this, "nivel reorden producto?", "adicionar producto", JOptionPane.QUESTION_MESSAGE);



			if(codigo !=null && unidad!=null && nombre !=null && marca!=null && precioVenta !=null && precioCompra!=null && presentacion !=null && precioXunidad!=null && cantidad !=null && empaque!=null && perecedero !=null && categoria!=null && nivelReo!=null)
			{
				superAndes.adicionarproducto(codigo, unidad, nombre, marca, Double.parseDouble(precioVenta), Double.parseDouble(precioCompra), presentacion, Double.parseDouble(precioXunidad), Integer.parseInt(cantidad), empaque, Boolean.parseBoolean(perecedero), categoria, Integer.parseInt(nivelReo), (long)3, (long)2, (long)10);

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
	public void eliminarProducto()
	{
		try
		{
			String c=JOptionPane.showInputDialog (this, "id producto?", "Eliminar producto", JOptionPane.QUESTION_MESSAGE);
			if(c!=null)
			{
				superAndes.eliminarProductoCodigoB(c);
				panelDatos.actualizarInterfaz("se elimino el producto con id "+c);
			}

			else
				panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
		}



		catch(Exception e)
		{
			String resultado= generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	/* ****************************************************************
	 * 			CRUD de proveedor
	 *****************************************************************/
	public void adicionarProveedor()
	{
		try
		{
			String nit= JOptionPane.showInputDialog (this, "nit proveedor?", "adicionar proveedor", JOptionPane.QUESTION_MESSAGE);
			String nombre=JOptionPane.showInputDialog (this, "nombre proveedor?", "adicionar proveedor", JOptionPane.QUESTION_MESSAGE);
			if(nit!=null && nombre!=null)
			{
				superAndes.adicionarproveedor(nombre, nit);
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

	/* ****************************************************************
	 * 			CRUD de sucursal
	 *****************************************************************/
	public void adicionarSucursal()
	{
		try
		{
			String nombre=JOptionPane.showInputDialog (this, "nombre sucursal?", "adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
			String direccion= JOptionPane.showInputDialog (this, "direccion proveedor?", "adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
			String ciudad= JOptionPane.showInputDialog (this, "ciudad proveedor?", "adicionar sucursal", JOptionPane.QUESTION_MESSAGE);
			if(nombre!=null && direccion!=null && ciudad!=null)
			{
				superAndes.adicionarsucursal(nombre, direccion, ciudad);

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



	/* ****************************************************************
	 * 			Metodos de interaccion
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


	/**
	 * Muestra el log de superAndes
	 */
	public void mostrarLogSuperAndes()
	{
		mostrarArchivo ("superAndes.log");
	}

	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
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



	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/EsquemaConceptualSuperAndes.pdf");
	}

	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/EsquemaBD.pdf");
	}

	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaSuperAndes.sql");
	}





	public void acercaDe ()
	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: SuperAndes Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Santiago Restrepo, Ixtli Barbosa \n";
		resultado += " * Octubre 3 de 2018\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}

	/* ****************************************************************
	 * 			REQUERIMIENTOS DE CONSULTA
	 *****************************************************************/

	public void RFC1()
	{
		try
		{
			String resultado="";

			String f1=JOptionPane.showInputDialog (this, "fecha inicial?", "RFC1", JOptionPane.QUESTION_MESSAGE);
			String f2=JOptionPane.showInputDialog (this, "fecha final?", "RFC1", JOptionPane.QUESTION_MESSAGE);
			resultado+= superAndes.RFC1(f1, f2);
			panelDatos.actualizarInterfaz(resultado);

		}
		catch(Exception e)
		{
			String resultado= generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RFC2()
	{
		try
		{
			String resultado="";
			List<Object[]>lista= superAndes.RFC2();
			for(Object p:lista)
			{
				resultado+=p.toString() +"\n";
			}
			panelDatos.actualizarInterfaz(resultado);

		}
		catch(Exception e)
		{
			String resultado= generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void RFC3()
	{
		try
		{
			String resultado="";
			resultado= superAndes.RFC3();
			panelDatos.actualizarInterfaz(resultado);
		}
		catch(Exception e)
		{
			String resultado= generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RFC4()
	{

	}
	public void RFC5()
	{
		try
		{
			String resultado="";
			List<Pedido> lista= superAndes.RFC5();
			for(Pedido p:lista)
			{
				resultado+=p.toString() +"\n";
			}
			panelDatos.actualizarInterfaz(resultado);

		}
		catch(Exception e)
		{
			String resultado= generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RFC6()
	{
		try
		{
			String id=JOptionPane.showInputDialog (this, "id cliente?", "RFC6", JOptionPane.QUESTION_MESSAGE);
			String f1=JOptionPane.showInputDialog (this, "fecha inicial?", "RFC6", JOptionPane.QUESTION_MESSAGE);
			String f2=JOptionPane.showInputDialog (this, "fecha final?", "RFC6", JOptionPane.QUESTION_MESSAGE);


			String resultado="";
			List<Compra> lista= superAndes.RFC6(Long.parseLong(id), f1, f2);
			for(Compra c:lista)
			{
				resultado+=c.toString() +"\n";
			}
			panelDatos.actualizarInterfaz(resultado);

		}
		catch(Exception e)
		{
			String resultado= generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
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
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por línea de comandos
	 */
	public static void main( String[] args )
	{
		try
		{

			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazSuperAndesApp interfaz = new InterfazSuperAndesApp( );
			interfaz.setVisible( true );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
}
