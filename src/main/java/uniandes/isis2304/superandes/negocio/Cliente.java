package uniandes.isis2304.superandes.negocio;

public class Cliente implements VOCliente
{


	private long codigo;
	
	private String nombre;
	
	private String correo;
	
	private int puntos;
	

	private String nombreSucursal;
	
	public Cliente()
	{
		this.codigo=0;
		this.correo="";
		this.nombre="";
		this.puntos=0;
		this.nombreSucursal="";
	}
	public Cliente(long pCodigo, String pCorreo, String pNombre, int pPuntos,String nombreSucursal)
	{
		this.codigo=pCodigo;
		this.correo=pCorreo;
		this.nombre=pNombre;
		this.puntos=pPuntos;
		this.nombreSucursal=nombreSucursal;
	}
	
	public long getCodigo()
	{
		return codigo;
	}
	public void setCodigo(long codigo) 
	{
		this.codigo = codigo;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public String getCorreo() 
	{
		return correo;
	}
	public void setCorreo(String correo)
	{
		this.correo = correo;
	}
	public int getPuntos() 
	{
		return puntos;
	}
	public void setPuntos(int puntos) 
	{
		this.puntos = puntos;
	}
	public String getNombreSucursal() {
		return nombreSucursal;
	}
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
	
}
