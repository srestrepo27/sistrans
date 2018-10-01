package uniandes.isis2304.superandes.negocio;

public interface VOProducto 
{
	public String getCodigoBarras();


	public String getUnidadMedida() ;


	public String getNombre() ;

	public String getMarca() ;



	public double getPrecioVenta() ;


	public double getPrecioCompra() ;


	public String getPresentacion() ;


	public double getPrecioXunidad() ;


	public int getCantidad();



	public String getEmpaque() ;


	public boolean isPercedero();



	public String getCategoria();



	public int getNivelReo();

	

	public long getEstantesId() ;


	public long getBodegasId();

	

	public long getPedidoId();


}
