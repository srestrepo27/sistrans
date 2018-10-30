package uniandes.isis2304.superandes.negocio;

public class Pedido implements VOPedido
{
	private long id;
	
	private String fecha;
	
	private String proveedorNit;
	
	private String nombreSucursal;

	public Pedido()
	{
		this.id=0;
		this.fecha="";
		this.proveedorNit="";
		this.nombreSucursal="";

	}
	
	public Pedido(long id, String fecha, String proveedorNit,String nombreSucursal)
	{
		this.id=id;
		this.fecha=fecha;
		this.proveedorNit=proveedorNit;
		this.nombreSucursal=nombreSucursal;

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
	public String getNombreSucursal() {
		return nombreSucursal;
	}
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
	
}
