package uniandes.isis2304.superandes.negocio;

public class Pedido implements VOPedido
{
	private long id;
	
	private String fecha;
	
	private String proveedorNit;
	
	private long superMercadoId;
	
	public Pedido()
	{
		this.id=0;
		this.fecha="";
		this.proveedorNit="";
		this.superMercadoId=0;
	}
	
	public Pedido(long id, String fecha, String proveedorNit, long superMercadoId)
	{
		this.id=id;
		this.fecha=fecha;
		this.proveedorNit=proveedorNit;
		this.superMercadoId=superMercadoId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getProveedorNit() {
		return proveedorNit;
	}

	public void setProveedorNit(String proveedorNit) {
		this.proveedorNit = proveedorNit;
	}

	public long getSuperMercadoId() {
		return superMercadoId;
	}

	public void setSuperMercadoId(long superMercadoId) {
		this.superMercadoId = superMercadoId;
	}
}
