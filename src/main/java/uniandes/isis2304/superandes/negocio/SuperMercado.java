package uniandes.isis2304.superandes.negocio;

public class SuperMercado implements VOSuperMercado
{
	private long id;
	
	public SuperMercado()
	{
		this.id=0;
	}
	
	public SuperMercado(long pId)
	{
		this.id=pId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
