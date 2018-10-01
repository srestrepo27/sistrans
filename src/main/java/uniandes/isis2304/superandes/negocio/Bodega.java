package uniandes.isis2304.superandes.negocio;

public class Bodega implements VOBodega
{
	private long id;

	private double peso;
	
	private double volumen;
	
	private String sucursal;
	
	private String categoria;
	
	
	public Bodega(long id, double peso, double volumen, String sucursal, String categoria) {
		this.id = id;
		this.peso = peso;
		this.volumen = volumen;
		this.sucursal = sucursal;
		this.categoria = categoria;
	}
	
	public Bodega() 
	{
		this.id = 0;
		this.peso = 0;
		this.volumen = 0;
		this.sucursal = "";
		this.categoria = "";
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getVolumen() {
		return volumen;
	}

	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	

}
