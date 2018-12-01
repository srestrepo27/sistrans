package DATOS;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class generador {
	public static final String PATH="./data/";
	
	public static void generarSucursales (){
		try {
			PrintStream out = new PrintStream (new FileOutputStream(PATH+"sucursales.sql"), true);
			String nombre = "Sucursal ";
			String direccion = "Calle ";
			String ciudad = "Ciudad ";
			for (int i = 1; i <= 10000; i++) {
				out.println("INSERT INTO A_SUCURSAL VALUES ('"+nombre+i+"','"+direccion+i+"','"+ciudad+i+"');");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void generarCliente (){
		try {
			PrintStream out = new PrintStream (new FileOutputStream(PATH+"cliente.sql"), true);
			String codigo = "Codigo ";
			String nombre = "Nombre ";
			String correo = "Correo ";
			int puntos = 0;
			Random r = new Random ();
			String sucursal = "Sucursal ";
			for (int i = 1; i <= 150000; i++) {
				puntos = r.nextInt(1000);
				int t = r.nextInt(10000);
				t=t==0?t=1:t;
				out.println("INSERT INTO A_Cliente VALUES ("+i+",'"+nombre+i+"','"+correo+i+"',"+ puntos + ",'"+sucursal+t +"');");
			}
			System.out.println("fin");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void generarEmpresas (){
		try {
			PrintStream out = new PrintStream (new FileOutputStream(PATH+"empresa.sql"), true);
			String nit = "nit ";
			String direccion = "direccion ";
			int codigo = 0;
			for (int i = 1; i <= 50000; i++) {
				out.println("INSERT INTO A_empresa VALUES ('"+nit+i+"','"+direccion+i+"',"+i+ ");");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void generarPersona (){
		try {
			PrintStream out = new PrintStream (new FileOutputStream(PATH+"persona.sql"), true);
		
			int codigo = 0;
			for (int i = 50001; i <= 150000; i++) {
				out.println("INSERT INTO A_persona VALUES ("+"00"+i+","+i+");");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void generarEstantes (){
		try {
			PrintStream out = new PrintStream (new FileOutputStream(PATH+"estatnes.sql"), true);
			String nombre = "Sucursal ";
			int codigo = 0;
			for (int i = 0; i <= 10000 ; i++) {
				out.println("INSERT INTO A_estante VALUES ("+i+","+500+","+500+",'"+nombre+i+"',"+"'CARNE');");
			}
			for (int i = 10000; i <= 15000 ; i++) {
				int p = i-10000;
				out.println("INSERT INTO A_estante VALUES ("+i+","+500+","+500+",'"+nombre+p+"',"+"'ENLATADO');");
			}
			for (int i = 15000; i <= 20000 ; i++) {
				int p = i-10000;
				out.println("INSERT INTO A_estante VALUES ("+i+","+500+","+500+",'"+nombre+p+"',"+"'ASEO');");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void generarBodegas (){
		try {
			PrintStream out = new PrintStream (new FileOutputStream(PATH+"bodegas.sql"), true);
			String nombre = "Sucursal ";
			int codigo = 0;
			for (int i = 0; i <= 10000 ; i++) {
				out.println("INSERT INTO A_bodega VALUES ("+i+","+500+","+500+",'"+nombre+i+"',"+"'CARNE');");
			}
			for (int i = 10000; i <= 15000 ; i++) {
				int p = i-10000;
				out.println("INSERT INTO A_bodega VALUES ("+i+","+500+","+500+",'"+nombre+p+"',"+"'ENLATADO');");
			}
			for (int i = 15000; i <= 20000 ; i++) {
				int p = i-10000;
				out.println("INSERT INTO A_bodega VALUES ("+i+","+500+","+500+",'"+nombre+p+"',"+"'ASEO');");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void generarProductos (){
		try {
			PrintStream out = new PrintStream (new FileOutputStream(PATH+"productos.sql"), true);
			String codigo = "P ";
			String nombre = "n ";
			String ciudad = "Ciudad ";
			for (int i = 1; i <= 100000; i++) {
				int m = i%3;
				int compra=m*10000;
				int venta = compra+5000;
				int c = (i%4)+10000;
				int u = venta/c; 
				int p= i%2;
				int bodega = i % 20000;
				String categoria= "'ENLATADO'";
				if (m==0){
					 categoria= "'ASEO'";
				}
				if (m==1){
					 categoria= "'CARNE'";
				}
				
				out.println("INSERT INTO A_producto VALUES ('"+codigo+i+"','litros','"+nombre+i+"','marca "+m+"',"+venta+","+compra+",'Individual',"+
				u+","+c+",'plastico',"+p+","+categoria+",10,"+i+","+i+",null);");
			System.out.println(i);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void generarProveedores (){
		try {
			PrintStream out = new PrintStream (new FileOutputStream(PATH+"provedores.sql"), true);
		
			int codigo = 0;
			for (int i =1; i <= 50000; i++) {
				out.println("INSERT INTO A_proveedor VALUES ('"+i+"','pro"+i+"');");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void generarPedido (){
		try {
			PrintStream out = new PrintStream (new FileOutputStream(PATH+"pedidosaa.sql"), true);
			String nombre = "Sucursal ";
			int codigo = 0;
			
			for (int i =0; i <= 100000; i++)
			{
				int s =i%10000;
				int p = i;
				if (i<50000){
				 p = (i%5000)+2;
				}
				
				out.println("INSERT INTO A_pedido VALUES ("+i+",DATE'2017-12-17','"+p+"','"+nombre +s+"');");
			
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void generarCompra (){
		try {
			PrintStream out = new PrintStream (new FileOutputStream(PATH+"compra.sql"), true);
			String codigo = "P ";
			
			
			for (int i =0; i <= 400000; i++)
			{
				int p = i ;
				int c= (i%150000)+1;
				int t= (i% 250000)+1;
				if (i>100000){
				 p = i%20;
				}
				
				out.println("INSERT INTO A_compra VALUES ('"+codigo+p+"',"+c+",'factura"+i +"',"+t+",DATE'2017-12-17');");
			
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	






	public static void main(String[] args) {
		generarSucursales();
	}

}
