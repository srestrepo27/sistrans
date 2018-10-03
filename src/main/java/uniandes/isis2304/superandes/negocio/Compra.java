package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

public class Compra implements VOCompra
{
	private String producto;

	
	private long cliente;
	
	private String factura;
	
	private double total;
	
	private  Timestamp fecha;
	private long id ;
	
	public Compra(String producto, long cliente, String factura, double total, Timestamp fecha,long id) {
		this.producto = producto;
		this.cliente = cliente;
		this.factura = factura;
		this.total = total;
		this.fecha = fecha ;
		this.id= id ;
		
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
	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
}
