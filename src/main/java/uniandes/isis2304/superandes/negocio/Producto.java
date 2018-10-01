package uniandes.isis2304.superandes.negocio;

public class Producto implements VOProducto
{
	private String codigoBarras;

	private String unidadMedida;
	
	private String nombre;
	
	private String marca;
	
	private double precioVenta;
	
	private double precioCompra;
	
	private String presentacion;
	
	private double precioXunidad;
	
	private int cantidad;
	
	private String empaque;
	
	private boolean percedero;
	
	private String categoria;
	
	private int nivelReo;
	
	private long estantesId;
	
	private long bodegasId;
	
	private long pedidoId;
	
	public Producto()
	{
		this.codigoBarras = "";
		this.unidadMedida = "";
		this.nombre = "";
		this.marca = "";
		this.precioVenta = 0;
		this.precioCompra = 0;
		this.presentacion = "";
		this.precioXunidad = 0;
		this.cantidad = 0;
		this.empaque = "";
		this.percedero = false;
		this.categoria = "";
		this.nivelReo = 0;
		this.estantesId = 0;
		this.bodegasId = 0;
		this.pedidoId = 0;
	}
	public Producto(String codigoBarras, String unidadMedida, String nombre, String marca, double precioVenta,
			double precioCompra, String presentacion, double precioXunidad, int cantidad, String empaque,
			boolean percedero, String categoria, int nivelReo, long estantesId, long bodegasId, long pedidoId) 
	{
		
		this.codigoBarras = codigoBarras;
		this.unidadMedida = unidadMedida;
		this.nombre = nombre;
		this.marca = marca;
		this.precioVenta = precioVenta;
		this.precioCompra = precioCompra;
		this.presentacion = presentacion;
		this.precioXunidad = precioXunidad;
		this.cantidad = cantidad;
		this.empaque = empaque;
		this.percedero = percedero;
		this.categoria = categoria;
		this.nivelReo = nivelReo;
		this.estantesId = estantesId;
		this.bodegasId = bodegasId;
		this.pedidoId = pedidoId;
	}
	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public double getPrecioXunidad() {
		return precioXunidad;
	}

	public void setPrecioXunidad(double precioXunidad) {
		this.precioXunidad = precioXunidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getEmpaque() {
		return empaque;
	}

	public void setEmpaque(String empaque) {
		this.empaque = empaque;
	}

	public boolean isPercedero() {
		return percedero;
	}

	public void setPercedero(boolean percedero) {
		this.percedero = percedero;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getNivelReo() {
		return nivelReo;
	}

	public void setNivelReo(int nivelReo) {
		this.nivelReo = nivelReo;
	}

	public long getEstantesId() {
		return estantesId;
	}

	public void setEstantesId(long estantesId) {
		this.estantesId = estantesId;
	}

	public long getBodegasId() {
		return bodegasId;
	}

	public void setBodegasId(long bodegasId) {
		this.bodegasId = bodegasId;
	}

	public long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(long pedidoId) {
		this.pedidoId = pedidoId;
	}
}
