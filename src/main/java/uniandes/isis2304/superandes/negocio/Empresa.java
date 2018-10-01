package uniandes.isis2304.superandes.negocio;

public class Empresa implements VOEmpresa
{
	private String nit;

	private String direccion;
	
	private long clientesCodigo;
	
	public Empresa(String nit, String direccion, long clientesCodigo)
	{
		this.nit = nit;
		this.direccion = direccion;
		this.clientesCodigo = clientesCodigo;
	}
	
	public Empresa()
	{
		this.nit = "";
		this.direccion = "";
		this.clientesCodigo = 0;
	}
	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public long getClientesCodigo() {
		return clientesCodigo;
	}

	public void setClientesCodigo(long clientesCodigo) {
		this.clientesCodigo = clientesCodigo;
	}
}
