package uniandes.isis2304.superandes.negocio;

public class Provee implements VOProvee
{
	private String proveedor;
	
	private String producto;
	
	public Provee(String proveedor, String producto) 
	{
		this.proveedor = proveedor;
		this.producto = producto;
	}
	
	public Provee() 
	{
		this.proveedor = "";
		this.producto = "";
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

}
