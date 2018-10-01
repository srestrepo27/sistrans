package uniandes.isis2304.superandes.negocio;

public class Cliente implements VOCliente
{


	private long codigo;
	
	private String nombre;
	
	private String correo;
	
	private int puntos;
	
	private long superMercadoId;
	
	public Cliente()
	{
		this.codigo=0;
		this.correo="";
		this.nombre="";
		this.puntos=0;
		this.superMercadoId=0;
		
	}
	public Cliente(long pCodigo, String pCorreo, String pNombre, int pPuntos, long id)
	{
		this.codigo=pCodigo;
		this.correo=pCorreo;
		this.nombre=pNombre;
		this.puntos=pPuntos;
		this.superMercadoId=id;
		
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
	public long getSuperMercadoId()
	{
		return superMercadoId;
	}
	public void setSuperMercadoId(long superMercadoId)
	{
		this.superMercadoId = superMercadoId;
	}
	
}
