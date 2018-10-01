package uniandes.isis2304.superandes.negocio;

public class PromocionProveedor implements VOPromocionProveedor
{
	private long id;

	private String producto;
	
	private String proveedor;
	

	public PromocionProveedor(long id, String producto, String proveedor) {
		this.id = id;
		this.producto = producto;
		this.proveedor = proveedor;
	}
	
	public PromocionProveedor() {
		this.id = 0;
		this.producto = "";
		this.proveedor = "";
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
}
