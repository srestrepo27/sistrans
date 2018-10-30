package uniandes.isis2304.superandes.negocio;

public class Carrito implements VOCarrito
{
	private long idCarrito;
	
	private long idCliente;
	
	 public Carrito() 
	 {
			this.idCarrito = 0;
			this.idCliente = 0;
	}
	 public Carrito(long idCarrito, long idCliente) 
	 {
			this.idCarrito = idCarrito;
			this.idCliente = idCliente;
	}

	 public long getIdCarrito() {
		return idCarrito;
	}

	public void setIdCarrito(long idCarrito) {
		this.idCarrito = idCarrito;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}


}
