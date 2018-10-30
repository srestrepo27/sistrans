package uniandes.isis2304.superandes.negocio;

public class Contiene implements VOContiene
{
	private long idCarrito;
	
	private String codigoProducto;
	
	
	public Contiene() 
	{
		this.idCarrito = 0;
		this.codigoProducto = "";
	}
	
	public Contiene(long idCarrito, String codigoProducto) 
	{
		this.idCarrito = idCarrito;
		this.codigoProducto = codigoProducto;
	}
	
	public long getIdCarrito() 
	{
		return idCarrito;
	}

	public void setIdCarrito(long idCarrito)
	{
		this.idCarrito = idCarrito;
	}

	public String getCodigoProducto() 
	{
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) 
	{
		this.codigoProducto = codigoProducto;
	}
	
}
