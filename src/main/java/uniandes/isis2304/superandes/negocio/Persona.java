package uniandes.isis2304.superandes.negocio;

public class Persona implements VOPersona
{
	private long cedula;
	
	private long codigoCliente;
	
	public  Persona()
	{
		this.cedula=0;
		this.codigoCliente=0;
	}
	
	public Persona(long cedula, long codigoCliente)
	{
		this.cedula=cedula;
		this.codigoCliente=codigoCliente;
	}
	
	public long getCedula() {
		return cedula;
	}

	public void setCedula(long cedula) {
		this.cedula = cedula;
	}

	public long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
}
