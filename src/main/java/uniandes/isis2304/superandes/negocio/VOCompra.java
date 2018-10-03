package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

public interface VOCompra 
{
	public String getProducto();


	public long getCliente();

	public String getFactura();

	
	public double getTotal();
	
	public Timestamp getFecha();
	

	public long getId();

}
