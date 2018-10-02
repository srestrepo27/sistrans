package uniandes.isis2304.superandes.negocio;

public class Compra implements VOCompra
{
	private String producto;

	
	private long cliente;
	
	private String factura;
	
	private double total;
	
	
	public Compra(String producto, long cliente, String factura, double total) {
		this.producto = producto;
		this.cliente = cliente;
		this.factura = factura;
		this.total = total;
	}
	
	public Compra() {
		this.producto = "";
		this.cliente = 0;
		this.factura = "";
		this.total = 0;
	}
	
	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public long getCliente() {
		return cliente;
	}

	public void setCliente(long cliente) {
		this.cliente = cliente;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	
}
