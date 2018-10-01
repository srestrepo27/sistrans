package uniandes.isis2304.superandes.negocio;

public class Proveedor implements VOProveedor
{
	private String nit;
	
	
	private String nombre;
	
	public Proveedor()
	{
		this.nit="";
		this.nombre="";          
	}                            
	
	public Proveedor(String pNombre, String pNit)
	{
		this.nit=pNit;
		this.nombre=pNombre;
	}
	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
