package uniandes.isis2304.superandes.negocio;

public class PromocionSucursal implements VOPromocionSucursal 
{
	private long id;

	private String fechaLimite;
	
	private String nombre;
	
	private String codigoProducto;
	
	public PromocionSucursal() 
	{
		this.id =0;
		this.fechaLimite = "";
		this.nombre = "";
		this.codigoProducto = "";
	}
	
	public PromocionSucursal(long id, String fechaLimite, String nombre, String codigoProducto) {
		super();
		this.id = id;
		this.fechaLimite = fechaLimite;
		this.nombre = nombre;
		this.codigoProducto = codigoProducto;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(String fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
}
