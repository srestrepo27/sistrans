package uniandes.isis2304.superandes.negocio;

public interface VOCliente 
{
	public long getCodigo();
	
	public String getNombre();

	public String getCorreo() ;
	
	public int getPuntos() ;

	public String getNombreSucursal();
	
	public void setNombreSucursal(String nombreSucursal) ;
	
}
