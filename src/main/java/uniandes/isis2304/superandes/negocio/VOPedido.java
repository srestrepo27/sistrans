package uniandes.isis2304.superandes.negocio;

public interface VOPedido
{
	public long getId();

	public String getFecha() ;

	public String getProveedorNit() ;
	
	public String getNombreSucursal() ;
	
	public void setNombreSucursal(String nombreSucursal);

}
